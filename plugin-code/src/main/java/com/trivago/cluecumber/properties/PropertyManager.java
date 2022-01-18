/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.logging.CluecumberLogger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.trivago.cluecumber.logging.CluecumberLogger.CluecumberLogLevel.COMPACT;
import static com.trivago.cluecumber.logging.CluecumberLogger.CluecumberLogLevel.DEFAULT;

@Singleton
public class PropertyManager {

    private static final String COLOR_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private final CluecumberLogger logger;
    private final FileIO fileIO;
    private final PropertiesFileLoader propertiesFileLoader;

    private String sourceJsonReportDirectory;
    private String generatedHtmlReportDirectory;

    private boolean failScenariosOnPendingOrUndefinedSteps;
    private boolean expandBeforeAfterHooks;
    private boolean expandStepHooks;
    private boolean expandDocStrings;
    private boolean expandAttachments;

    private final Map<String, String> customParameters = new LinkedHashMap<>();
    private String customCssFile;
    private String customParametersFile;

    private PluginSettings.CustomParamDisplayMode customParametersDisplayMode;

    private String customStatusColorPassed = "#28a745";
    private String customStatusColorFailed = "#dc3545";
    private String customStatusColorSkipped = "#ffc107";
    private String customPageTitle = "Cluecumber Report";

    private PluginSettings.StartPage startPage;

    @Inject
    public PropertyManager(
            final CluecumberLogger logger,
            FileIO fileIO,
            final PropertiesFileLoader propertiesFileLoader
    ) {
        this.logger = logger;
        this.fileIO = fileIO;
        this.propertiesFileLoader = propertiesFileLoader;
    }

    public String getSourceJsonReportDirectory() {
        return sourceJsonReportDirectory;
    }

    public void setSourceJsonReportDirectory(final String sourceJsonReportDirectory)
            throws WrongOrMissingPropertyException {

        if (!isSet(sourceJsonReportDirectory)) {
            throw new WrongOrMissingPropertyException("sourceJsonReportDirectory");
        }
        this.sourceJsonReportDirectory = sourceJsonReportDirectory;
    }

    public String getGeneratedHtmlReportDirectory() {
        return generatedHtmlReportDirectory;
    }

    public void setGeneratedHtmlReportDirectory(final String generatedHtmlReportDirectory) throws WrongOrMissingPropertyException {
        if (!isSet(generatedHtmlReportDirectory)) {
            throw new WrongOrMissingPropertyException("generatedHtmlReportDirectory");
        }
        this.generatedHtmlReportDirectory = generatedHtmlReportDirectory;
    }

    public Map<String, String> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(final Map<String, String> customParameters) {
        this.customParameters.putAll(customParameters);
    }

    String getCustomParametersFile() {
        return customParametersFile;
    }

    public void setCustomParametersFile(String customParametersFile) throws CluecumberPluginException {
        this.customParametersFile = customParametersFile;
        if (!isSet(customParametersFile)) {
            return;
        }
        if (!fileIO.isExistingFile(customParametersFile)) {
            throw new MissingFileException(customParametersFile + " (customParametersFile)");
        }
        Map<String, String> customParameters = propertiesFileLoader.loadPropertiesMap(customParametersFile);
        this.customParameters.putAll(customParameters);
    }

    public PluginSettings.CustomParamDisplayMode getCustomParametersDisplayMode() {
        return customParametersDisplayMode;
    }

    public void setCustomParametersDisplayMode(String customParametersDisplayMode) {
        try {
            this.customParametersDisplayMode = PluginSettings.CustomParamDisplayMode.valueOf(customParametersDisplayMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Unknown setting for custom parameter page(s): '" + customParametersDisplayMode + "'. Must be one of " + Arrays.toString(PluginSettings.CustomParamDisplayMode.values()));
            this.customParametersDisplayMode = PluginSettings.CustomParamDisplayMode.SCENARIO_PAGES;
        }
    }

    public boolean isFailScenariosOnPendingOrUndefinedSteps() {
        return failScenariosOnPendingOrUndefinedSteps;
    }

    public void setFailScenariosOnPendingOrUndefinedSteps(final boolean failScenariosOnPendingOrUndefinedSteps) {
        this.failScenariosOnPendingOrUndefinedSteps = failScenariosOnPendingOrUndefinedSteps;
    }

    public boolean isExpandBeforeAfterHooks() {
        return expandBeforeAfterHooks;
    }

    public void setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
        this.expandBeforeAfterHooks = expandBeforeAfterHooks;
    }

    public boolean isExpandStepHooks() {
        return expandStepHooks;
    }

    public void setExpandStepHooks(final boolean expandStepHooks) {
        this.expandStepHooks = expandStepHooks;
    }

    public boolean isExpandDocStrings() {
        return expandDocStrings;
    }

    public void setExpandDocStrings(final boolean expandDocStrings) {
        this.expandDocStrings = expandDocStrings;
    }

