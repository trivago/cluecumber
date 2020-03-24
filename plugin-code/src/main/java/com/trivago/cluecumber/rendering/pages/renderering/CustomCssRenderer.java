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

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.properties.PropertyManager;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

@Singleton
public class CustomCssRenderer {

    private final PropertyManager propertyManager;

    @Inject
    public CustomCssRenderer(final PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    /**
     * Return the completely rendered custom css content.
     *
     * @param template           The Freemarker template.
     * @return The fully rendered content.
     * @throws CluecumberPluginException In case of a rendering error.
     */
    public String getRenderedCustomCssContent(final Template template) throws CluecumberPluginException {
        Writer stringWriter = new StringWriter();

        CustomStatusColors customStatusColors = new CustomStatusColors(
                propertyManager.getCustomStatusColorPassed(),
                propertyManager.getCustomStatusColorFailed(),
                propertyManager.getCustomStatusColorSkipped()
        );

        try {
            template.process(customStatusColors, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new CluecumberPluginException("Could not render custom css content: " + e.getMessage());
        }
        return stringWriter.toString();
    }

    @SuppressWarnings("unused")
    public static class CustomStatusColors {

        private final String passedColor;
        private final String failedColor;
        private final String skippedColor;

        CustomStatusColors(String passedColor, String failedColor, String skippedColor) {
            this.passedColor = passedColor;
            this.failedColor = failedColor;
            this.skippedColor = skippedColor;
        }

        public String getPassedColor() {
            return passedColor;
        }

        public String getFailedColor() {
            return failedColor;
        }

        public String getSkippedColor() {
            return skippedColor;
        }
    }
}

