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
import com.trivago.rta.rendering.pages.ScenarioDetailPageRenderer;
import com.trivago.rta.rendering.pages.StartPageRenderer;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TemplateEngine {
    private TemplateConfiguration templateConfiguration;
    private final StartPageRenderer startPageRenderer;
    private final ScenarioDetailPageRenderer scenarioDetailPageRenderer;

    @Inject
    public TemplateEngine(
            final TemplateConfiguration templateConfiguration,
            final StartPageRenderer startPageRenderer,
            final ScenarioDetailPageRenderer scenarioDetailPageRenderer
    ) {
        this.templateConfiguration = templateConfiguration;
        this.startPageRenderer = startPageRenderer;
        this.scenarioDetailPageRenderer = scenarioDetailPageRenderer;
    }

    void init(final Class rootClass, final String basePath) {
        templateConfiguration.init(rootClass, basePath);
    }

    String getRenderedStartPage(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        return startPageRenderer.getRenderedContent(
                startPageCollection,
                templateConfiguration.getTemplate(PluginSettings.START_PAGE_NAME)
        );
    }

    String getRenderedDetailPage(final DetailPageCollection detailPageCollection) throws CluecumberPluginException {
        return scenarioDetailPageRenderer.getRenderedContent(
                detailPageCollection,
                templateConfiguration.getTemplate(PluginSettings.DETAIL_PAGE_NAME)
        );
    }
}
