/*
 * Copyright 2018 trivago N.V.
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

package com.trivago.rta.rendering.pages;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.properties.PropertyManager;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TagSummaryPageRenderer extends PageRenderer {

    private PropertyManager propertyManager;

    @Inject
    public TagSummaryPageRenderer(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public String getRenderedContent(
            final Template template)
            throws CluecumberPluginException {
        return "Tag Summary";
    }
}
