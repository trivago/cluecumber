/*
 * Copyright 2023 trivago N.V.
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
package com.trivago.cluecumber.engine;

import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.engine.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.filesystem.FileSystemManager;
import com.trivago.cluecumber.engine.json.JsonPojoConverter;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.processors.ElementIndexPreProcessor;
import com.trivago.cluecumber.engine.json.processors.ElementMultipleRunsPreProcessor;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.ReportGenerator;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;

import static com.trivago.cluecumber.engine.logging.CluecumberLogger.CluecumberLogLevel.COMPACT;
import static com.trivago.cluecumber.engine.logging.CluecumberLogger.CluecumberLogLevel.DEFAULT;
import static com.trivago.cluecumber.engine.logging.CluecumberLogger.CluecumberLogLevel.MINIMAL;

/**
 * The main plugin class.
 */
public final class CluecumberEngine {

    private final CluecumberLogger logger;
    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;
    private final FileIO fileIO;
    private final JsonPojoConverter jsonPojoConverter;
    private final ElementIndexPreProcessor elementIndexPreProcessor;
    private final ElementMultipleRunsPreProcessor elementMultipleRunsPreProcessor;
    private final ReportGenerator reportGenerator;

    /**
     * Skip Cluecumber report generation.
     */
    @SuppressWarnings("unused")
    private boolean skip;

    /**
     * Constructor for dependency injection.
     *
     * @param elementIndexPreProcessor The {@link ElementIndexPreProcessor} instance.
     * @param elementMultipleRunsPreProcessor The {@link ElementMultipleRunsPreProcessor} instance.
     * @param fileIO                   The {@link FileIO} instance.
     * @param fileSystemManager        The {@link FileSystemManager} instance.
     * @param jsonPojoConverter        The {@link JsonPojoConverter} instance.
     * @param logger                   The {@link CluecumberLogger} instance.
     * @param propertyManager          The {@link PropertyManager} instance.
     * @param reportGenerator          The {@link ReportGenerator} instance.
     */
    @Inject
    public CluecumberEngine(
            final CluecumberLogger logger,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final FileIO fileIO,
            final JsonPojoConverter jsonPojoConverter,
            final ElementIndexPreProcessor elementIndexPreProcessor,
            final ElementMultipleRunsPreProcessor elementMultipleRunsPreProcessor,
            final ReportGenerator reportGenerator
    ) {
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
        this.fileIO = fileIO;
        this.jsonPojoConverter = jsonPojoConverter;
        this.logger = logger;
        this.elementIndexPreProcessor = elementIndexPreProcessor;
        this.elementMultipleRunsPreProcessor = elementMultipleRunsPreProcessor;
        this.reportGenerator = reportGenerator;
    }

    /**
     * Cluecumber Report start method.
     *
     * @param sourceJsonReportDirectory    The source directory of Cucumber JSON files.
     * @param generatedHtmlReportDirectory The target directory of the generated report.
     * @throws CluecumberException When thrown, the plugin execution is stopped.
     */
    public void build(
            final String sourceJsonReportDirectory,
            final String generatedHtmlReportDirectory) throws CluecumberException {

        propertyManager.setSourceJsonReportDirectory(sourceJsonReportDirectory);
        propertyManager.setGeneratedHtmlReportDirectory(generatedHtmlReportDirectory);

        if (skip) {
            logger.info("Cluecumber report generation was skipped using the <skip> property.",
                    DEFAULT);
            return;
        }

        logger.logInfoSeparator(DEFAULT);
        logger.info(String.format(" [ Cluecumber v%s ]", RenderingUtils.getPluginVersion()), DEFAULT);
        logger.logInfoSeparator(DEFAULT, COMPACT);

        propertyManager.logProperties();

        // Create attachment directory here since they are handled during json generation.
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/attachments");

        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection(propertyManager.getCustomPageTitle());
        List<Path> jsonFilePaths = fileSystemManager.getJsonFilePaths(propertyManager.getSourceJsonReportDirectory());
        for (Path jsonFilePath : jsonFilePaths) {
            String jsonString = fileIO.readContentFromFile(jsonFilePath.toString());
            try {
                Report[] reports = jsonPojoConverter.convertJsonToReportPojos(jsonString);
                allScenariosPageCollection.addReports(reports);
            } catch (CluecumberException e) {
                logger.warn("Could not parse JSON in file '" + jsonFilePath + "': " + e.getMessage());
            }
        }
        elementIndexPreProcessor.addScenarioIndices(allScenariosPageCollection.getReports());
        if (propertyManager.isGroupPreviousScenarioRuns()) {
            elementMultipleRunsPreProcessor.addMultipleRunsInformationToScenarios(allScenariosPageCollection.getReports());
        }
        reportGenerator.generateReport(allScenariosPageCollection);
        logger.info(
                "=> Cluecumber Report: " + propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                        Settings.START_PAGE + Settings.HTML_FILE_EXTENSION,
                DEFAULT,
                COMPACT,
                MINIMAL
        );
    }

    /**
     * Custom parameters to display at the top of the test report.
     *
     * @param customParameters A map of custom key value pairs.
     */
    public void setCustomParameters(final LinkedHashMap<String, String> customParameters) {
        if (customParameters == null) {
            return;
        }
        propertyManager.setCustomParameters(customParameters);
    }

    /**
     * Set a file that contains custom parameters as properties.
     *
     * @param customParametersFile The path to a properties file.
     * @throws CluecumberException Thrown on any error.
     */
    public void setCustomParametersFile(final String customParametersFile) throws CluecumberException {
        propertyManager.setCustomParametersFile(customParametersFile);
    }

