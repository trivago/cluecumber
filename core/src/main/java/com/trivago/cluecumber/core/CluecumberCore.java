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
package com.trivago.cluecumber.core;

import com.trivago.cluecumber.engine.CluecumberEngine;
import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.dagger.DaggerCluecumberCoreGraph;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;

import java.util.LinkedHashMap;

/**
 * The main Cluecumber core class that passes properties to the Cluecumber engine.
 */
public class CluecumberCore {

    private final CluecumberEngine cluecumberEngine;

    private CluecumberCore(Builder builder) throws CluecumberException {
        cluecumberEngine = DaggerCluecumberCoreGraph.create().getCluecumberEngine();
        cluecumberEngine.setCustomCssFile(builder.customCssFile);
        cluecumberEngine.setCustomNavigationLinks(builder.customNavigationLinks);
        cluecumberEngine.setCustomPageTitle(builder.customPageTitle);
        cluecumberEngine.setCustomParameters(builder.customParameters);
        cluecumberEngine.setCustomParametersDisplayMode(builder.customParametersDisplayMode);
        cluecumberEngine.setCustomParametersFile(builder.customParametersFile);
        cluecumberEngine.setCustomStatusColorFailed(builder.customStatusColorFailed);
        cluecumberEngine.setCustomStatusColorPassed(builder.customStatusColorPassed);
        cluecumberEngine.setCustomStatusColorSkipped(builder.customStatusColorSkipped);
        cluecumberEngine.setExpandAttachments(builder.expandAttachments);
        cluecumberEngine.setExpandBeforeAfterHooks(builder.expandBeforeAfterHooks);
        cluecumberEngine.setExpandDocStrings(builder.expandDocStrings);
        cluecumberEngine.setExpandStepHooks(builder.expandStepHooks);
        cluecumberEngine.setFailScenariosOnPendingOrUndefinedSteps(builder.failScenariosOnPendingOrUndefinedSteps);
        cluecumberEngine.setLogLevel(builder.logLevel);
        cluecumberEngine.setStartPage(builder.startPage);
    }

    /**
     * The main method to trigger report generation.
     *
     * @param jsonDirectory   The directory of the source JSON files.
     * @param reportDirectory The target directory of the generated report.
     * @throws CluecumberException This is thrown in case of any error.
     */
    public void generateReports(final String jsonDirectory, final String reportDirectory) throws CluecumberException {
        cluecumberEngine.build(jsonDirectory, reportDirectory);
    }

    /**
     * The builder class for a new {@link CluecumberCore} instance.
     */
    public static class Builder {
        private String customCssFile;
        private LinkedHashMap<String, String> customNavigationLinks;
        private String customPageTitle;
        private LinkedHashMap<String, String> customParameters;
        private String customParametersDisplayMode;
        private String customParametersFile;
        private String customStatusColorFailed;
        private String customStatusColorPassed;
        private String customStatusColorSkipped;
        private boolean expandAttachments;
        private boolean expandBeforeAfterHooks;
        private boolean expandDocStrings;
        private boolean expandStepHooks;
        private String logLevel;
        private String startPage;
        private boolean failScenariosOnPendingOrUndefinedSteps;

        /**
         * Finalize the {@link CluecumberCore} builder.
         *
         * @return The final {@link CluecumberCore} instance.
         * @throws CluecumberException Thrown in case of any error.
         */
        public CluecumberCore build() throws CluecumberException {
            return new CluecumberCore(this);
        }

        /**
         * Custom CSS file to override default styles.
         *
         * @param customCssFile The path to a CSS file.
         * @return The {@link Builder}.
         */
        public Builder setCustomCssFile(final String customCssFile) {
            this.customCssFile = customCssFile;
            return this;
        }

        /**
         * Custom navigation links to display at the end of the default navigation.
         *
         * @param customNavigationLinks A map of custom key value pairs (key is the link name, value is the URL).
         * @return The {@link Builder}.
         */
        public Builder setCustomNavigationLinks(final LinkedHashMap<String, String> customNavigationLinks) {
            this.customNavigationLinks = customNavigationLinks;
            return this;
        }


        /**
         * Custom parameters to display at the top of the test report.
         *
         * @param customParameters A map of custom key value pairs.
         * @return The {@link Builder}.
         */
        public Builder setCustomParameters(final LinkedHashMap<String, String> customParameters) {
            this.customParameters = customParameters;
            return this;
        }


