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

import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

/**
 * Custom report parameters.
 */
public class CustomParameter {
    private final String key;
    private final String value;

    /**
     * Constructor.
     *
     * @param key   They parameter name to display.
     * @param value The parameter value to display.
     */
    public CustomParameter(final String key, final String value) {
        this.key = key;
        this.value = value;
    }


    /**
     * Return the parameter name.
     *
     * @return The name.
     */
    public String getKey() {
        return key;
    }

    /**
     * Return the parameter value.
     *
     * @return The value.
     */
    public String getValue() {
        return value != null ? value.trim() : "";
    }

    /**
     * Check if the value is a URL.
     *
     * @return true if it is a URL.
     */
    public boolean isUrl() {
        return RenderingUtils.isUrl(getValue());
    }

    /**
     * Check if the value is a relative URL.
     *
     * @return true if it is a relative URL.
     */
    public boolean isRelativeUrl() {
        return RenderingUtils.isRelativeUrl(getValue());
    }
}
