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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ScenarioDetailsPageRenderer extends PageWithChartRenderer {
    private final ChartConfiguration chartConfiguration;
    private final PropertyManager propertyManager;

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
        scenarioDetailsPageCollection.setExpandAttachments(propertyManager.isExpandAttachments());

        addChartJsonToReportDetails(scenarioDetailsPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == PluginSettings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(scenarioDetailsPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, scenarioDetailsPageCollection, propertyManager.getNavigationLinks());
    }

    private void addChartJsonToReportDetails(final ScenarioDetailsPageCollection scenarioDetailsPageCollection) {

        Element element = scenarioDetailsPageCollection.getElement();
        List<String> labels = new ArrayList<>();
        element.getBefore().stream().map(ResultMatch::getGlueMethodName).forEach(labels::add);
        element.getSteps().stream().map(Step::getName).forEach(labels::add);
        element.getAfter().stream().map(ResultMatch::getGlueMethodName).forEach(labels::add);

        final List<Float> passedValues = getValuesByStatus(element, Status.PASSED);
        final List<Float> failedValues = getValuesByStatus(element, Status.FAILED);
        final List<Float> skippedValues = getValuesByStatus(element, Status.SKIPPED);

        float passedMax = 0;
        for (Float passedValue : passedValues) {
            if (passedValue > passedMax) {
                passedMax = passedValue;
            }
        }

        float skippedMax = 0;
        for (Float skippedValue : skippedValues) {
            if (skippedValue > skippedMax) {
                skippedMax = skippedValue;
            }
        }

        float failedMax = 0;
        for (Float failedValue : failedValues) {
            if (failedValue > failedMax) {
                failedMax = failedValue;
            }
        }

        final Float maximumValue = Collections.max(Arrays.asList(passedMax, failedMax, skippedMax));

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setxAxisLabel("Steps")
                        .setyAxisLabel("Step Runtime (seconds)")
                        .setyAxisStepSize(maximumValue)
                        .setLabels(labels)
                        .setStacked(false)
                        .addValues(passedValues, Status.PASSED)
                        .addValues(failedValues, Status.FAILED)
                        .addValues(skippedValues, Status.SKIPPED)
                        .build();

        scenarioDetailsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }

    private List<Float> getValuesByStatus(final Element element, final Status status) {
        List<Float> values = new ArrayList<>();
        element.getAllResultMatches().forEach(resultMatch -> {
            if (resultMatch.getConsolidatedStatus() == status) {
                values.add(resultMatch.getResult().getDurationInMilliseconds() / 1000f);
            } else {
                values.add(0f);
            }
        });
        return values;
    }
}
