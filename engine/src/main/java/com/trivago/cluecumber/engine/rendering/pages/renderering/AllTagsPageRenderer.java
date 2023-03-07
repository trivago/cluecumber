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
import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Tag;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The renderer for the tags overview page.
 */
@Singleton
public class AllTagsPageRenderer extends PageWithChartRenderer {

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
            throws CluecumberException {

        addChartJsonToReportDetails(allTagsPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == PluginSettings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(allTagsPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, allTagsPageCollection, propertyManager.getNavigationLinks());
    }

    private void addChartJsonToReportDetails(final AllTagsPageCollection allTagsPageCollection) {

        List<Float> passed = new ArrayList<>();
        List<Float> failed = new ArrayList<>();
        List<Float> skipped = new ArrayList<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<Tag, ResultCount> entry : allTagsPageCollection.getTagResultCounts().entrySet()) {
            ResultCount tagResultCount = entry.getValue();
            passed.add((float) tagResultCount.getPassed());
            failed.add((float) tagResultCount.getFailed());
            skipped.add((float) tagResultCount.getSkipped());
            if (tagResultCount.getTotal() > maximumNumberOfRuns) {
                maximumNumberOfRuns = tagResultCount.getTotal();
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

