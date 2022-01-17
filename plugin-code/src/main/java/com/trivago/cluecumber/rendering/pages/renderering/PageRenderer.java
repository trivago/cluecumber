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

package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.CustomParameter;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.PageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageRenderer {

    /**
     * Return the completely rendered template content using a page collection.
     *
     * @param template       The Freemarker template.
     * @param pageCollection The page collection to take the data from.
     * @return The fully rendered content.
     * @throws CluecumberPluginException In case of a rendering error.
     */
    String processedContent(final Template template, final Object pageCollection)
            throws CluecumberPluginException {

        Writer stringWriter = new StringWriter();
        try {
            template.process(pageCollection, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new CluecumberPluginException("Could not render page content: " + e.getMessage());
        }
        return stringWriter.toString();
    }

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
