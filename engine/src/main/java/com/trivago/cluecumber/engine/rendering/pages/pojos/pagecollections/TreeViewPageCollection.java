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
import com.trivago.cluecumber.engine.rendering.pages.renderering.PathFormatter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Page collection for the tree view page.
 */
public class TreeViewPageCollection extends PageCollection {
    private final Set<Path> paths;
    private final Map<Path, List<FeatureAndScenarios>> featuresByPath;
    private final PathFormatter pathFormatter;

    public static class FeatureAndScenarios {
        private final Feature feature;
        private final List<Element> scenarios;

        public FeatureAndScenarios(Feature feature, List<Element> scenarios) {
            this.feature = feature;
            this.scenarios = scenarios;
        }

        public Feature getFeature() {
            return feature;
        }

        public List<Element> getScenarios() {
            return scenarios;
        }
    }

    /**
     * Constructor.
     *
     * @param paths          The paths of the features.
     * @param featuresByPath The map of paths and associated features.
     * @param pathFormatter  The formatter used to display paths.
     * @param pageTitle      The title of the tree view page.
     */
    public TreeViewPageCollection(final Set<Path> paths, final Map<Path, List<FeatureAndScenarios>> featuresByPath, PathFormatter pathFormatter, final String pageTitle) {
        super(pageTitle);
        this.paths = paths;
        this.featuresByPath = featuresByPath;
        this.pathFormatter = pathFormatter;
    }

    /**
     * Get the paths of the features.
     *
     * @return The paths of the features.
     */
    public Set<Path> getPaths() {
        return paths;
    }

    /**
     * Get the map of paths and their features.
     *
     * @return The map of paths and associated features.
     */
    public Map<Path, List<FeatureAndScenarios>> getFeaturesByPath() {
        return featuresByPath;
    }

    /**
     * Get the formatter used to turn the path into a displayable title.
     * @return The formatter used for paths.
     */
    public PathFormatter getPathFormatter() {
        return pathFormatter;
    }

    /**
     * Retrieve the total number of features.
     *
     * @return The count.
     */
    public int getNumberOfFeatures() {
        return featuresByPath.values().stream().mapToInt(List::size).sum();
    }

    /**
     * Retrieve the total number of scenarios.
     *
     * @return The count.
     */
    public int getNumberOfScenarios() {
        return featuresByPath.values().stream().flatMap(List::stream).map(FeatureAndScenarios::getScenarios).mapToInt(List::size).sum();
    }
}

