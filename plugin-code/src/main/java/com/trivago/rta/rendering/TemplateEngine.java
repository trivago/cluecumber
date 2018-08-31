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

package com.trivago.rta.rendering;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Tag;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import com.trivago.rta.rendering.pages.renderers.AllFeaturesPageRenderer;
import com.trivago.rta.rendering.pages.renderers.AllScenariosPageRenderer;
import com.trivago.rta.rendering.pages.renderers.AllTagsPageRenderer;
import com.trivago.rta.rendering.pages.renderers.ScenarioDetailsPageRenderer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TemplateEngine {
    private final TemplateConfiguration templateConfiguration;
    private final AllFeaturesPageRenderer allFeaturesPageRenderer;
    private final AllTagsPageRenderer allTagsPageRenderer;
    private final ScenarioDetailsPageRenderer scenarioDetailsPageRenderer;
    private final AllScenariosPageRenderer allScenariosPageRenderer;

    @Inject
    public TemplateEngine(
            final TemplateConfiguration templateConfiguration,
            final AllFeaturesPageRenderer allFeaturesPageRenderer,
            final AllTagsPageRenderer allTagsPageRenderer,
            final ScenarioDetailsPageRenderer scenarioDetailsPageRenderer,
            final AllScenariosPageRenderer allScenariosPageRenderer
    ) {
        this.templateConfiguration = templateConfiguration;
        this.allFeaturesPageRenderer = allFeaturesPageRenderer;
        this.allScenariosPageRenderer = allScenariosPageRenderer;
        this.scenarioDetailsPageRenderer = scenarioDetailsPageRenderer;
        this.allTagsPageRenderer = allTagsPageRenderer;

        templateConfiguration.init(this.getClass(), PluginSettings.BASE_TEMPLATE_PATH);
    }

    String getRenderedScenarioSummaryPageContent(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(allScenariosPageRenderer.getRenderedContent(
                allScenariosPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE)
        ));
    }

    String getRenderedScenarioSequencePageContent(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(allScenariosPageRenderer.getRenderedContent(
                allScenariosPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SEQUENCE_TEMPLATE)
        ));
    }

    String getRenderedScenarioSummaryPageContentByTagFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Tag tag) throws CluecumberPluginException {

        return RenderingUtils.prettifyHtml(allScenariosPageRenderer.getRenderedContentByTagFilter(
                allScenariosPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE),
                tag
        ));
    }

    String getRenderedScenarioSummaryPageContentByFeatureFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Feature feature) throws CluecumberPluginException {

        return RenderingUtils.prettifyHtml(allScenariosPageRenderer.getRenderedContentByFeatureFilter(
                allScenariosPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE),
                feature
        ));
    }

    String getRenderedScenarioDetailPageContent(final ScenarioDetailsPageCollection scenarioDetailsPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(scenarioDetailsPageRenderer.getRenderedContent(
                scenarioDetailsPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_DETAIL_TEMPLATE)
        ));
    }

    String getRenderedTagSummaryPageContent(final AllTagsPageCollection allTagsPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(allTagsPageRenderer.getRenderedContent(
                allTagsPageCollection,
                templateConfiguration.getTemplate(PluginSettings.TAG_SUMMARY_TEMPLATE)
        ));
    }

    String getRenderedFeatureSummaryPageContent(final AllFeaturesPageCollection allFeaturesPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(allFeaturesPageRenderer.getRenderedContent(
                allFeaturesPageCollection,
                templateConfiguration.getTemplate(PluginSettings.FEATURE_SUMMARY_TEMPLATE)
        ));
    }
}