    /**
     * Where to display custom parameters.
     *
     * @param customParametersDisplayMode The display mode for custom parameters.
     */
    public void setCustomParametersDisplayMode(final String customParametersDisplayMode) {
        if (customParametersDisplayMode == null) {
            return;
        }
        propertyManager.setCustomParametersDisplayMode(customParametersDisplayMode);
    }

    /**
     * Custom navigation links to display at the end of the default navigation.
     *
     * @param customNavigationLinks A map of custom key value pairs (key is the link name, value is the URL).
     */
    public void setCustomNavigationLinks(final LinkedHashMap<String, String> customNavigationLinks) {
        propertyManager.setCustomNavigationLinks(customNavigationLinks);
    }

    /**
     * Whether to fail scenarios when steps are pending or undefined.
     *
     * @param failScenariosOnPendingOrUndefinedSteps On true, it will fail scenarios with pending or undefined steps.
     */
    public void setFailScenariosOnPendingOrUndefinedSteps(final boolean failScenariosOnPendingOrUndefinedSteps) {
        propertyManager.setFailScenariosOnPendingOrUndefinedSteps(failScenariosOnPendingOrUndefinedSteps);
    }

    /**
     * Whether to expand before and after hooks or not.
     *
     * @param expandBeforeAfterHooks If true, before and after hooks will be expanded.
     */
    public void setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
        propertyManager.setExpandBeforeAfterHooks(expandBeforeAfterHooks);
    }

    /**
     * Whether to expand step hooks or not.
     *
     * @param expandStepHooks If true, step hooks will be expanded.
     */
    public void setExpandStepHooks(final boolean expandStepHooks) {
        propertyManager.setExpandStepHooks(expandStepHooks);
    }

    /**
     * Whether to expand doc strings or not.
     *
     * @param expandDocStrings If true, doc strings will be expanded.
     */
    public void setExpandDocStrings(final boolean expandDocStrings) {
        propertyManager.setExpandDocStrings(expandDocStrings);
    }

    /**
     * Whether to expand attachments or not.
     *
     * @param expandAttachments If true, attachments will be expanded.
     */
    public void setExpandAttachments(final boolean expandAttachments) {
        propertyManager.setExpandAttachments(expandAttachments);
    }

    /**
     * Whether to show the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     *
     * @param groupPreviousScenarioRuns If true, the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     */
    public void setGroupPreviousScenarioRuns(final boolean groupPreviousScenarioRuns) {
        propertyManager.setGroupPreviousScenarioRuns(groupPreviousScenarioRuns);
    }

    /**
     * Whether to expand not last run elements or not.
     *
     * @param expandPreviousScenarioRuns If true, not last run elements will be expanded.
     */
    public void setExpandPreviousScenarioRuns(final boolean expandPreviousScenarioRuns) {
        propertyManager.setExpandPreviousScenarioRuns(expandPreviousScenarioRuns);
    }

    /**
     * Custom CSS file to override default styles.
     *
     * @param customCss The path to a CSS file.
     * @throws MissingFileException Thrown if the specified file does not exist.
     */
    public void setCustomCssFile(final String customCss) throws MissingFileException {
        if (customCss == null) {
            return;
        }
        propertyManager.setCustomCssFile(customCss);
    }

    /**
     * Set a custom color for passed scenarios.
     *
     * @param customStatusColorPassed A color in hex format.
     * @throws WrongOrMissingPropertyException Thrown if the property is missing or in a wrong format.
     */
    public void setCustomStatusColorPassed(final String customStatusColorPassed) throws WrongOrMissingPropertyException {
        propertyManager.setCustomStatusColorPassed(customStatusColorPassed);
    }

    /**
     * Set a custom color for failed scenarios.
     *
     * @param customStatusColorFailed A color in hex format.
     * @throws WrongOrMissingPropertyException Thrown if the property is missing or in a wrong format.
     */
    public void setCustomStatusColorFailed(final String customStatusColorFailed) throws WrongOrMissingPropertyException {
        propertyManager.setCustomStatusColorFailed(customStatusColorFailed);
    }

    /**
     * Set a custom color for skipped scenarios.
     *
     * @param customStatusColorSkipped A color in hex format.
     * @throws WrongOrMissingPropertyException Thrown in case of a missing or incompatible property.
     */
    public void setCustomStatusColorSkipped(final String customStatusColorSkipped) throws WrongOrMissingPropertyException {
        propertyManager.setCustomStatusColorSkipped(customStatusColorSkipped);
    }

    /**
     * Set a custom page tite for the report.
     *
     * @param customPageTitle The custom page title.
     */
    public void setCustomPageTitle(final String customPageTitle) {
        propertyManager.setCustomPageTitle(customPageTitle);
    }

    /**
     * Set the start page of the test report that should be directed to.
     *
     * @param startPage The name of the start page (referring to {@link Settings.StartPage}).
     */
    public void setStartPage(final String startPage) {
        if (startPage == null) {
            return;
        }
        propertyManager.setStartPage(startPage);
    }

    /**
     * Set the log level for Cluecumber output.
     *
     * @param logLevel The log level (referring to {@link com.trivago.cluecumber.engine.logging.CluecumberLogger.CluecumberLogLevel}).
     */
    public void setLogLevel(final String logLevel) {
        logger.setLogLevel(logLevel);
    }
}
