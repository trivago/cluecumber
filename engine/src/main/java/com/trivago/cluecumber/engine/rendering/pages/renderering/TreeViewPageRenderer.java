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
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.TreeViewPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The renderer for the feature and scenario tree view page.
 */
@Singleton
public class TreeViewPageRenderer extends PageRenderer {

    private final PropertyManager propertyManager;
    private TreeNode rootTreeNode;

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

        Map<Feature, List<Element>> scenariosPerFeatures = new LinkedHashMap<>();
        Set<Feature> features = allFeaturesPageCollection.getFeatures()
                .stream().sorted(Comparator.comparing(Feature::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        rootTreeNode = new TreeNode("root", false);

        for (Feature feature : features) {
            String uri = feature.getUri();
            addPath(uri);

            scenariosPerFeatures.put(feature, allScenariosPageCollection.getElementsByFeatureIndex(feature.getIndex()));
        }


        printTree(rootTreeNode, 0);

        return processedContent(
                template,
                new TreeViewPageCollection(rootTreeNode, scenariosPerFeatures, allFeaturesPageCollection.getPageTitle()),
                propertyManager.getNavigationLinks()
        );
    }

    private void printTree(final TreeNode node, final int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println("Node: " + node.getName() + " isFeature: " + node.isFeatureFile());

        for (TreeNode child : node.getChildren().values()) {
            printTree(child, level + 1);
        }
    }

    public void addPath(String path) {
        String[] parts = path.split("/");
        TreeNode current = rootTreeNode;

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            boolean isFeatureFile = part.endsWith(".feature");

            current.getChildren().putIfAbsent(part, new TreeNode(part, isFeatureFile));
            current = current.getChildren().get(part);
        }
    }
}
