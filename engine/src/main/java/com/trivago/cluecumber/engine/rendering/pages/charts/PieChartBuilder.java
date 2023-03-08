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
package com.trivago.cluecumber.engine.rendering.pages.charts;

import com.trivago.cluecumber.engine.constants.ChartConfiguration;
import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Data;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Dataset;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * The builder class for pie charts.
 */
public class PieChartBuilder {
    private final ChartConfiguration chartConfiguration;
    private final List<ValueSet> valueSets;

    /**
     * Default constructor of the chart builder.
     *
     * @param chartConfiguration The {@link ChartConfiguration} instance to use.
     */
    public PieChartBuilder(final ChartConfiguration chartConfiguration) {
        this.chartConfiguration = chartConfiguration;
        valueSets = new ArrayList<>();
    }

    /**
     * Add a new value for a specific status.
     *
     * @param value  The numeric value.
     * @param status The status to assign it to.
     * @return The {@link PieChartBuilder}.
     */
    public PieChartBuilder addValue(final int value, final Status status) {
        String color = chartConfiguration.getColorRgbaStringByStatus(status);
        valueSets.add(new ValueSet(value, color));
        return this;
    }

    /**
     * Create the final chart.
     *
     * @return The {@link Chart} instance.
     */
    public Chart build() {

        List<Float> values = new ArrayList<>();
        List<String> colors = new ArrayList<>();

        for (ValueSet valueSet : valueSets) {
            values.add(valueSet.value);
            colors.add(valueSet.color);
        }

        Dataset dataset = new Dataset();
        dataset.setBackgroundColor(colors);
        dataset.setData(values);

        Data data = new Data();
        List<String> labels = new ArrayList<>();
        labels.add(Status.PASSED.getStatusString());
        labels.add(Status.FAILED.getStatusString());
        labels.add(Status.SKIPPED.getStatusString());
        data.setLabels(labels);

        List<Dataset> datasets = new ArrayList<>();
        datasets.add(dataset);
        data.setDatasets(datasets);

        Chart chart = new Chart();
        chart.setData(data);

        final Options options = new Options();
        List<String> events = new ArrayList<>();
        events.add("click");
        events.add("mousemove");
        options.setEvents(events);
        chart.setOptions(options);
        chart.setType(ChartConfiguration.Type.pie);
        return chart;
    }

    private static class ValueSet {
        private final float value;
        private final String color;

        ValueSet(final float value, final String color) {
            this.value = value;
            this.color = color;
        }
    }
}
