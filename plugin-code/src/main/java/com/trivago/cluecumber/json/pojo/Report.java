/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumber.json.pojo;

import java.util.ArrayList;
import java.util.List;

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

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(final List<Element> elements) {
        this.elements = elements;
    }

    public String getName() {
        return !name.isEmpty() ? name : "[Unnamed]";
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public int getFeatureIndex() {
        return featureIndex;
    }

    public void setFeatureIndex(final int featureIndex) {
        this.featureIndex = featureIndex;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    public long getTotalDuration() {
        long totalDurationNanoseconds = 0;
        for (Element element : elements) {
            totalDurationNanoseconds += element.getTotalDuration();
        }
        return totalDurationNanoseconds;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
