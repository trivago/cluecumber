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
package com.trivago.cluecumber.engine.json.pojo;

import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

/**
 * The class to hold docstring information of steps.
 */
public class DocString {
    private int line;
    private String value = "";

    /**
     * Get the line number.
     *
     * @return The line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Set the line number.
     *
     * @param line The line number.
     */
    public void setLine(final int line) {
        this.line = line;
    }

    /**
     * Get the value.
     *
     * @return The value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value.
     *
     * @param value The value.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Get this doc string with clickable HTML links.
     *
     * @return The converted doc string.
     */
    public String returnWithClickableLinks() {
        return RenderingUtils.turnUrlsIntoLinks(RenderingUtils.escapeHTML(value));
    }
}
