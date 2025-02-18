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
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllExceptionsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The renderer for the exceptions overview page.
 */
@Singleton
public class AllExceptionsPageRenderer extends PageWithChartRenderer {

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
    public AllExceptionsPageRenderer(
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
     * @param allExceptionsPageCollection The {@link AllExceptionsPageCollection} instance.
     * @param template              THe {@link Template} instance.
     * @return The HTML string.
     * @throws CluecumberException Thrown on any error.
     */
    public String getRenderedContent(
            final AllExceptionsPageCollection allExceptionsPageCollection, final Template template)
            throws CluecumberException {

        addChartJsonToReportDetails(allExceptionsPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == Settings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(allExceptionsPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, allExceptionsPageCollection, propertyManager.getNavigationLinks());
    }

    private void addChartJsonToReportDetails(final AllExceptionsPageCollection allExceptionsPageCollection) {

        List<Float> passed = new ArrayList<>();
        List<Float> failed = new ArrayList<>();
        List<Float> skipped = new ArrayList<>();
        Map<String, String> urlLookup = new HashMap<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<String, ResultCount> entry : allExceptionsPageCollection.getExceptionResultCounts().entrySet()) {
            urlLookup.put(
                    entry.getKey(),
                    Settings.PAGES_DIRECTORY + Settings.EXCEPTION_SCENARIO_PAGE_FRAGMENT +
                            entry.getKey() + Settings.HTML_FILE_EXTENSION);
            ResultCount exceptionResultCount = entry.getValue();
            passed.add((float) exceptionResultCount.getPassed());
            failed.add((float) exceptionResultCount.getFailed());
            skipped.add((float) exceptionResultCount.getSkipped());
            if (exceptionResultCount.getTotal() > maximumNumberOfRuns) {
                maximumNumberOfRuns = exceptionResultCount.getTotal();
            }
        }

        List<String> keys = new ArrayList<>();
        for (String exception : allExceptionsPageCollection.getExceptionResultCounts().keySet()) {
            keys.add(exception);
        }

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setLabels(keys)
                        .setxAxisLabel(allExceptionsPageCollection.getTotalNumberOfExceptions() + " Exceptions")
                        .setyAxisLabel("Number of Scenarios")
                        .setyAxisStepSize(maximumNumberOfRuns)
                        .addValues(passed, Status.PASSED)
                        .addValues(failed, Status.FAILED)
                        .addValues(skipped, Status.SKIPPED)
                        .build();

        allExceptionsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
        allExceptionsPageCollection.getReportDetails().setChartUrlLookup(urlLookup);
    }
}

