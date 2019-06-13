/*
 * Copyright 2018 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.cluecumber;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.filesystem.FileSystemManager;
import com.trivago.cluecumber.json.JsonPojoConverter;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.processors.ElementIndexPreProcessor;
import com.trivago.cluecumber.logging.CluecumberLogger;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.ReportGenerator;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The main plugin class.
 */
@Mojo(name = "reporting")
public final class CluecumberReportPlugin extends AbstractMojo {

    private final CluecumberLogger logger;
    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;
    private final FileIO fileIO;
    private final JsonPojoConverter jsonPojoConverter;
    private final ElementIndexPreProcessor elementIndexPreProcessor;
    private final ReportGenerator reportGenerator;

    /**
     * The path to the Cucumber JSON files.
     */
    @Parameter(property = "reporting.sourceJsonReportDirectory", required = true)
    private String sourceJsonReportDirectory = "";

    /**
     * The location of the generated report.
     */
    @Parameter(property = "reporting.generatedHtmlReportDirectory", required = true)
    private String generatedHtmlReportDirectory = "";

    /**
     * Custom parameters to add to the report.
     */
    @Parameter(property = "reporting.customParameters")
    private LinkedHashMap<String, String> customParameters = new LinkedHashMap<>();

    /**
     * Path to a properties file. The included properties are converted to custom parameters.
     * <pre>
     *
     * </pre>
     */
    @Parameter(property = "reporting.customParametersFile")
    private String customParametersFile = "";

    /**
     * Mark scenarios as failed if they contain pending or undefined steps (default: false).
     */
    @Parameter(property = "reporting.failScenariosOnPendingOrUndefinedSteps", defaultValue = "false")
    private boolean failScenariosOnPendingOrUndefinedSteps;

    /**
     * Custom CSS that is applied on top of Cluecumber's default styles.
     */
    @Parameter(property = "reporting.customCss")
    private String customCss = "";

    /**
     * Custom flag that determines if before and after hook sections of scenario detail pages should be expanded (default: false).
     */
    @Parameter(property = "reporting.expandBeforeAfterHooks", defaultValue = "false")
    private boolean expandBeforeAfterHooks;

    /**
     * Custom flag that determines if step hook sections of scenario detail pages should be expanded (default: false).
     */
    @Parameter(property = "reporting.expandStepHooks", defaultValue = "false")
    private boolean expandStepHooks;

    /**
     * Custom flag that determines if doc string sections of scenario detail pages should be expanded (default: false).
     */
    @Parameter(property = "reporting.expandDocStrings", defaultValue = "false")
    private boolean expandDocStrings;

    /**
     * Skip Cluecumber report generation.
     */
    @Parameter(defaultValue = "false", property = "skip")
    private boolean skip;

    @Inject
    public CluecumberReportPlugin(
            final CluecumberLogger logger,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final FileIO fileIO,
            final JsonPojoConverter jsonPojoConverter,
            final ElementIndexPreProcessor elementIndexPreProcessor,
            final ReportGenerator reportGenerator
    ) {
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
        this.fileIO = fileIO;
        this.jsonPojoConverter = jsonPojoConverter;
        this.logger = logger;
        this.elementIndexPreProcessor = elementIndexPreProcessor;
        this.reportGenerator = reportGenerator;
    }

    /**
     * Cluecumber Report start method.
     *
     * @throws CluecumberPluginException When thrown, the plugin execution is stopped.
     */
    public void execute() throws CluecumberPluginException {
        // Initialize logger to be available outside the AbstractMojo class
        logger.setMojoLogger(getLog());

        if (skip) {
            getLog().info("Cluecumber report generation was skipped using the <skip> property.");
            return;
        }

        // Initialize and validate passed pom properties
        propertyManager.setSourceJsonReportDirectory(sourceJsonReportDirectory);
        propertyManager.setGeneratedHtmlReportDirectory(generatedHtmlReportDirectory);
        propertyManager.setCustomParameters(customParameters);
        propertyManager.setCustomParametersFile(customParametersFile);
        propertyManager.setFailScenariosOnPendingOrUndefinedSteps(failScenariosOnPendingOrUndefinedSteps);
        propertyManager.setExpandBeforeAfterHooks(expandBeforeAfterHooks);
        propertyManager.setExpandStepHooks(expandStepHooks);
        propertyManager.setExpandDocStrings(expandDocStrings);
        propertyManager.setCustomCssFile(customCss);

        logger.logSeparator();
        logger.info(String.format(" Cluecumber Report Maven Plugin, version %s", getClass().getPackage().getImplementationVersion()));
        logger.logSeparator();
        propertyManager.logProperties();

        // Create attachment directory here since they are handled during json generation.
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/attachments");

        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        List<Path> jsonFilePaths = fileSystemManager.getJsonFilePaths(propertyManager.getSourceJsonReportDirectory());
        for (Path jsonFilePath : jsonFilePaths) {
            String jsonString = fileIO.readContentFromFile(jsonFilePath.toString());
            try {
                Report[] reports = jsonPojoConverter.convertJsonToReportPojos(jsonString);
                allScenariosPageCollection.addReports(reports);
            } catch (CluecumberPluginException e) {
                logger.error("Could not parse JSON in file '" + jsonFilePath.toString() + "': " + e.getMessage());
            }
        }
        elementIndexPreProcessor.addScenarioIndices(allScenariosPageCollection.getReports());
        reportGenerator.generateReport(allScenariosPageCollection);
        logger.info(
                "=> Cluecumber Report: " + propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                        PluginSettings.SCENARIO_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }
}



