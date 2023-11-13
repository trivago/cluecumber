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
package com.trivago.cluecumber.engine.properties;

import com.trivago.cluecumber.engine.constants.Navigation;
import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.engine.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.Link;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.LinkType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.trivago.cluecumber.engine.logging.CluecumberLogger.CluecumberLogLevel.COMPACT;
import static com.trivago.cluecumber.engine.logging.CluecumberLogger.CluecumberLogLevel.DEFAULT;

/**
 * This class stores and serves all Cluecumber properties.
 */
@Singleton
public class PropertyManager {

    private static final String COLOR_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private final CluecumberLogger logger;
    private final FileIO fileIO;
    private final PropertiesFileLoader propertiesFileLoader;
    private final Map<String, String> customParameters = new LinkedHashMap<>();
    private final Map<String, String> customNavigationLinks = new LinkedHashMap<>();
    private String sourceJsonReportDirectory;
    private String generatedHtmlReportDirectory;
    private boolean failScenariosOnPendingOrUndefinedSteps = false;
    private boolean expandBeforeAfterHooks = false;
    private boolean expandStepHooks = false;
    private boolean expandDocStrings = false;
    private boolean expandAttachments = true;
    private boolean groupPreviousScenarioRuns = false;
    private boolean expandPreviousScenarioRuns = true;
    private String customCssFile;
    private String customParametersFile;
    private Settings.CustomParamDisplayMode customParametersDisplayMode =
            Settings.CustomParamDisplayMode.ALL_PAGES;
    private String customStatusColorPassed = "#60cc79";
    private String customStatusColorFailed = "#fc7180";
    private String customStatusColorSkipped = "#f7c42b";
    private String customPageTitle = "Cluecumber Report";
    private Settings.StartPage startPage = Settings.StartPage.ALL_SCENARIOS;

    /**
     * Constructor for dependency injection.
     *
     * @param logger               The {@link CluecumberLogger} instance.
     * @param fileIO               The {@link FileIO} instance.
     * @param propertiesFileLoader The {@link PropertiesFileLoader} instance.
     */
    @Inject
    public PropertyManager(
            final CluecumberLogger logger,
            final FileIO fileIO,
            final PropertiesFileLoader propertiesFileLoader
    ) {
        this.logger = logger;
        this.fileIO = fileIO;
        this.propertiesFileLoader = propertiesFileLoader;
    }

    /**
     * Get the root directory of the source JSON files.
     *
     * @return The path.
     */
    public String getSourceJsonReportDirectory() {
        return sourceJsonReportDirectory;
    }

    /**
     * Set the root directory of the source JSON files.
     *
     * @param sourceJsonReportDirectory The path.
     * @throws WrongOrMissingPropertyException Thrown on any error.
     */
    public void setSourceJsonReportDirectory(final String sourceJsonReportDirectory)
            throws WrongOrMissingPropertyException {

        if (!isSet(sourceJsonReportDirectory)) {
            throw new WrongOrMissingPropertyException("sourceJsonReportDirectory");
        }
        this.sourceJsonReportDirectory = sourceJsonReportDirectory;
    }

    /**
     * Get the root directory of the target HTML report.
     *
     * @return The path.
     */
    public String getGeneratedHtmlReportDirectory() {
        return generatedHtmlReportDirectory;
    }

    /**
     * Set the root directory of the target HTML report.
     *
     * @param generatedHtmlReportDirectory The path.
     * @throws WrongOrMissingPropertyException Thrown on any error.
     */
    public void setGeneratedHtmlReportDirectory(final String generatedHtmlReportDirectory) throws WrongOrMissingPropertyException {
        if (!isSet(generatedHtmlReportDirectory)) {
            throw new WrongOrMissingPropertyException("generatedHtmlReportDirectory");
        }
        this.generatedHtmlReportDirectory = generatedHtmlReportDirectory;
    }

    /**
     * Get the custom parameters to be shown at the top of the report.
     *
     * @return The map of custom parameter key value pairs.
     */
    public Map<String, String> getCustomParameters() {
        return customParameters;
    }

    /**
     * Set the custom parameters to be shown at the top of the report.
     *
     * @param customParameters The map of custom parameter key value pairs.
     */
    public void setCustomParameters(final Map<String, String> customParameters) {
        this.customParameters.putAll(customParameters);
    }

    /**
     * Get the path to the custom parameter file.
     *
     * @return The path as string.
     */
    public String getCustomParametersFile() {
        return customParametersFile;
    }

