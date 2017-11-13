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
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class TemplateEngine {
    private static final String START_PAGE_TEMPLATE = "index.html";
    private static final String DETAIL_PAGE_TEMPLATE = "scenario-detail/detail.html";

    private final StartPageRenderer startPageRenderer;
    private final DetailPageRenderer detailPageRenderer;
    private Configuration cfg;

    @Inject
    public TemplateEngine(
            final StartPageRenderer startPageRenderer,
            final DetailPageRenderer detailPageRenderer
    ) {
        this.startPageRenderer = startPageRenderer;
        this.detailPageRenderer = detailPageRenderer;
    }

    void init(final Class rootClass, final String basePath) {
        cfg = new Configuration(Configuration.VERSION_2_3_26);
        cfg.setClassForTemplateLoading(rootClass, basePath);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    String getRenderedStartPage(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        return startPageRenderer.getRenderedContent(startPageCollection, getTemplate(START_PAGE_TEMPLATE));
    }

    String getRenderedDetailPage(final DetailPageCollection detailPageCollection) throws CluecumberPluginException {
        return detailPageRenderer.getRenderedContent(detailPageCollection, getTemplate(DETAIL_PAGE_TEMPLATE));
    }

    private Template getTemplate(final String templateName) {
        Template template = null;
        try {
            template = cfg.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }

}
