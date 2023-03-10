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

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;

import java.util.List;
import java.util.Map;

/**
 * Page collection for the tree view page.
 */
public class TreeViewPageCollection extends PageCollection {
    private final Map<Feature, List<Element>> elements;

    /**
     * Constructor.
     *
     * @param elements  The map of features and associated scenarios.
     * @param pageTitle The title of the tree view page.
     */
    public TreeViewPageCollection(final Map<Feature, List<Element>> elements, final String pageTitle) {
        super(pageTitle);
        this.elements = elements;
    }

    /**
     * Get the list of features and their scenarios.
     *
     * @return The map of features and associated scenarios.
     */
    public Map<Feature, List<Element>> getElements() {
        return elements;
    }

    /**
     * Retrieve the total number of features.
     *
     * @return The count.
     */
    public int getNumberOfFeatures() {
        return elements.size();
    }

    /**
     * Retrieve the total number of scenarios.
     *
     * @return The count.
     */
    public int getNumberOfScenarios() {
        return elements.values().stream().mapToInt(List::size).sum();
    }
}

