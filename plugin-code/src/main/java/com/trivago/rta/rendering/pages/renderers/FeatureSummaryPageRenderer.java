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
import com.trivago.rta.rendering.charts.ChartJsonConverter;
import com.trivago.rta.rendering.charts.pojos.Axis;
import com.trivago.rta.rendering.charts.pojos.Chart;
import com.trivago.rta.rendering.charts.pojos.Data;
import com.trivago.rta.rendering.charts.pojos.Dataset;
import com.trivago.rta.rendering.charts.pojos.Options;
import com.trivago.rta.rendering.charts.pojos.Scales;
import com.trivago.rta.rendering.charts.pojos.Ticks;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.ResultCount;
import com.trivago.rta.rendering.pages.pojos.pagecollections.FeatureSummaryPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class FeatureSummaryPageRenderer extends PageRenderer {

    private ChartJsonConverter chartJsonConverter;

    @Inject
    public FeatureSummaryPageRenderer(final ChartJsonConverter chartJsonConverter) {
        this.chartJsonConverter = chartJsonConverter;
    }

    public String getRenderedContent(
            final FeatureSummaryPageCollection featureSummaryPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(featureSummaryPageCollection);
        return processedContent(template, featureSummaryPageCollection);
    }

    private void addChartJsonToReportDetails(final FeatureSummaryPageCollection featureSummaryPageCollection) {

        Chart chart = new Chart();
        Data data = new Data();
        chart.setData(data);

        List<Dataset> datasets = new ArrayList<>();

        List<Integer> passed = new ArrayList<>();
        List<Integer> failed = new ArrayList<>();
        List<Integer> skipped = new ArrayList<>();

        int maxY = 0;
        for (Map.Entry<Feature, ResultCount> entry : featureSummaryPageCollection.getFeatureResultCounts().entrySet()) {
            passed.add(entry.getValue().getPassed());
            failed.add(entry.getValue().getFailed());
            skipped.add(entry.getValue().getSkipped());
            maxY = entry.getValue().getTotal();
        }

        Dataset passedDataset = new Dataset();
        passedDataset.setLabel("passed");
        passedDataset.setData(passed);
        passedDataset.setBackgroundColor(ChartColor.getChartColorStringByStatus(Status.PASSED));
        datasets.add(passedDataset);

        Dataset failedDataset = new Dataset();
        failedDataset.setLabel("failed");
        failedDataset.setData(failed);
        failedDataset.setBackgroundColor(ChartColor.getChartColorStringByStatus(Status.FAILED));
        datasets.add(failedDataset);

        Dataset skippedDataset = new Dataset();
        skippedDataset.setLabel("passed");
        skippedDataset.setData(skipped);
        skippedDataset.setBackgroundColor(ChartColor.getChartColorStringByStatus(Status.SKIPPED));
        datasets.add(skippedDataset);

        data.setDatasets(datasets);

        List<String> keys = new ArrayList<>();
        for (Feature feature : featureSummaryPageCollection.getFeatureResultCounts().keySet()) {
            keys.add(feature.getName());
        }
        data.setLabels(keys);

        Options options = new Options();
        Scales scales = new Scales();
        List<Axis> xAxes = new ArrayList<>();
        Axis xAxis = new Axis();
        xAxis.setStacked(true);
        Ticks xTicks = new Ticks();
        xAxis.setTicks(xTicks);
        ScaleLabel xScaleLabel = new ScaleLabel();
        xScaleLabel.setDisplay(true);
        xScaleLabel.setLabelString("Features");
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
        yScaleLabel.setLabelString("Number of Scenarios");
        yAxis.setScaleLabel(yScaleLabel);
        yAxis.setStepSize(maxY);
        yAxes.add(yAxis);
        scales.setyAxes(yAxes);

        options.setScales(scales);
        chart.setOptions(options);

        chart.setType("bar");

        featureSummaryPageCollection.getReportDetails().setChartJson(chartJsonConverter.convertChartToJson(chart));
    }
}