    /**
     * Set the path to the custom parameter file.
     *
     * @param customParametersFile The path as string.
     * @throws CluecumberException Thrown on every error.
     */
    public void setCustomParametersFile(String customParametersFile) throws CluecumberException {
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

    /**
     * Get the display mode for custom parameters (on which page they should appear).
     *
     * @return The {@link com.trivago.cluecumber.engine.constants.Settings.CustomParamDisplayMode} value.
     */
    public Settings.CustomParamDisplayMode getCustomParametersDisplayMode() {
        return customParametersDisplayMode;
    }

    /**
     * Set the display mode for custom parameters (on which page they should appear).
     * Must be a value of {@link com.trivago.cluecumber.engine.constants.Settings.CustomParamDisplayMode}.
     *
     * @param customParametersDisplayMode The display mode string.
     */
    public void setCustomParametersDisplayMode(String customParametersDisplayMode) {
        try {
            this.customParametersDisplayMode = Settings.CustomParamDisplayMode.valueOf(customParametersDisplayMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Unknown setting for custom parameter page(s): '" + customParametersDisplayMode +
                    "'. Must be one of " + Arrays.toString(Settings.CustomParamDisplayMode.values()));
            this.customParametersDisplayMode = Settings.CustomParamDisplayMode.SCENARIO_PAGES;
        }
    }

    /**
     * Set the custom navigation links to appear next to the default Cluecumber navigation.
     * The map key represents the caption to be display in the navigation, the value is the actual URL to link to.
     *
     * @param customNavigationLinks The map of key value pairs.
     */
    public void setCustomNavigationLinks(final Map<String, String> customNavigationLinks) {
        if (customNavigationLinks == null) {
            return;
        }
        this.customNavigationLinks.putAll(customNavigationLinks);
    }

    /**
     * Get the custom navigation links to appear next to the default Cluecumber navigation.
     * The map key represents the caption to be display in the navigation, the value is the actual URL to link to.
     *
     * @return The map of key value pairs.
     */
    public List<Link> getNavigationLinks() {
        List<Link> links = new LinkedList<>(Navigation.internalLinks);

        customNavigationLinks.forEach((key, value) -> {
            String linkName = key.replace("_", " ");
            links.add(new Link(linkName, value, LinkType.EXTERNAL));
        });

        return links;
    }

    /**
     * This determines whether a scenario should be considered failed if it contains pending or undefined steps.
     *
     * @return true means fail on pending or undefined steps.
     */
    public boolean isFailScenariosOnPendingOrUndefinedSteps() {
        return failScenariosOnPendingOrUndefinedSteps;
    }

    /**
     * Set whether a scenario should be considered failed if it contains pending or undefined steps.
     *
     * @param failScenariosOnPendingOrUndefinedSteps true means fail on pending or undefined steps.
     */
    public void setFailScenariosOnPendingOrUndefinedSteps(final boolean failScenariosOnPendingOrUndefinedSteps) {
        this.failScenariosOnPendingOrUndefinedSteps = failScenariosOnPendingOrUndefinedSteps;
    }

    /**
     * This determines whether before and after hooks should be expanded by default.
     *
     * @return true means they should be expanded.
     */
    public boolean isExpandBeforeAfterHooks() {
        return expandBeforeAfterHooks;
    }

    /**
     * Set whether before and after hooks should be expanded by default.
     *
     * @param expandBeforeAfterHooks true means they should be expanded.
     */
    public void setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
        this.expandBeforeAfterHooks = expandBeforeAfterHooks;
    }

    /**
     * This determines whether step hooks should be expanded by default.
     *
     * @return true means they should be expanded.
     */
    public boolean isExpandStepHooks() {
        return expandStepHooks;
    }

    /**
     * Set whether step hooks should be expanded by default.
     *
     * @param expandStepHooks true means they should be expanded.
     */
    public void setExpandStepHooks(final boolean expandStepHooks) {
        this.expandStepHooks = expandStepHooks;
    }

    /**
     * This determines whether doc strings should be expanded by default.
     *
     * @return true means they should be expanded.
     */
    public boolean isExpandDocStrings() {
        return expandDocStrings;
    }

    /**
     * Set whether doc strings should be expanded by default.
     *
     * @param expandDocStrings true means they should be expanded.
     */
    public void setExpandDocStrings(final boolean expandDocStrings) {
        this.expandDocStrings = expandDocStrings;
    }

    /**
     * This determines whether attachments should be expanded by default.
     *
     * @return true means they should be expanded.
     */
    public boolean isExpandAttachments() {
        return expandAttachments;
    }

    /**
     * Set whether attachments should be expanded by default.
     *
     * @param expandAttachments true means they should be expanded.
     */
    public void setExpandAttachments(final boolean expandAttachments) {
        this.expandAttachments = expandAttachments;
    }

    /**
     * This determines whether the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     *
     * @return true means scenarios should be grouped and toggle should be shown.
     */
    public boolean isGroupPreviousScenarioRuns() {
        return groupPreviousScenarioRuns;
    }

    /**
     * Set whether the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     *
     * @param groupPreviousScenarioRuns true means scenarios should be grouped and toggle should be shown.
     */
    public void setGroupPreviousScenarioRuns(final boolean groupPreviousScenarioRuns) {
        this.groupPreviousScenarioRuns = groupPreviousScenarioRuns;
    }

    /**
     * This determines whether the not last run elements should be expanded and shown.
     *
     * @return true means it should be expanded.
     */
    public boolean isExpandPreviousScenarioRuns() {
        return expandPreviousScenarioRuns;
    }

