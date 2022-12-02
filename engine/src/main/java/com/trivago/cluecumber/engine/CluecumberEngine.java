/*
 * Copyright 2022 trivago N.V.
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

import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.engine.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.filesystem.FileSystemManager;
import com.trivago.cluecumber.engine.json.JsonPojoConverter;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.processors.ElementIndexPreProcessor;
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
    private final ReportGenerator reportGenerator;

    /**
     * Skip Cluecumber report generation.
     */
    @SuppressWarnings("unused")
    private boolean skip;

    @Inject
    public CluecumberEngine(
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
        reportGenerator.generateReport(allScenariosPageCollection);
        logger.info(
                "=> Cluecumber Report: " + propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                        PluginSettings.START_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION,
                DEFAULT,
                COMPACT,
                MINIMAL
        );
    }

    public void setCustomParameters(final LinkedHashMap<String, String> customParameters) {
        if (customParameters == null) {
            return;
        }
        propertyManager.setCustomParameters(customParameters);
    }

    public void setCustomParametersFile(final String customParametersFile) throws CluecumberException {
        propertyManager.setCustomParametersFile(customParametersFile);
    }

    public void setCustomParametersDisplayMode(final String customParametersDisplayMode) {
        if (customParametersDisplayMode == null) {
            return;
        }
        propertyManager.setCustomParametersDisplayMode(customParametersDisplayMode);
    }

    public void setCustomNavigationLinks(final LinkedHashMap<String, String> customNavigationLinks) {
        propertyManager.setCustomNavigationLinks(customNavigationLinks);
    }

    public void setFailScenariosOnPendingOrUndefinedSteps(final boolean failScenariosOnPendingOrUndefinedSteps) {
        propertyManager.setFailScenariosOnPendingOrUndefinedSteps(failScenariosOnPendingOrUndefinedSteps);
    }

    public void setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
        propertyManager.setExpandBeforeAfterHooks(expandBeforeAfterHooks);
    }

    public void setExpandStepHooks(final boolean expandStepHooks) {
        propertyManager.setExpandStepHooks(expandStepHooks);
    }

    public void setExpandDocStrings(final boolean expandDocStrings) {
        propertyManager.setExpandStepHooks(expandDocStrings);
    }

    public void setExpandAttachments(final boolean expandAttachments) {
        propertyManager.setExpandAttachments(expandAttachments);
    }

    public void setCustomCssFile(final String customCss) throws MissingFileException {
        if (customCss == null) {
            return;
        }
        propertyManager.setCustomCssFile(customCss);
    }

    public void setCustomStatusColorPassed(final String customStatusColorPassed) throws WrongOrMissingPropertyException {
        propertyManager.setCustomStatusColorPassed(customStatusColorPassed);
    }

    public void setCustomStatusColorFailed(final String customStatusColorFailed) throws WrongOrMissingPropertyException {
        propertyManager.setCustomStatusColorFailed(customStatusColorFailed);
    }

    public void setCustomStatusColorSkipped(final String customStatusColorSkipped) throws WrongOrMissingPropertyException {
        propertyManager.setCustomStatusColorSkipped(customStatusColorSkipped);
    }

    public void setCustomPageTitle(final String customPageTitle) {
        propertyManager.setCustomPageTitle(customPageTitle);
    }

    public void setStartPage(final String startPage) {
        if (startPage == null) {
            return;
        }
        propertyManager.setStartPage(startPage);
    }

    public void setLogLevel(final String logLevel) {
        logger.setLogLevel(logLevel);
    }
}
