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
package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.TreeViewPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.TreeViewPageCollection.FeatureAndScenarios;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * The renderer for the feature and scenario tree view page.
 */
@Singleton
public class TreeViewPageRenderer extends PageRenderer {

    private static final Path ROOT_PATH = Path.of("/");
    private final PropertyManager propertyManager;

    /**
     * Constructor for dependency injection.
     *
     * @param propertyManager The {@link PropertyManager} instance.
     */
    @Inject
    public TreeViewPageRenderer(
            final PropertyManager propertyManager
    ) {
        this.propertyManager = propertyManager;
    }

    /**
     * Get the rendered site content.
     *
     * @param allFeaturesPageCollection  The {@link AllFeaturesPageCollection} instance.
     * @param allScenariosPageCollection The {@link AllScenariosPageCollection} instance.
     * @param template                   The Freemarker {@link Template} instance.
     * @return The page content as string.
     * @throws CluecumberException Thrown on any error.
     */
    public String getRenderedContent(
            final AllFeaturesPageCollection allFeaturesPageCollection,
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template)
            throws CluecumberException {

        Set<Feature> features = allFeaturesPageCollection.getFeatures()
                .stream().sorted(Comparator.comparing(Feature::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        BasePaths basePaths = propertyManager.getRemovableBasePaths();
        Map<Path, List<FeatureAndScenarios>> featuresByPath = new TreeMap<>(new PathComparator());
        for (Feature feature : features) {
            Path directoryPath = propertyManager.isGroupFeaturesByPath() ? basePaths.stripBasePath(PathUtils.extractDirectoryPath(feature.getUri())) : ROOT_PATH;
            featuresByPath.computeIfAbsent(directoryPath, k -> new ArrayList<>()).add(new FeatureAndScenarios(feature, allScenariosPageCollection.getElementsByFeatureIndex(feature.getIndex())));
        }
        Set<Path> paths = featuresByPath.keySet().stream().sorted(new PathComparator()).collect(Collectors.toCollection(LinkedHashSet::new));

        return processedContent(
                template,
                new TreeViewPageCollection(paths, featuresByPath, new PathFormatter(propertyManager.getDirectoryNameFormatter()), allFeaturesPageCollection.getPageTitle()),
                propertyManager.getNavigationLinks()
        );
    }

}