    /**
     * Set whether the not last run elements should be expanded and shown.
     *
     * @param expandPreviousScenarioRuns true means elements should be expanded.
     */
    public void setExpandPreviousScenarioRuns(final boolean expandPreviousScenarioRuns) {
        this.expandPreviousScenarioRuns = expandPreviousScenarioRuns;
    }

    /**
     * Get the custom CSS file path.
     *
     * @return The path.
     */
    public String getCustomCssFile() {
        return customCssFile;
    }

    /**
     * Set the custom CSS file path.
     *
     * @param customCssFile The path.
     * @throws MissingFileException Thrown if the file is not found.
     */
    public void setCustomCssFile(final String customCssFile) throws MissingFileException {
        this.customCssFile = customCssFile;
        if (!isSet(customCssFile)) {
            return;
        }
        if (!fileIO.isExistingFile(customCssFile)) {
            throw new MissingFileException(customCssFile + " (customCssFile)");
        }
    }

    /**
     * Get the custom hex color for passed elements.
     *
     * @return The hex color string.
     */
    public String getCustomStatusColorPassed() {
        return this.customStatusColorPassed;
    }

    /**
     * Set a custom hex color for passed elements.
     *
     * @param customStatusColorPassed The color as a hex string (e.g. '#00ff00').
     * @throws WrongOrMissingPropertyException Thrown on any error.
     */
    public void setCustomStatusColorPassed(final String customStatusColorPassed) throws WrongOrMissingPropertyException {
        if (!isSet(customStatusColorPassed)) return;
        checkHexColorValidity(customStatusColorPassed, "customStatusColorPassed");
        this.customStatusColorPassed = customStatusColorPassed;
    }

    /**
     * Get the custom hex color for failed elements.
     *
     * @return The hex color string.
     */
    public String getCustomStatusColorFailed() {
        return this.customStatusColorFailed;
    }

    /**
     * Set a custom hex color for failed elements.
     *
     * @param customStatusColorFailed The color as a hex string (e.g. '#00ff00').
     * @throws WrongOrMissingPropertyException Thrown on any error.
     */
    public void setCustomStatusColorFailed(final String customStatusColorFailed) throws WrongOrMissingPropertyException {
        if (!isSet(customStatusColorFailed)) return;
        checkHexColorValidity(customStatusColorFailed, "customStatusColorFailed");
        this.customStatusColorFailed = customStatusColorFailed;
    }

    /**
     * Get the custom hex color for skipped elements.
     *
     * @return The hex color string.
     */
    public String getCustomStatusColorSkipped() {
        return this.customStatusColorSkipped;
    }

    /**
     * Set a custom hex color for skipped elements.
     *
     * @param customStatusColorSkipped The color as a hex string (e.g. '#00ff00').
     * @throws WrongOrMissingPropertyException Thrown on any error.
     */
    public void setCustomStatusColorSkipped(final String customStatusColorSkipped) throws WrongOrMissingPropertyException {
        if (!isSet(customStatusColorSkipped)) return;
        checkHexColorValidity(customStatusColorSkipped, "customStatusColorSkipped");
        this.customStatusColorSkipped = customStatusColorSkipped;
    }

    /**
     * Get the custom page title of the report.
     *
     * @return The page title.
     */
    public String getCustomPageTitle() {
        return this.customPageTitle;
    }

    /**
     * Set the custom page title of the report.
     *
     * @param customPageTitle The page title.
     */
    public void setCustomPageTitle(final String customPageTitle) {
        if (isSet(customPageTitle)) {
            this.customPageTitle = customPageTitle;
        }
    }

    /**
     * Log Cluecumber properties on the command line based on the set log level.
     */
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
        logger.info("- group previous scenario runs     : " + groupPreviousScenarioRuns, DEFAULT);
        logger.info("- expand previous scenario runs    : " + expandPreviousScenarioRuns, DEFAULT);

        if (!customNavigationLinks.isEmpty()) {
            customNavigationLinks.entrySet().stream().map(entry -> "- custom navigation link           : " +
                    entry.getKey() + " -> " + entry.getValue()).forEach(logString -> logger.info(logString, DEFAULT));
        }


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

    /**
     * Get the start page of the report.
     *
     * @return The {@link com.trivago.cluecumber.engine.constants.Settings.StartPage} value.
     */
    public Settings.StartPage getStartPage() {
        return startPage;
    }

    /**
     * Set the start page of the report.
     *
     * @param startPage The name of the start page (must be included in the {@link com.trivago.cluecumber.engine.constants.Settings.StartPage} enum.
     */
    public void setStartPage(final String startPage) {
        try {
            this.startPage = Settings.StartPage.valueOf(startPage.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Unknown start page '" + startPage + "'. Must be one of " + Arrays.toString(Settings.StartPage.values()));
            this.startPage = Settings.StartPage.ALL_SCENARIOS;
        }
    }
}
