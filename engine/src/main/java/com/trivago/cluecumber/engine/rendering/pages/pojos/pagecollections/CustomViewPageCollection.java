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
package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

/**
 * Page collection for a custom embedded view page.
 */
public class CustomViewPageCollection extends PageCollection {
    private final String viewTitle;
    private final String viewUrl;
    private final String highlight;

    /**
     * Constructor.
     *
     * @param pageTitle The browser page title prefix.
     * @param viewTitle The headline shown on the page.
     * @param viewUrl   The URL embedded in the iframe.
     * @param highlight The navigation highlight key.
     */
    public CustomViewPageCollection(
            final String pageTitle,
            final String viewTitle,
            final String viewUrl,
            final String highlight
    ) {
        super(pageTitle);
        this.viewTitle = viewTitle;
        this.viewUrl = viewUrl;
        this.highlight = highlight;
    }

    /**
     * Get the headline shown on the page.
     *
     * @return The view title.
     */
    public String getViewTitle() {
        return viewTitle;
    }

    /**
     * Get the URL embedded in the iframe.
     *
     * @return The embed URL.
     */
    public String getViewUrl() {
        return viewUrl;
    }

    /**
     * Get the navigation highlight key.
     *
     * @return The highlight key.
     */
    public String getHighlight() {
        return highlight;
    }
}
