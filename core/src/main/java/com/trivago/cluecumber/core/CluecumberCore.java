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

package com.trivago.cluecumber.core;

import com.trivago.cluecumber.engine.CluecumberEngine;
import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.dagger.DaggerCluecumberCoreGraph;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;

import java.util.LinkedHashMap;

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

    public void generateReports(final String jsonDirectory, final String reportDirectory) throws CluecumberException {
        cluecumberEngine.build(jsonDirectory, reportDirectory);
    }

    public static class Builder {
        public LinkedHashMap<String, String> customViews;
        protected String customCssFile;
        protected LinkedHashMap<String, String> customNavigationLinks;
        protected String customPageTitle;
        protected LinkedHashMap<String, String> customParameters;
        protected String customParametersDisplayMode;
        protected String customParametersFile;
        protected String customStatusColorFailed;
        protected String customStatusColorPassed;
        protected String customStatusColorSkipped;
        protected boolean expandAttachments;
        protected boolean expandBeforeAfterHooks;
        protected boolean expandDocStrings;
        protected boolean expandStepHooks;
        protected String logLevel;
        protected String startPage;
        private boolean failScenariosOnPendingOrUndefinedSteps;

        public Builder() {
        }

        public CluecumberCore build() throws CluecumberException {
            return new CluecumberCore(this);
        }

        public Builder setCustomCssFile(final String customCssFile) {
            this.customCssFile = customCssFile;
            return this;
        }

        public Builder setCustomNavigationLinks(final LinkedHashMap<String, String> customNavigationLinks) {
            this.customNavigationLinks = customNavigationLinks;
            return this;
        }

        public Builder setCustomParameters(final LinkedHashMap<String, String> customParameters) {
            this.customParameters = customParameters;
            return this;
        }

        public Builder setCustomParametersDisplayMode(final PluginSettings.CustomParamDisplayMode customParametersDisplayMode) {
            this.customParametersDisplayMode = customParametersDisplayMode.name();
            return this;
        }

        public Builder setCustomParametersFile(final String customParametersFile) {
            this.customParametersFile = customParametersFile;
            return this;
        }

        public Builder setCustomStatusColorFailed(final String customStatusColorFailed) {
            this.customStatusColorFailed = customStatusColorFailed;
            return this;
        }

        public Builder setCustomStatusColorPassed(final String customStatusColorPassed) {
            this.customStatusColorPassed = customStatusColorPassed;
            return this;
        }

        public Builder setCustomStatusColorSkipped(final String customStatusColorSkipped) {
            this.customStatusColorSkipped = customStatusColorSkipped;
            return this;
        }

        public Builder setExpandAttachments(final boolean expandAttachments) {
            this.expandAttachments = expandAttachments;
            return this;
        }

        public Builder setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
            this.expandBeforeAfterHooks = expandBeforeAfterHooks;
            return this;
        }

        public Builder setExpandDocStrings(final boolean expandDocStrings) {
            this.expandDocStrings = expandDocStrings;
            return this;
        }

        public Builder setExpandStepHooks(final boolean expandStepHooks) {
            this.expandStepHooks = expandStepHooks;
            return this;
        }

        public Builder setLogLevel(final CluecumberLogger.CluecumberLogLevel logLevel) {
            this.logLevel = logLevel.name();
            return this;
        }

        public Builder setStartPage(final PluginSettings.StartPage startPage) {
            this.startPage = startPage.name();
            return this;
        }

        public Builder setCustomPageTitle(final String customPageTitle) {
            this.customPageTitle = customPageTitle;
            return this;
        }

        public Builder setFailScenariosOnPendingOrUndefinedSteps(final boolean failScenariosOnPendingOrUndefinedSteps) {
            this.failScenariosOnPendingOrUndefinedSteps = failScenariosOnPendingOrUndefinedSteps;
            return this;
        }
    }
}
