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
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The base configuration for the Pebble template engine.
 */
@Singleton
public class TemplateConfiguration {
    private PebbleEngine engine;

    /**
     * Default constructor.
     */
    @Inject
    public TemplateConfiguration() {
    }

    /**
     * Initialize Pebble.
     *
     * @param basePath The base path for the templates.
     */
    public void init(final String basePath) {
        final ClasspathLoader loader = new ClasspathLoader();
        String normalizedPath = basePath.startsWith("/") ? basePath.substring(1) : basePath;
        final String prefix = normalizedPath.isEmpty() ? "" : (normalizedPath.endsWith("/") ? normalizedPath : normalizedPath + "/");
        loader.setPrefix(prefix);
        loader.setSuffix(Settings.TEMPLATE_FILE_EXTENSION);
        engine = new PebbleEngine.Builder()
                .loader(loader)
                .strictVariables(false)
                .build();
    }

    /**
     * Retrieve a template by template name.
     *
     * @param templateName The template name without file extension.
     * @return The {@link PebbleTemplate} instance.
     * @throws CluecumberException Thrown on missing or wrong templates.
     */
    public PebbleTemplate getTemplate(final String templateName) throws CluecumberException {
        try {
            return engine.getTemplate(templateName);
        } catch (Exception e) {
            throw new CluecumberException("Template '" + templateName + "' was not found or not parsable: "
                    + e.getMessage());
        }
    }
}
