/*
 * Copyright 2017 trivago N.V.
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

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.rendering.pages.DetailPageRenderer;
import com.trivago.rta.rendering.pages.StartPageRenderer;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TemplateEngine {
    private static final String START_PAGE_TEMPLATE = "index.html";
    private static final String DETAIL_PAGE_TEMPLATE = "scenario-detail/detail.html";

    private TemplateConfiguration templateConfiguration;
    private final StartPageRenderer startPageRenderer;
    private final DetailPageRenderer detailPageRenderer;

    @Inject
    public TemplateEngine(
            final TemplateConfiguration templateConfiguration,
            final StartPageRenderer startPageRenderer,
            final DetailPageRenderer detailPageRenderer
    ) {
        this.templateConfiguration = templateConfiguration;
        this.startPageRenderer = startPageRenderer;
        this.detailPageRenderer = detailPageRenderer;
    }

    void init(final Class rootClass, final String basePath) {
        templateConfiguration.init(rootClass, basePath);
    }

    String getRenderedStartPage(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        return startPageRenderer.getRenderedContent(startPageCollection, templateConfiguration.getTemplate(START_PAGE_TEMPLATE));
    }

    String getRenderedDetailPage(final DetailPageCollection detailPageCollection) throws CluecumberPluginException {
        return detailPageRenderer.getRenderedContent(detailPageCollection, templateConfiguration.getTemplate(DETAIL_PAGE_TEMPLATE));
    }
}
