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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a feature.
 */
public class Report implements Cloneable {
    private int line;
    private List<Element> elements = new ArrayList<>();
    private String name = "";
    private String description = "";
    private String id = "";
    private String keyword = "";
    private String uri = "";
    private List<Tag> tags = new ArrayList<>();

    private transient int featureIndex = -1;

    /**
     * Get the line number where this feature starts.
     *
     * @return The line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Set the line number where this feature starts.
     *
     * @param line The line number.
     */
    public void setLine(final int line) {
        this.line = line;
    }

    /**
     * Get the associated scenarios.
     *
     * @return The {@link Element} list.
     */
    public List<Element> getElements() {
        return elements;
    }

    /**
     * Set the associated scenarios.
     *
     * @param elements The {@link Element} list.
     */
    public void setElements(final List<Element> elements) {
        this.elements = elements;
    }

    /**
     * Get the feature name.
     *
     * @return The name.
     */
    public String getName() {
        return !name.isEmpty() ? name : "[Unnamed]";
    }

    /**
     * Set the feature name.
     *
     * @param name The name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the feature description.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the feature description.
     *
     * @param description The description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get the feature id.
     *
     * @return The string id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the feature id.
     *
     * @param id The string id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Get the feature URI.
     *
     * @return The URI.
     */
    public String getUri() {
        return uri;
    }

    /**
     * Set the feature URI.
     *
     * @param uri The URI.
     */
    public void setUri(final String uri) {
        this.uri = uri;
    }

    /**
     * Get the feature index.
     *
     * @return The index.
     */
    public int getFeatureIndex() {
        return featureIndex;
    }

    /**
     * Set the feature index.
     *
     * @param featureIndex The index.
     */
    public void setFeatureIndex(final int featureIndex) {
        this.featureIndex = featureIndex;
    }

    /**
     * Get the feature tags.
     *
     * @return The {@link Tag} list.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Set the feature tags.
     *
     * @param tags The {@link Tag} list.
     */
    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Get the total duration.
     *
     * @return The duration in nanoseconds.
     */
    public long getTotalDuration() {
        long totalDurationNanoseconds = 0;
        for (Element element : elements) {
            totalDurationNanoseconds += element.getTotalDuration();
        }
        return totalDurationNanoseconds;
    }

    /**
     * Clone method for page collection clones.
     *
     * @return The clone.
     * @throws CloneNotSupportedException Thrown if cloning is not possible.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
