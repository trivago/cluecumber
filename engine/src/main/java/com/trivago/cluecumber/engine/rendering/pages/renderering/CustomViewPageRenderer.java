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
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.CustomView;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.CustomViewPageCollection;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The renderer for custom embedded view pages.
 */
@Singleton
public class CustomViewPageRenderer extends PageRenderer {

    private final PropertyManager propertyManager;

    /**
     * Constructor for dependency injection.
     *
     * @param propertyManager The {@link PropertyManager} instance.
     */
    @Inject
    public CustomViewPageRenderer(final PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    /**
     * Return the rendered page content.
     *
     * @param customView The {@link CustomView} instance.
     * @param template   The Pebble template.
     * @return The rendered page content as a string.
     * @throws CluecumberException Thrown in case of any error.
     */
    public String getRenderedContent(final CustomView customView, final PebbleTemplate template)
            throws CluecumberException {

        CustomViewPageCollection pageCollection = new CustomViewPageCollection(
                propertyManager.getCustomPageTitle(),
                customView.getDisplayName(),
                customView.getUrl(),
                customView.getHighlight()
        );

        return processedContent(template, pageCollection, propertyManager.getNavigationLinks());
    }
}
