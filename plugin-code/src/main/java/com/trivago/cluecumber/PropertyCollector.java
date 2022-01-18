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

package com.trivago.cluecumber;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.properties.PropertyManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.inject.Inject;
import java.util.LinkedHashMap;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public class PropertyCollector extends AbstractMojo {

    private final PropertyManager propertyManager;

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
     * Path to a properties file. The included properties are converted to custom parameters and added to the others.
     * <pre>
     * My_Custom_Parameter=This is my custom value
     * My_Custom_Parameter2=This is another value
     * </pre>
     */
    @Parameter(property = "reporting.customParametersFile")
    private String customParametersFile = "";

    /**
     * Path to a properties file. The included properties are converted to custom parameters and added to the others.
     * <pre>
     * My_Custom_Parameter=This is my custom value
     * My_Custom_Parameter2=This is another value
     * </pre>
     */
    @Parameter(property = "reporting.customParametersDisplayMode", defaultValue = "SCENARIO_PAGES")
    private String customParametersDisplayMode = "";

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
     * Custom flag that determines if attachment sections of scenario detail pages should be expanded (default: false).
     */
    @Parameter(property = "reporting.expandAttachments", defaultValue = "false")
    private boolean expandAttachments;

    /**
     * Custom hex color for passed scenarios (e.g. '#00ff00')'.
     */
    @Parameter(property = "reporting.customStatusColorPassed")
    private String customStatusColorPassed;

    /**
     * Custom hex color for failed scenarios (e.g. '#ff0000')'.
     */
    @Parameter(property = "reporting.customStatusColorFailed")
    private String customStatusColorFailed;

    /**
     * Custom hex color for skipped scenarios (e.g. '#ffff00')'.
     */
    @Parameter(property = "reporting.customStatusColorSkipped")
    private String customStatusColorSkipped;

    /**
     * Custom page title for the generated report.
     */
    @Parameter(property = "reporting.customPageTitle")
    private String customPageTitle;

    /**
     * Custom start page (default: ALL_SCENARIOS).
     * Allowed values: ALL_SCENARIOS, SCENARIO_SEQUENCE, ALL_FEATURES, ALL_TAGS, ALL_STEPS
     */
    @Parameter(property = "reporting.startPage", defaultValue = "ALL_SCENARIOS")
    private String startPage;

    /**
     * Optional log level to control what information is logged in the console.
     * Allowed values: default, compact, minimal, off
     */
    @Parameter(property = "parallel.logLevel", defaultValue = "default")
    String logLevel;

    @Inject
    public PropertyCollector(final PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    @Override
    public void execute() throws CluecumberPluginException {
        try {
            // Initialize and validate passed pom properties
            propertyManager.setSourceJsonReportDirectory(sourceJsonReportDirectory);
            propertyManager.setGeneratedHtmlReportDirectory(generatedHtmlReportDirectory);
            propertyManager.setCustomParameters(customParameters);
            propertyManager.setCustomParametersFile(customParametersFile);
            propertyManager.setCustomParametersDisplayMode(customParametersDisplayMode);
            propertyManager.setFailScenariosOnPendingOrUndefinedSteps(failScenariosOnPendingOrUndefinedSteps);
            propertyManager.setExpandBeforeAfterHooks(expandBeforeAfterHooks);
            propertyManager.setExpandStepHooks(expandStepHooks);
            propertyManager.setExpandDocStrings(expandDocStrings);
            propertyManager.setExpandAttachments(expandAttachments);
            propertyManager.setCustomCssFile(customCss);
            propertyManager.setCustomStatusColorPassed(customStatusColorPassed);
            propertyManager.setCustomStatusColorFailed(customStatusColorFailed);
            propertyManager.setCustomStatusColorSkipped(customStatusColorSkipped);
            propertyManager.setCustomPageTitle(customPageTitle);
            propertyManager.setStartPage(startPage);
        } catch (Exception e) {
            throw new CluecumberPluginException(e.getMessage());
        }
        propertyManager.logProperties();
    }
}
