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

package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Tag;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class AllTagsPageRenderer extends PageWithChartRenderer {

    private final ChartConfiguration chartConfiguration;
    private final PropertyManager propertyManager;

    @Inject
    public AllTagsPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration,
            final PropertyManager propertyManager
    ) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
        this.propertyManager = propertyManager;
    }

    public String getRenderedContent(
            final AllTagsPageCollection allTagsPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(allTagsPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == PluginSettings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(allTagsPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, allTagsPageCollection);
    }

    private void addChartJsonToReportDetails(final AllTagsPageCollection allTagsPageCollection) {

        List<Integer> passed = new ArrayList<>();
        List<Integer> failed = new ArrayList<>();
        List<Integer> skipped = new ArrayList<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<Tag, ResultCount> entry : allTagsPageCollection.getTagResultCounts().entrySet()) {
            ResultCount value = entry.getValue();
            passed.add(value.getPassed());
            failed.add(value.getFailed());
            skipped.add(value.getSkipped());
            if (value.getTotal() > maximumNumberOfRuns) {
                maximumNumberOfRuns = value.getTotal();
            }
        }

        List<String> keys = new ArrayList<>();
        for (Tag tag : allTagsPageCollection.getTagResultCounts().keySet()) {
            keys.add(tag.getName());
        }

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setLabels(keys)
                        .setxAxisLabel(allTagsPageCollection.getTotalNumberOfTags() + " Tags")
                        .setyAxisLabel("Number of Scenarios")
                        .setyAxisStepSize(maximumNumberOfRuns)
                        .addValues(passed, Status.PASSED)
                        .addValues(failed, Status.FAILED)
                        .addValues(skipped, Status.SKIPPED)
                        .build();

        allTagsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }
}

