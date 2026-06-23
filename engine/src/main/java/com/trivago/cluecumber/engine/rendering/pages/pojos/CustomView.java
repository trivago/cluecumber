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
package com.trivago.cluecumber.engine.rendering.pages.pojos;

/**
 * A custom page that embeds external content in an iframe.
 */
public class CustomView {
    private final String displayName;
    private final String url;
    private final String slug;
    private final String highlight;

    /**
     * Constructor.
     *
     * @param displayName The navigation label.
     * @param url         The URL to embed.
     * @param slug        The generated page file slug.
     * @param highlight   The navigation highlight key.
     */
    public CustomView(final String displayName, final String url, final String slug, final String highlight) {
        this.displayName = displayName;
        this.url = url;
        this.slug = slug;
        this.highlight = highlight;
    }

    /**
     * Get the navigation label.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the URL to embed.
     *
     * @return The embed URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the generated page file slug.
     *
     * @return The slug.
     */
    public String getSlug() {
        return slug;
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
