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

package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TemplateEngine {
    private final TemplateConfiguration templateConfiguration;

    public enum Template {
        ALL_FEATURES("feature-summary"),
        ALL_SCENARIOS("scenario-summary"),
        SCENARIO_SEQUENCE("scenario-sequence"),
        ALL_STEPS("step-summary"),
        ALL_TAGS("tag-summary"),
        SCENARIO_DETAILS("scenario-detail");

        private String fileName;

        Template(final String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    @Inject
    TemplateEngine(final TemplateConfiguration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
        templateConfiguration.init(PluginSettings.BASE_TEMPLATE_PATH);
    }

    public freemarker.template.Template getTemplate(final Template template) throws CluecumberPluginException {
        return templateConfiguration.getTemplate(template.fileName);
    }
//
//    public String getRenderedScenarioSummaryPageContent(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
//        return allScenariosPageRenderer.getRenderedContent(
//                allScenariosPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE)
//        );
//    }
//
//    String getRenderedScenarioSequencePageContent(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
//        return allScenariosPageRenderer.getRenderedContent(
//                allScenariosPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SEQUENCE_TEMPLATE)
//        );
//    }
//
//    String getRenderedScenarioSummaryPageContentByTagFilter(
//            final AllScenariosPageCollection allScenariosPageCollection,
//            final Tag tag) throws CluecumberPluginException {
//
//        return allScenariosPageRenderer.getRenderedContentByTagFilter(
//                allScenariosPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE),
//                tag
//        );
//    }
//
//    String getRenderedScenarioSummaryPageContentByStepFilter(
//            final AllScenariosPageCollection allScenariosPageCollection,
//            final Step step) throws CluecumberPluginException {
//
//        return allScenariosPageRenderer.getRenderedContentByStepFilter(
//                allScenariosPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE),
//                step
//        );
//    }
//
//    String getRenderedScenarioSummaryPageContentByFeatureFilter(
//            final AllScenariosPageCollection allScenariosPageCollection,
//            final Feature feature) throws CluecumberPluginException {
//
//        return allScenariosPageRenderer.getRenderedContentByFeatureFilter(
//                allScenariosPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE),
//                feature
//        );
//    }
//
//    String getRenderedScenarioDetailPageContent(final ScenarioDetailsPageCollection scenarioDetailsPageCollection) throws CluecumberPluginException {
//        return scenarioDetailsPageRenderer.getRenderedContent(
//                scenarioDetailsPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.SCENARIO_DETAIL_TEMPLATE)
//        );
//    }
//
//    String getRenderedTagSummaryPageContent(final AllTagsPageCollection allTagsPageCollection) throws CluecumberPluginException {
//        return allTagsPageRenderer.getRenderedContent(
//                allTagsPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.TAG_SUMMARY_TEMPLATE)
//        );
//    }
//
//    String getRenderedStepSummaryPageContent(final AllStepsPageCollection allStepsPageCollection) throws CluecumberPluginException {
//        return allStepsPageRenderer.getRenderedContent(
//                allStepsPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.STEP_SUMMARY_TEMPLATE)
//        );
//    }
//
//    String getRenderedFeatureSummaryPageContent(final AllFeaturesPageCollection allFeaturesPageCollection) throws CluecumberPluginException {
//        return allFeaturesPageRenderer.getRenderedContent(
//                allFeaturesPageCollection,
//                templateConfiguration.getTemplate(PluginSettings.FEATURE_SUMMARY_TEMPLATE)
//        );
//    }
}
