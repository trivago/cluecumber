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
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.ResultMatch;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ScenarioDetailsPageRenderer extends PageRenderer {
    private final ChartConfiguration chartConfiguration;
    private PropertyManager propertyManager;

    @Inject
    public ScenarioDetailsPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration,
            final PropertyManager propertyManager) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
        this.propertyManager = propertyManager;
    }

    public String getRenderedContent(
            final ScenarioDetailsPageCollection scenarioDetailsPageCollection,
            final Template template) throws CluecumberPluginException {

        scenarioDetailsPageCollection.setExpandBeforeAfterHooks(propertyManager.isExpandBeforeAfterHooks());
        scenarioDetailsPageCollection.setExpandStepHooks(propertyManager.isExpandStepHooks());
        scenarioDetailsPageCollection.setExpandDocStrings(propertyManager.isExpandDocStrings());

        addChartJsonToReportDetails(scenarioDetailsPageCollection);
        return processedContent(template, scenarioDetailsPageCollection);
    }

    private void addChartJsonToReportDetails(final ScenarioDetailsPageCollection scenarioDetailsPageCollection) {

        Element element = scenarioDetailsPageCollection.getElement();
        List<String> labels = new ArrayList<>();
        if (element.getBefore().size() > 0) {
            element.getBefore().stream().map(ResultMatch::getGlueMethodName).forEach(labels::add);
        }
        if (element.getSteps().size() > 0) {
            element.getSteps().stream().map(Step::getName).forEach(labels::add);
        }
        if (element.getAfter().size() > 0) {
            element.getAfter().stream().map(ResultMatch::getGlueMethodName).forEach(labels::add);
        }

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setxAxisLabel("Steps")
                        .setyAxisLabel("Step Runtime (seconds)")
                        .setyAxisStepSize(0)
                        .setLabels(labels)
                        .setStacked(false)
                        .addValues(getValuesByStatus(element, Status.PASSED), Status.PASSED)
                        .addValues(getValuesByStatus(element, Status.FAILED), Status.FAILED)
                        .addValues(getValuesByStatus(element, Status.SKIPPED), Status.SKIPPED)
                        .build();

        scenarioDetailsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }

    private List<Integer> getValuesByStatus(final Element element, final Status status) {
        List<Integer> values = new ArrayList<>();
        element.getAllResultMatches().forEach(resultMatch -> {
            if (resultMatch.getConsolidatedStatus() == status) {
                values.add((int) resultMatch.getResult().getDurationInMilliseconds() / 1000);
            } else {
                values.add(0);
            }
        });
        return values;
    }
}
