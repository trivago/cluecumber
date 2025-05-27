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
package com.trivago.cluecumber.engine.rendering.pages.templates;

import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The base configuration for the Freemarker template engine.
 */
@Singleton
public class TemplateConfiguration {
    private Configuration cfg;

    /**
     * Default constructor.
     */
    @Inject
    public TemplateConfiguration() {
    }

    /**
     * Initialize Freemarker.
     *
     * @param basePath The base path for the templates.
     */
    public void init(final String basePath) {
        cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassForTemplateLoading(this.getClass(), basePath);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setWhitespaceStripping(true);
        cfg.setLogTemplateExceptions(false);
    }

    /**
     * Retrieve a template by template name.
     *
     * @param templateName The template name without file extension.
     * @return The {@link Template} instance.
     * @throws CluecumberException Thrown on missing or wrong templates.s
     */
    public Template getTemplate(final String templateName) throws CluecumberException {
        Template template;
        try {
            template = cfg.getTemplate(templateName + Settings.TEMPLATE_FILE_EXTENSION);
        } catch (Exception e) {
            throw new CluecumberException("Template '" + templateName + "' was not found or not parsable: " +
                    e.getMessage());
        }
        return template;
    }
}
