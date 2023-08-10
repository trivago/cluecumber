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
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllStepsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The renderer for the steps overview page.
 */
public class AllStepsPageRenderer extends PageWithChartRenderer {

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
    public AllStepsPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration,
            final PropertyManager propertyManager
    ) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
        this.propertyManager = propertyManager;
    }

    /**
     * Get the rendered HTML content.
     *
     * @param allStepsPageCollection The {@link AllStepsPageCollection} instance.
     * @param template               The {@link Template} instance.
     * @return The rendered HTML string.
     * @throws CluecumberException Thrown on all errors.
     */
    public String getRenderedContent(
            final AllStepsPageCollection allStepsPageCollection, final Template template)
            throws CluecumberException {

        addChartJsonToReportDetails(allStepsPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == Settings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(allStepsPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, allStepsPageCollection, propertyManager.getNavigationLinks());
    }

    private void addChartJsonToReportDetails(final AllStepsPageCollection allStepsPageCollection) {

        List<Float> passed = new ArrayList<>();
        List<Float> failed = new ArrayList<>();
        List<Float> skipped = new ArrayList<>();
        Map<String, String> urlLookup = new HashMap<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<Step, ResultCount> entry : allStepsPageCollection.getStepResultCounts().entrySet()) {
            urlLookup.put(
                    entry.getKey().returnNameWithArgumentPlaceholders(),
                    Settings.PAGES_DIRECTORY + Settings.STEP_SCENARIO_PAGE_FRAGMENT +
                            entry.getKey().getUrlFriendlyName() + Settings.HTML_FILE_EXTENSION);
            ResultCount stepResultCount = entry.getValue();
            passed.add((float) stepResultCount.getPassed());
            failed.add((float) stepResultCount.getFailed());
            skipped.add((float) stepResultCount.getSkipped());
            if (stepResultCount.getTotal() > maximumNumberOfRuns) {
                maximumNumberOfRuns = stepResultCount.getTotal();
            }
        }

        List<String> keys = allStepsPageCollection.getStepResultCounts()
                .keySet()
                .stream()
                .map(Step::returnNameWithArgumentPlaceholders)
                .collect(Collectors.toList());

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setLabels(keys)
                        .setxAxisLabel(allStepsPageCollection.getTotalNumberOfSteps() + " Steps")
                        .setyAxisStepSize(maximumNumberOfRuns)
                        .setyAxisLabel("Number of Usages")
                        .addValues(passed, Status.PASSED)
                        .addValues(failed, Status.FAILED)
                        .addValues(skipped, Status.SKIPPED)
                        .build();


        allStepsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
        allStepsPageCollection.getReportDetails().setChartUrlLookup(urlLookup);
    }
}