    public boolean isExpandAttachments() {
        return expandAttachments;
    }

    public void setExpandAttachments(final boolean expandAttachments) {
        this.expandAttachments = expandAttachments;
    }

    public String getCustomCssFile() {
        return customCssFile;
    }

    public void setCustomCssFile(final String customCssFile) throws MissingFileException {
        this.customCssFile = customCssFile;
        if (!isSet(customCssFile)) {
            return;
        }
        if (!fileIO.isExistingFile(customCssFile)) {
            throw new MissingFileException(customCssFile + " (customCssFile)");
        }
    }

    public String getCustomStatusColorPassed() {
        return this.customStatusColorPassed;
    }

    public void setCustomStatusColorPassed(final String customStatusColorPassed) throws WrongOrMissingPropertyException {
        if (!isSet(customStatusColorPassed)) return;
        checkHexColorValidity(customStatusColorPassed, "customStatusColorPassed");
        this.customStatusColorPassed = customStatusColorPassed;
    }

    public String getCustomStatusColorFailed() {
        return this.customStatusColorFailed;
    }

    public void setCustomStatusColorFailed(final String customStatusColorFailed) throws WrongOrMissingPropertyException {
        if (!isSet(customStatusColorFailed)) return;
        checkHexColorValidity(customStatusColorFailed, "customStatusColorFailed");
        this.customStatusColorFailed = customStatusColorFailed;
    }

    public String getCustomStatusColorSkipped() {
        return this.customStatusColorSkipped;
    }

    public void setCustomStatusColorSkipped(final String customStatusColorSkipped) throws WrongOrMissingPropertyException {
        if (!isSet(customStatusColorSkipped)) return;
        checkHexColorValidity(customStatusColorSkipped, "customStatusColorSkipped");
        this.customStatusColorSkipped = customStatusColorSkipped;
    }

    public String getCustomPageTitle() {
        return this.customPageTitle;
    }

    public void setCustomPageTitle(final String customPageTitle) {
        if (isSet(customPageTitle)) {
            this.customPageTitle = customPageTitle;
        }
    }

    public void logProperties() {
        logger.info("- source JSON report directory     : " + sourceJsonReportDirectory, DEFAULT, COMPACT);
        logger.info("- generated HTML report directory  : " + generatedHtmlReportDirectory, DEFAULT, COMPACT);

        boolean customParametersFileExists = isSet(customParametersFile);
        if (customParametersFileExists) {
            logger.logInfoSeparator(DEFAULT);
            logger.info("- custom parameters file           : " + customParametersFile, DEFAULT);
        }

        if (!customParameters.isEmpty()) {
            if (!customParametersFileExists) {
                logger.logInfoSeparator();
            }
            customParameters.entrySet().stream().map(entry -> "- custom parameter                 : " +
                    entry.getKey() + " -> " + entry.getValue()).forEach(logString -> logger.info(logString, DEFAULT));
        }

        logger.logInfoSeparator(DEFAULT);

        logger.info("- fail pending/undefined scenarios : " + failScenariosOnPendingOrUndefinedSteps, DEFAULT);
        logger.info("- expand before/after hooks        : " + expandBeforeAfterHooks, DEFAULT);
        logger.info("- expand step hooks                : " + expandStepHooks, DEFAULT);
        logger.info("- expand doc strings               : " + expandDocStrings, DEFAULT);
        logger.info("- expand attachments               : " + expandAttachments, DEFAULT);
        logger.info("- page title                       : " + customPageTitle, DEFAULT);
        logger.info("- start page                       : " + startPage, DEFAULT);
        logger.info("- custom parameters display mode   : " + customParametersDisplayMode, DEFAULT);

        if (isSet(customCssFile)) {
            logger.info("- custom CSS file                  : " + customCssFile, DEFAULT);
        }

        logger.info("- colors (passed, failed, skipped) : " +
                customStatusColorPassed + ", " + customStatusColorFailed + ", " + customStatusColorSkipped, DEFAULT);

        logger.logInfoSeparator(DEFAULT);
    }

    private boolean isSet(final String string) {
        return string != null && !string.trim().isEmpty();
    }

    private void checkHexColorValidity(String color, String colorPropertyName) throws WrongOrMissingPropertyException {
        if (!Pattern.compile(COLOR_PATTERN).matcher(color).matches()) {
            throw new WrongOrMissingPropertyException(colorPropertyName);
        }
    }

    public PluginSettings.StartPage getStartPage() {
        return startPage;
    }

    public void setStartPage(final String startPage) {
        try {
            this.startPage = PluginSettings.StartPage.valueOf(startPage.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Unknown start page '" + startPage + "'. Must be one of " + Arrays.toString(PluginSettings.StartPage.values()));
            this.startPage = PluginSettings.StartPage.ALL_SCENARIOS;
        }
    }
}
