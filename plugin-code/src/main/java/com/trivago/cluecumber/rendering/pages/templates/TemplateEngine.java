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
        SCENARIO_DETAILS("scenario-detail"),
        CUSTOM_CSS("custom-css"),
        START_PAGE("index");

        private final String fileName;

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

    /**
     * Retrieve a template by {@link Template} type.
     *
     * @param template The template type.
     * @return The requested template.
     * @throws CluecumberPluginException In case the template cannot be found.
     */
    public freemarker.template.Template getTemplate(final Template template) throws CluecumberPluginException {
        return templateConfiguration.getTemplate(template.fileName);
    }
}
