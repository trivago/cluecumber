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
package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.pojos.CustomParameter;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.Link;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.PageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The base class for page rendering.
 */
public class PageRenderer {

    /**
     * Default constructor.
     */
    public PageRenderer() {
        // Default constructor
    }

    /**
     * Return the completely rendered template content using a page collection.
     *
     * @param template       The Freemarker template.
     * @param pageCollection The page collection to take the data from.
     * @return The fully rendered content.
     * @throws CluecumberException In case of a rendering error.
     */
    String processedContent(final Template template, final Object pageCollection, final List<Link> navigation)
            throws CluecumberException {

        if (pageCollection instanceof PageCollection) {
            ((PageCollection) pageCollection).setNavigationLinks(navigation);
        }

        Writer stringWriter = new StringWriter();
        try {
            template.process(pageCollection, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new CluecumberException("Could not render page content: " + e.getMessage());
        }
        return stringWriter.toString();
    }

    /**
     * Add converted custom parameters to the page collection.
     *
     * @param pageCollection     The {@link PageCollection} instance.
     * @param customParameterMap The map of custom parameter key value pairs.
     */
    protected void addCustomParametersToReportDetails(final PageCollection pageCollection,
                                                      final Map<String, String> customParameterMap) {

        if (customParameterMap == null || customParameterMap.isEmpty()) {
            return;
        }
        List<CustomParameter> customParameters = new ArrayList<>();
        customParameterMap.forEach((key, value) -> {
            if (value != null && !value.trim().isEmpty()) {
                String newKey = key.replace("_", " ");
                CustomParameter customParameter = new CustomParameter(newKey, value);
                customParameters.add(customParameter);
            }
        });

        pageCollection.setCustomParameters(customParameters);
    }
}
