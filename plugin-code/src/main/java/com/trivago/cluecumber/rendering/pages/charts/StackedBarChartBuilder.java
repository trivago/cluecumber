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

package com.trivago.cluecumber.rendering.pages.charts;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Axis;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Data;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Dataset;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Options;
import com.trivago.cluecumber.rendering.pages.charts.pojos.ScaleLabel;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Scales;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Ticks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StackedBarChartBuilder {
    private final ChartConfiguration chartConfiguration;
    private List<String> labels;
    private final List<Dataset> datasets = new ArrayList<>();
    private String xAxisLabel;
    private String yAxisLabel;
    private int yAxisStepSize = 1;
    private boolean stacked = true;

    public StackedBarChartBuilder(final ChartConfiguration chartConfiguration) {
        this.chartConfiguration = chartConfiguration;
    }

    public StackedBarChartBuilder addValues(final List<Integer> values, final Status status) {
        String color = chartConfiguration.getColorRgbaStringByStatus(status);
        Dataset dataset = new Dataset();
        dataset.setLabel(status.getStatusString());
        dataset.setData(values);
        List<String> colors = new ArrayList<>(Collections.nCopies(values.size(), color));
        dataset.setBackgroundColor(colors);
        datasets.add(dataset);
        return this;
    }

    public StackedBarChartBuilder setLabels(final List<String> labels) {
        this.labels = labels;
        return this;
    }

    public StackedBarChartBuilder setxAxisLabel(final String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
        return this;
    }

    public StackedBarChartBuilder setyAxisLabel(final String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
        return this;
    }

    public StackedBarChartBuilder setyAxisStepSize(final int yAxisStepSize) {
        this.yAxisStepSize = yAxisStepSize;
        return this;
    }

    public StackedBarChartBuilder setStacked(final boolean stacked) {
        this.stacked = stacked;
        return this;
    }

    public Chart build() {
        Chart chart = new Chart();
        chart.setType(ChartConfiguration.Type.bar);

        Data data = new Data();
        data.setLabels(labels);

        for (Dataset dataset : datasets) {
            if (!stacked) {
                dataset.setStack("complete");
            }
        }

        data.setDatasets(datasets);
        chart.setData(data);

        Options options = new Options();
        Scales scales = new Scales();
        List<Axis> xAxes = new ArrayList<>();
        Axis xAxis = new Axis();
        xAxis.setStacked(true);
        Ticks xTicks = new Ticks();
        xTicks.setDisplay(false);
        xAxis.setTicks(xTicks);
        ScaleLabel xScaleLabel = new ScaleLabel();
        xScaleLabel.setDisplay(true);
        xScaleLabel.setLabelString(xAxisLabel);
        xAxis.setScaleLabel(xScaleLabel);
        xAxes.add(xAxis);
        scales.setxAxes(xAxes);

        List<Axis> yAxes = new ArrayList<>();
        Axis yAxis = new Axis();
        yAxis.setStacked(true);
        Ticks yTicks = new Ticks();
        yTicks.setStepSize(yAxisStepSize);
        yAxis.setTicks(yTicks);
        ScaleLabel yScaleLabel = new ScaleLabel();
        yScaleLabel.setDisplay(true);
        yScaleLabel.setLabelString(yAxisLabel);
        yAxis.setScaleLabel(yScaleLabel);
        yAxes.add(yAxis);
        scales.setyAxes(yAxes);

        options.setScales(scales);
        List<String> events = new ArrayList<>();
        events.add("click");
        events.add("mousemove");
        options.setEvents(events);
        chart.setOptions(options);

        return chart;
    }
}
