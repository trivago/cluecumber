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

package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.constants.ChartConfiguration;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
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

    /**
     * Constructor for dependency injection.
     *
     * @param propertyManager    The {@link PropertyManager} instance.
     */
    @Inject
    public TreeViewPageRenderer(
            final PropertyManager propertyManager
    ) {
        this.propertyManager = propertyManager;
    }

    public String getRenderedContent(
            final AllFeaturesPageCollection allFeaturesPageCollection,
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template)
            throws CluecumberException {

        Map<Feature, List<Element>> scenariosPerFeatures = new LinkedHashMap<>();
        Set<Feature> features = allFeaturesPageCollection.getFeatures()
                .stream().sorted(Comparator.comparing(Feature::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (Feature feature : features) {
            scenariosPerFeatures.put(feature, allScenariosPageCollection.getElementsByFeatureIndex(feature.getIndex()));
        }

        return processedContent(
                template,
                new TreeViewPageCollection(scenariosPerFeatures, allFeaturesPageCollection.getPageTitle()),
                propertyManager.getNavigationLinks()
        );
    }

}