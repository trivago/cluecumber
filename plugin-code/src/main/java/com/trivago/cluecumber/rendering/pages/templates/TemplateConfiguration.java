/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumber.rendering.pages.templates;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.inject.Singleton;

@Singleton
public class TemplateConfiguration {
    private Configuration cfg;

    public void init(final String basePath) {
        cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(this.getClass(), basePath);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setWhitespaceStripping(true);
        cfg.setLogTemplateExceptions(false);
    }

    public Template getTemplate(final String templateName) throws CluecumberPluginException {
        Template template;
        try {
            template = cfg.getTemplate(templateName + PluginSettings.TEMPLATE_FILE_EXTENSION);
        } catch (Exception e) {
            throw new CluecumberPluginException("Template '" + templateName + "' was not found or not parsable: " +
                    e.getMessage());
        }
        return template;
    }
}
