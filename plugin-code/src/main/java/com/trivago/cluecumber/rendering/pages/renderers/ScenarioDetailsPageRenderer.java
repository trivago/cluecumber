/*
 * Copyright 2018 trivago N.V.
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

package com.trivago.cluecumber.rendering.pages.renderers;

import com.trivago.cluecumber.constants.ChartColor;
import com.trivago.cluecumber.constants.ChartType;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.ResultMatch;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.charts.pojos.Axis;
import com.trivago.cluecumber.rendering.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.charts.pojos.Data;
import com.trivago.cluecumber.rendering.charts.pojos.Dataset;
import com.trivago.cluecumber.rendering.charts.pojos.Options;
import com.trivago.cluecumber.rendering.charts.pojos.ScaleLabel;
import com.trivago.cluecumber.rendering.charts.pojos.Scales;
import com.trivago.cluecumber.rendering.charts.pojos.Ticks;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Singleton
public class ScenarioDetailsPageRenderer extends PageRenderer {
    private PropertyManager propertyManager;

    @Inject
    public ScenarioDetailsPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final PropertyManager propertyManager) {
        super(chartJsonConverter);
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
        Chart chart = new Chart();

        Element element = scenarioDetailsPageCollection.getElement();
        List<String> labels = new ArrayList<>();
        IntStream.rangeClosed(1, element.getBefore().size()).mapToObj(i -> "Before " + i).forEachOrdered(labels::add);
        IntStream.rangeClosed(1, element.getSteps().size()).mapToObj(i -> "Step " + i).forEachOrdered(labels::add);
        if (element.getAfter().size() > 0) {
            IntStream.rangeClosed(element.getBefore().size(), element.getAfter().size()).mapToObj(i -> "After " + i).forEachOrdered(labels::add);
        }

        Data data = new Data();
        data.setLabels(labels);

        List<Dataset> datasets = new ArrayList<>();
        for (Status status : Status.BASIC_STATES) {
            Dataset dataset = new Dataset();
            List<Integer> dataList = new ArrayList<>();
            for (ResultMatch resultMatch : element.getAllResultMatches()) {
                if (resultMatch.getConsolidatedStatus() == status) {
                    dataList.add((int) resultMatch.getResult().getDurationInMilliseconds());
                } else {
                    dataList.add(0);
                }
            }
            dataset.setData(dataList);
            dataset.setLabel(status.getStatusString());
            dataset.setStack("complete");
            dataset.setBackgroundColor(new ArrayList<>(Collections.nCopies(dataList.size(), ChartColor.getChartColorStringByStatus(status))));
            datasets.add(dataset);
        }

        data.setDatasets(datasets);
        chart.setData(data);

        Options options = new Options();
        Scales scales = new Scales();
        List<Axis> xAxes = new ArrayList<>();
        Axis xAxis = new Axis();
        xAxis.setStacked(true);
        Ticks xTicks = new Ticks();
        xAxis.setTicks(xTicks);
        ScaleLabel xScaleLabel = new ScaleLabel();
        xScaleLabel.setDisplay(true);
        xScaleLabel.setLabelString("Step(s)");
        xAxis.setScaleLabel(xScaleLabel);
        xAxes.add(xAxis);
        scales.setxAxes(xAxes);

        List<Axis> yAxes = new ArrayList<>();
        Axis yAxis = new Axis();
        yAxis.setStacked(true);
        Ticks yTicks = new Ticks();
        yAxis.setTicks(yTicks);
        ScaleLabel yScaleLabel = new ScaleLabel();
        yScaleLabel.setDisplay(true);
        yScaleLabel.setLabelString("Step Runtime");
        yAxis.setScaleLabel(yScaleLabel);
        yAxes.add(yAxis);
        scales.setyAxes(yAxes);

        options.setScales(scales);
        chart.setOptions(options);

        chart.setType(ChartType.bar);

        scenarioDetailsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }
}
