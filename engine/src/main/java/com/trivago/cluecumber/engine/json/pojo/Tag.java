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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * This represents a Cucumber tag.
 */
public class Tag {
    private String name;

    /**
     * Get the tag name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the tag name.
     *
     * @param name The tag name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Turns the tag into a name that can be used within a URL.
     *
     * @return The sanitized tag name.
     */
    public String getUrlFriendlyName() {
        String escapedTag = RenderingUtils.escapeHTML(getName()).replace("@", "");
        return URLEncoder.encode(escapedTag, StandardCharsets.UTF_8).replace("%", "");
    }

    /**
     * Tag comparison function.
     *
     * @param o The tag to compare to.
     * @return true if the names match.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    /**
     * Default hash code function that hashes the tag name.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
