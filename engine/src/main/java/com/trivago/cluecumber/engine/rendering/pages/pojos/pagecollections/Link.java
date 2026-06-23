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
 * Represents a link in the generated report.
 */
public class Link {
    private final String name;
    private final String target;
    private final LinkType type;
    private final String highlight;

    /**
     * Default constructor.
     *
     * @param name   The name to display.
     * @param target The link target.
     * @param type   The type of link (see {@link LinkType}).
     */
    public Link(String name, String target, LinkType type) {
        this(name, target, type, name);
    }

    /**
     * Constructor with an explicit navigation highlight key.
     *
     * @param name      The name to display.
     * @param target    The link target.
     * @param type      The type of link (see {@link LinkType}).
     * @param highlight The value matched by the page {@code highlight} variable.
     */
    public Link(String name, String target, LinkType type, String highlight) {
        this.name = name;
        this.target = target;
        this.type = type;
        this.highlight = highlight;
    }

    /**
     * Get the name of the link.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the target of the link.
     *
     * @return The target.
     */
    public String getTarget() {
        return target;
    }

    /**
     * Get the type of the link (internal or external).
     *
     * @return The type.
     */
    public LinkType getType() {
        return type;
    }

    /**
     * Get the navigation highlight key for this link.
     *
     * @return The highlight key.
     */
    public String getHighlight() {
        return highlight;
    }

    /**
     * Create a navigation bar separator entry.
     *
     * @return A separator link.
     */
    public static Link navigationSeparator() {
        return new Link("", "", LinkType.SEPARATOR, "");
    }
}
