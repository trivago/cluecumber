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

package com.trivago.rta.rendering.pages.renderers;

import be.ceau.chart.options.scales.ScaleLabel;
import com.trivago.rta.constants.ChartColor;
import com.trivago.rta.constants.Status;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.rendering.charts.ChartJsonConverter;
import com.trivago.rta.rendering.charts.pojos.Axis;
import com.trivago.rta.rendering.charts.pojos.Chart;
import com.trivago.rta.rendering.charts.pojos.Data;
import com.trivago.rta.rendering.charts.pojos.Dataset;
import com.trivago.rta.rendering.charts.pojos.Options;
import com.trivago.rta.rendering.charts.pojos.Scales;
import com.trivago.rta.rendering.charts.pojos.Ticks;
import com.trivago.rta.rendering.pages.pojos.pagecollections.DetailPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ScenarioDetailPageRenderer extends PageRenderer {

    private ChartJsonConverter chartJsonConverter;

    @Inject
    public ScenarioDetailPageRenderer(final ChartJsonConverter chartJsonConverter) {
        this.chartJsonConverter = chartJsonConverter;
    }

    public String getRenderedContent(final DetailPageCollection detailPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(detailPageCollection);
        return processedContent(template, detailPageCollection);
    }

    private void addChartJsonToReportDetails(final DetailPageCollection detailPageCollection) {
        Chart chart = new Chart();

        List<String> labels = new ArrayList<>();
        for (int i = 1; i <= detailPageCollection.getElement().getSteps().size(); i++) {
            labels.add(String.valueOf(i));
        }
        Data data = new Data();
        data.setLabels(labels);

        List<Dataset> datasets = new ArrayList<>();
        for (Status status : Status.BASIC_STATES) {
            Dataset dataset = new Dataset();
            List<Integer> dataList = new ArrayList<>();
            for (Step step : detailPageCollection.getElement().getSteps()) {
                if (step.getConsolidatedStatus() == status) {
                    dataList.add((int) step.getResult().getDurationInMilliseconds());
                } else {
                    dataList.add(0);
                }
            }
            dataset.setData(dataList);
            dataset.setLabel(status.getStatusString());
            dataset.setStack("complete");
            dataset.setBackgroundColor(ChartColor.getChartColorStringByStatus(status));
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
        xScaleLabel.setLabelString("Step Number");
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

        chart.setType("bar");

        detailPageCollection.getReportDetails().setChartJson(chartJsonConverter.convertChartToJson(chart));
    }
}
