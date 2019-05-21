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
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.rendering.charts.ChartJsonConverter;
import com.trivago.cluecumber.constants.ChartType;
import com.trivago.cluecumber.rendering.charts.pojos.Axis;
import com.trivago.cluecumber.rendering.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.charts.pojos.Data;
import com.trivago.cluecumber.rendering.charts.pojos.Dataset;
import com.trivago.cluecumber.rendering.charts.pojos.Options;
import com.trivago.cluecumber.rendering.charts.pojos.ScaleLabel;
import com.trivago.cluecumber.rendering.charts.pojos.Scales;
import com.trivago.cluecumber.rendering.charts.pojos.Ticks;
import com.trivago.cluecumber.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Singleton
public class AllFeaturesPageRenderer extends PageRenderer {

    @Inject
    public AllFeaturesPageRenderer(final ChartJsonConverter chartJsonConverter) {
        super(chartJsonConverter);
    }

    public String getRenderedContent(
            final AllFeaturesPageCollection allFeaturesPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(allFeaturesPageCollection);
        return processedContent(template, allFeaturesPageCollection);
    }

    private void addChartJsonToReportDetails(final AllFeaturesPageCollection allFeaturesPageCollection) {
        Chart chart = new Chart();
        Data data = new Data();
        chart.setData(data);

        List<Dataset> datasets = new ArrayList<>();

        List<Integer> passed = new ArrayList<>();
        List<Integer> failed = new ArrayList<>();
        List<Integer> skipped = new ArrayList<>();

        int maxY = 0;
        for (Map.Entry<Feature, ResultCount> entry : allFeaturesPageCollection.getFeatureResultCounts().entrySet()) {
            passed.add(entry.getValue().getPassed());
            failed.add(entry.getValue().getFailed());
            skipped.add(entry.getValue().getSkipped());
            maxY = entry.getValue().getTotal();
        }

        Dataset passedDataset = new Dataset();
        passedDataset.setLabel(Status.PASSED.getStatusString());
        passedDataset.setData(passed);
        List<String> passedBG = new ArrayList<>(Collections.nCopies(passed.size(), ChartColor.getChartColorStringByStatus(Status.PASSED)));
        passedDataset.setBackgroundColor(passedBG);
        datasets.add(passedDataset);

        Dataset failedDataset = new Dataset();
        failedDataset.setLabel(Status.FAILED.getStatusString());
        failedDataset.setData(failed);
        List<String> failedBG = new ArrayList<>(Collections.nCopies(passed.size(), ChartColor.getChartColorStringByStatus(Status.FAILED)));
        failedDataset.setBackgroundColor(failedBG);
        datasets.add(failedDataset);

        Dataset skippedDataset = new Dataset();
        skippedDataset.setLabel(Status.SKIPPED.getStatusString());
        skippedDataset.setData(skipped);
        List<String> skippedBG = new ArrayList<>(Collections.nCopies(passed.size(), ChartColor.getChartColorStringByStatus(Status.SKIPPED)));
        skippedDataset.setBackgroundColor(skippedBG);
        datasets.add(skippedDataset);

        data.setDatasets(datasets);

        List<String> keys = new ArrayList<>();
        for (Feature feature : allFeaturesPageCollection.getFeatureResultCounts().keySet()) {
            keys.add(feature.getName());
        }
        data.setLabels(keys);

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
        xScaleLabel.setLabelString(allFeaturesPageCollection.getTotalNumberOfFeatures() + " Feature(s)");
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

        chart.setType(ChartType.bar);

        allFeaturesPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }
}

