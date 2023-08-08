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

import com.trivago.cluecumber.engine.constants.ChartConfiguration;
import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The renderer for the feature overview page.
 */
@Singleton
public class AllFeaturesPageRenderer extends PageWithChartRenderer {

    private final ChartConfiguration chartConfiguration;
    private final PropertyManager propertyManager;

    /**
     * Constructor for dependency injection.
     *
     * @param chartJsonConverter The {@link ChartJsonConverter} instance.
     * @param chartConfiguration The {@link ChartConfiguration} instance.
     * @param propertyManager    The {@link PropertyManager} instance.
     */
    @Inject
    public AllFeaturesPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration,
            final PropertyManager propertyManager
    ) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
        this.propertyManager = propertyManager;
    }

    /**
     * Return the rendered page content.
     *
     * @param allFeaturesPageCollection The {@link AllFeaturesPageCollection} instance.
     * @param template                  The Freemarker template.
     * @return The rendered page content as a string.
     * @throws CluecumberException Thrown in case of any error.
     */
    public String getRenderedContent(
            final AllFeaturesPageCollection allFeaturesPageCollection, final Template template)
            throws CluecumberException {

        addChartJsonToReportDetails(allFeaturesPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == Settings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(allFeaturesPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, allFeaturesPageCollection, propertyManager.getNavigationLinks());
    }

    private void addChartJsonToReportDetails(final AllFeaturesPageCollection allFeaturesPageCollection) {
        List<Float> passed = new ArrayList<>();
        List<Float> failed = new ArrayList<>();
        List<Float> skipped = new ArrayList<>();
        Map<String, String> urlLookup = new HashMap<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<Feature, ResultCount> entry : allFeaturesPageCollection.getFeatureResultCounts().entrySet()) {
            urlLookup.put(
                    entry.getKey().getName(),
                    Settings.PAGES_DIRECTORY + Settings.FEATURE_SCENARIOS_PAGE_FRAGMENT +
                            entry.getKey().getIndex() + Settings.HTML_FILE_EXTENSION);
            ResultCount featureResultCount = entry.getValue();
            passed.add((float) featureResultCount.getPassed());
            failed.add((float) featureResultCount.getFailed());
            skipped.add((float) featureResultCount.getSkipped());
            if (featureResultCount.getTotal() > maximumNumberOfRuns) {
                maximumNumberOfRuns = featureResultCount.getTotal();
            }
        }

        List<String> keys = allFeaturesPageCollection.getFeatureResultCounts()
                .keySet()
                .stream()
                .map(Feature::getName)
                .collect(Collectors.toList());

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setLabels(keys)
                        .setxAxisLabel(allFeaturesPageCollection.getTotalNumberOfFeatures() + " Features")
                        .setyAxisLabel("Number of Scenarios")
                        .setyAxisStepSize(maximumNumberOfRuns)
                        .addValues(passed, Status.PASSED)
                        .addValues(failed, Status.FAILED)
                        .addValues(skipped, Status.SKIPPED)
                        .build();

        allFeaturesPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
        allFeaturesPageCollection.getReportDetails().setChartUrlLookup(urlLookup);
    }
}

