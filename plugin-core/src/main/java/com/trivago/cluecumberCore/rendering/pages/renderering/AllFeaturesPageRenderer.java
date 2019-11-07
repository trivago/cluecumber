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

package com.trivago.cluecumberCore.rendering.pages.renderering;

import com.trivago.cluecumberCore.constants.ChartConfiguration;
import com.trivago.cluecumberCore.constants.Status;
import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumberCore.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumberCore.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumberCore.rendering.pages.pojos.Feature;
import com.trivago.cluecumberCore.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class AllFeaturesPageRenderer extends PageRenderer {

    private final ChartConfiguration chartConfiguration;

    @Inject
    public AllFeaturesPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration
    ) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
    }

    public String getRenderedContent(
            final AllFeaturesPageCollection allFeaturesPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(allFeaturesPageCollection);
        return processedContent(template, allFeaturesPageCollection);
    }

    private void addChartJsonToReportDetails(final AllFeaturesPageCollection allFeaturesPageCollection) {
        List<Integer> passed = new ArrayList<>();
        List<Integer> failed = new ArrayList<>();
        List<Integer> skipped = new ArrayList<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<Feature, ResultCount> entry : allFeaturesPageCollection.getFeatureResultCounts().entrySet()) {
            ResultCount value = entry.getValue();
            passed.add(value.getPassed());
            failed.add(value.getFailed());
            skipped.add(value.getSkipped());
            if (value.getTotal() > maximumNumberOfRuns) {
                maximumNumberOfRuns = value.getTotal();
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
    }
}

