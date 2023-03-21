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

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class manages the Freemarker templates.
 */
@Singleton
public class TemplateEngine {
    private final TemplateConfiguration templateConfiguration;

    /**
     * Constructor for dependency injection.
     *
     * @param templateConfiguration The {@link TemplateConfiguration} instance.
     */
    @Inject
    public TemplateEngine(final TemplateConfiguration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
        templateConfiguration.init(Settings.BASE_TEMPLATE_PATH);
    }

    /**
     * Retrieve a template by {@link Template} type.
     *
     * @param template The template type.
     * @return The requested template.
     * @throws CluecumberException In case the template cannot be found.
     */
    public freemarker.template.Template getTemplate(final Template template) throws CluecumberException {
        return templateConfiguration.getTemplate(template.fileName);
    }

    /**
     * The enum that defines the different Freemarker template names.
     */
    public enum Template {
        /**
         * Tree view template.
         */
        TREE_VIEW("tree-view"),
        /**
         * Feature overview template.
         */
        ALL_FEATURES("feature-summary"),
        /**
         * Scenario overview template.
         */
        ALL_SCENARIOS("scenario-summary"),
        /**
         * Scenario sequence template.
         */
        SCENARIO_SEQUENCE("scenario-sequence"),
        /**
         * Step overview template.
         */
        ALL_STEPS("step-summary"),
        /**
         * Tag overview template.
         */
        ALL_TAGS("tag-summary"),
        /**
         * Scenario detail template.
         */
        SCENARIO_DETAILS("scenario-detail"),
        /**
         * Template for a custom CSS file.
         */
        CUSTOM_CSS("custom-css"),
        /**
         * Start page template.
         */
        START_PAGE("index");

        private final String fileName;

        Template(final String fileName) {
            this.fileName = fileName;
        }

        /**
         * Get the file name of the Freemarker template.
         *
         * @return The template file name.
         */
        public String getFileName() {
            return fileName;
        }
    }
}