        /**
         * Where to display custom parameters.
         *
         * @param customParametersDisplayMode The display mode for custom parameters.
         * @return The {@link Builder}.
         */
        public Builder setCustomParametersDisplayMode(final Settings.CustomParamDisplayMode customParametersDisplayMode) {
            this.customParametersDisplayMode = customParametersDisplayMode.name();
            return this;
        }

        /**
         * Set a file that contains custom parameters as properties.
         *
         * @param customParametersFile The path to a properties file.
         * @return The {@link Builder}.
         */
        public Builder setCustomParametersFile(final String customParametersFile) {
            this.customParametersFile = customParametersFile;
            return this;
        }

        /**
         * Set a custom color for failed scenarios.
         *
         * @param customStatusColorFailed A color in hex format.
         * @return The {@link Builder}.
         */
        public Builder setCustomStatusColorFailed(final String customStatusColorFailed) {
            this.customStatusColorFailed = customStatusColorFailed;
            return this;
        }

        /**
         * Set a custom color for passed scenarios.
         *
         * @param customStatusColorPassed A color in hex format.
         * @return The {@link Builder}.
         */
        public Builder setCustomStatusColorPassed(final String customStatusColorPassed) {
            this.customStatusColorPassed = customStatusColorPassed;
            return this;
        }

        /**
         * Set a custom color for skipped scenarios.
         *
         * @param customStatusColorSkipped A color in hex format.
         * @return The {@link Builder}.
         */
        public Builder setCustomStatusColorSkipped(final String customStatusColorSkipped) {
            this.customStatusColorSkipped = customStatusColorSkipped;
            return this;
        }

        /**
         * Whether to expand attachments or not.
         *
         * @param expandAttachments If true, attachments will be expanded.
         * @return The {@link Builder}.
         */
        public Builder setExpandAttachments(final boolean expandAttachments) {
            this.expandAttachments = expandAttachments;
            return this;
        }

        /**
         * Whether to expand before and after hooks or not.
         *
         * @param expandBeforeAfterHooks If true, before and after hooks will be expanded.
         * @return The {@link Builder}.
         */
        public Builder setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
            this.expandBeforeAfterHooks = expandBeforeAfterHooks;
            return this;
        }

        /**
         * Whether to expand doc strings or not.
         *
         * @param expandDocStrings If true, doc strings will be expanded.
         * @return The {@link Builder}.
         */
        public Builder setExpandDocStrings(final boolean expandDocStrings) {
            this.expandDocStrings = expandDocStrings;
            return this;
        }

        /**
         * Whether to expand step hooks or not.
         *
         * @param expandStepHooks If true, step hooks will be expanded.
         * @return The {@link Builder}.
         */
        public Builder setExpandStepHooks(final boolean expandStepHooks) {
            this.expandStepHooks = expandStepHooks;
            return this;
        }

        /**
         * Set the log level for Cluecumber logs.
         *
         * @param logLevel The desired log level.
         * @return The {@link Builder}.
         */
        public Builder setLogLevel(final CluecumberLogger.CluecumberLogLevel logLevel) {
            this.logLevel = logLevel.name();
            return this;
        }

        /**
         * Set a custom start page for the report.
         *
         * @param startPage The page to set as a start page.
         * @return The {@link Builder}.
         */
        public Builder setStartPage(final Settings.StartPage startPage) {
            this.startPage = startPage.name();
            return this;
        }

        /**
         * Set a custom page tite for the report.
         *
         * @param customPageTitle The custom page title.
         * @return The {@link Builder}.
         */
        public Builder setCustomPageTitle(final String customPageTitle) {
            this.customPageTitle = customPageTitle;
            return this;
        }

        /**
         * Whether to fail scenarios when steps are pending or undefined.
         *
         * @param failScenariosOnPendingOrUndefinedSteps On true, it will fail scenarios with pending or undefined steps.
         * @return The {@link Builder}.
         */
        public Builder setFailScenariosOnPendingOrUndefinedSteps(final boolean failScenariosOnPendingOrUndefinedSteps) {
            this.failScenariosOnPendingOrUndefinedSteps = failScenariosOnPendingOrUndefinedSteps;
            return this;
        }
    }
}
