/*
 * Copyright 2017 trivago N.V.
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

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.json.JsonPojoConverter;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.ReportGenerator;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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
    @Parameter(property = "reporting.customParameters", required = true)
    private Map<String, String> customParameters;

    @Inject
    public CluecumberReportPlugin(
            final CluecumberLogger logger,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final FileIO fileIO,
            final JsonPojoConverter jsonPojoConverter,
            final ReportGenerator reportGenerator
    ) {
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
        this.fileIO = fileIO;
        this.jsonPojoConverter = jsonPojoConverter;
        this.logger = logger;
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

        // Initialize and validate passed pom properties
        propertyManager.setSourceJsonReportDirectory(sourceJsonReportDirectory);
        propertyManager.setGeneratedHtmlReportDirectory(generatedHtmlReportDirectory);
        propertyManager.setCustomParameters(customParameters);
        propertyManager.validateSettings();

        logger.info("--------------------------------");
        logger.info(" Cluecumber Report Maven Plugin ");
        logger.info("--------------------------------");

        propertyManager.logProperties();

        // Create attachments directory for json attachment saving
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/attachments");

        StartPageCollection startPageCollection = new StartPageCollection();
        List<Path> jsonFilePaths = fileSystemManager.getJsonFilePaths();
        for (Path jsonFilePath : jsonFilePaths) {
            String jsonString = fileIO.readContentFromFile(jsonFilePath.toString());
            try {
                Report[] reports = jsonPojoConverter.convertJsonToReportPojos(jsonString);
                startPageCollection.addReports(reports);
            } catch (CluecumberPluginException e) {
                logger.error("Could not parse JSON in file '" + jsonFilePath.toString() + "': " + e.getMessage());
            }
        }
        reportGenerator.generateReports(startPageCollection);
        logger.info("Converted " + startPageCollection.getTotalNumberOfFeatures() + " features into test report:");
        logger.info("- " + propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.START_PAGE_NAME);
    }
}



