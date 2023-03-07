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

import java.util.List;
import java.util.Objects;

/**
 * Represents a feature file.
 */
public class Feature {
    private final String name;
    private final String description;
    private final int index;
    private final String uri;

    /**
     * Default constructor.
     *
     * @param name        The feature name.
     * @param description The feature description.
     * @param uri         The Cucumber feature file URI.
     * @param index       The internal Cluecumber index.
     */
    public Feature(final String name, final String description, final String uri, final int index) {
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.index = index;
    }

    /**
     * Get the feature name.
     *
     * @return The name string.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the feature description.
     *
     * @return The description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the Cucumber URI for this feature file.
     *
     * @return The URI string.
     */
    public String getUri() {
        return uri;
    }

    /**
     * Get the Cluecumber internal index for this feature.
     *
     * @return The index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Comparator for feature file comparison.
     * Description is not taken into account for now.
     *
     * @param o The {@link Feature} instance to compare to this one.
     * @return true if both features are the same.
     */
    // description is not taken into account in comparison
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return index == feature.index &&
                Objects.equals(name, feature.name);
    }

    /**
     * Get the hash code of this feature file's name and index.
     *
     * @return The hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, index);
    }
}
