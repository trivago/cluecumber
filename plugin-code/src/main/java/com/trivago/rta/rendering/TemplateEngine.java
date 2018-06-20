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
import com.trivago.rta.rendering.pages.pojos.pagecollections.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.FeatureSummaryPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioSummaryPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.TagSummaryPageCollection;
import com.trivago.rta.rendering.pages.renderers.FeatureSummaryPageRenderer;
import com.trivago.rta.rendering.pages.renderers.ScenarioDetailPageRenderer;
import com.trivago.rta.rendering.pages.renderers.ScenarioSummaryPageRenderer;
import com.trivago.rta.rendering.pages.renderers.TagSummaryPageRenderer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TemplateEngine {
    private final TemplateConfiguration templateConfiguration;
    private final FeatureSummaryPageRenderer featureSummaryPageRenderer;
    private final TagSummaryPageRenderer tagSummaryPageRenderer;
    private final ScenarioDetailPageRenderer scenarioDetailPageRenderer;
    private final ScenarioSummaryPageRenderer scenarioSummaryPageRenderer;

    @Inject
    public TemplateEngine(
            final TemplateConfiguration templateConfiguration,
            final FeatureSummaryPageRenderer featureSummaryPageRenderer,
            final TagSummaryPageRenderer tagSummaryPageRenderer,
            final ScenarioDetailPageRenderer scenarioDetailPageRenderer,
            final ScenarioSummaryPageRenderer scenarioSummaryPageRenderer
    ) {
        this.templateConfiguration = templateConfiguration;
        this.featureSummaryPageRenderer = featureSummaryPageRenderer;
        this.scenarioSummaryPageRenderer = scenarioSummaryPageRenderer;
        this.scenarioDetailPageRenderer = scenarioDetailPageRenderer;
        this.tagSummaryPageRenderer = tagSummaryPageRenderer;

        templateConfiguration.init(this.getClass(), PluginSettings.BASE_TEMPLATE_PATH);
    }

    String getRenderedScenarioSummaryPageContent(final ScenarioSummaryPageCollection scenarioSummaryPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(scenarioSummaryPageRenderer.getRenderedContent(
                scenarioSummaryPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE)
        ));
    }

    String getRenderedScenarioSummaryPageContentByTagFilter(
            final ScenarioSummaryPageCollection scenarioSummaryPageCollection,
            final Tag tag) throws CluecumberPluginException {

        return RenderingUtils.prettifyHtml(scenarioSummaryPageRenderer.getRenderedContentByTagFilter(
                scenarioSummaryPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE),
                tag
        ));
    }

    String getRenderedScenarioSummaryPageContentByFeatureFilter(
            final ScenarioSummaryPageCollection scenarioSummaryPageCollection,
            final Feature feature) throws CluecumberPluginException {

        return RenderingUtils.prettifyHtml(scenarioSummaryPageRenderer.getRenderedContentByFeatureFilter(
                scenarioSummaryPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE),
                feature
        ));
    }

    String getRenderedScenarioDetailPageContent(final DetailPageCollection detailPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(scenarioDetailPageRenderer.getRenderedContent(
                detailPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_DETAIL_TEMPLATE)
        ));
    }

    String getRenderedTagSummaryPageContent(final TagSummaryPageCollection tagSummaryPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(tagSummaryPageRenderer.getRenderedContent(
                tagSummaryPageCollection,
                templateConfiguration.getTemplate(PluginSettings.TAG_SUMMARY_TEMPLATE)
        ));
    }

    String getRenderedFeatureSummaryPageContent(final FeatureSummaryPageCollection featureSummaryPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(featureSummaryPageRenderer.getRenderedContent(
                featureSummaryPageCollection,
                templateConfiguration.getTemplate(PluginSettings.FEATURE_SUMMARY_TEMPLATE)
        ));
    }
}
