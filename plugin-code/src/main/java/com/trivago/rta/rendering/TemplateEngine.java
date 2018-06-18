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
import com.trivago.rta.rendering.pages.renderers.FeatureSummaryPageRenderer;
import com.trivago.rta.rendering.pages.renderers.ScenarioDetailPageRenderer;
import com.trivago.rta.rendering.pages.renderers.StartPageRenderer;
import com.trivago.rta.rendering.pages.renderers.TagSummaryPageRenderer;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.FeatureSummaryPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import com.trivago.rta.rendering.pages.pojos.TagSummaryPageCollection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TemplateEngine {
    private final TemplateConfiguration templateConfiguration;
    private final FeatureSummaryPageRenderer featureSummaryPageRenderer;
    private final TagSummaryPageRenderer tagSummaryPageRenderer;
    private final ScenarioDetailPageRenderer scenarioDetailPageRenderer;
    private final StartPageRenderer startPageRenderer;

    @Inject
    public TemplateEngine(
            final TemplateConfiguration templateConfiguration,
            final FeatureSummaryPageRenderer featureSummaryPageRenderer,
            final TagSummaryPageRenderer tagSummaryPageRenderer,
            final ScenarioDetailPageRenderer scenarioDetailPageRenderer,
            final StartPageRenderer startPageRenderer
    ) {
        this.templateConfiguration = templateConfiguration;
        this.featureSummaryPageRenderer = featureSummaryPageRenderer;
        this.startPageRenderer = startPageRenderer;
        this.scenarioDetailPageRenderer = scenarioDetailPageRenderer;
        this.tagSummaryPageRenderer = tagSummaryPageRenderer;

        templateConfiguration.init(this.getClass(), PluginSettings.BASE_TEMPLATE_PATH);
    }

    String getRenderedStartPageContent(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        return RenderingUtils.prettifyHtml(startPageRenderer.getRenderedContent(
                startPageCollection,
                templateConfiguration.getTemplate(PluginSettings.SCENARIO_OVERVIEW_TEMPLATE)
        ));
    }

    String getRenderedDetailPageContent(final DetailPageCollection detailPageCollection) throws CluecumberPluginException {
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
