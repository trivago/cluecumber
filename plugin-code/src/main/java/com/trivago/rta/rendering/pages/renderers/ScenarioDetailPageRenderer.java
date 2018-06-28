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

import be.ceau.chart.BarChart;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.XAxis;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.trivago.rta.constants.ChartColor;
import com.trivago.rta.constants.Status;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.rendering.pages.pojos.pagecollections.DetailPageCollection;
import freemarker.template.Template;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ScenarioDetailPageRenderer extends PageRenderer {

    public String getRenderedContent(final DetailPageCollection detailPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(detailPageCollection);
        return processedContent(template, detailPageCollection);
    }

    private void addChartJsonToReportDetails(final DetailPageCollection detailPageCollection) {

        BarData data = getDataSetFor(detailPageCollection, Status.PASSED);

        BarScale barScale = new BarScale();
        List<XAxis<LinearTicks>> xAxisList = new ArrayList<>();
        XAxis<LinearTicks> xAxis = new XAxis<>();
        xAxis.setTicks(new LinearTicks().setMin(0));
        xAxisList.add(xAxis);
        barScale.setxAxes(xAxisList);

        List<YAxis<LinearTicks>> yAxisList = new ArrayList<>();
        YAxis<LinearTicks> yAxis = new YAxis<>();

        LinearTicks ticks = new LinearTicks();
        ticks.setMin(0);
        ticks.setDisplay(true);
        yAxis.setTicks(ticks);

        yAxis.setStacked(true);
        yAxisList.add(yAxis);
        barScale.setyAxes(yAxisList);

        BarOptions barOptions = new BarOptions().setScales(barScale);
        detailPageCollection.getReportDetails().setChartJson(new BarChart(data, barOptions).toJson());
    }

    private BarData getDataSetFor(final DetailPageCollection detailPageCollection, final Status status) {
        int stepCounter = 1;
        BarDataset barDataSet = new BarDataset();
        BarData data = new BarData();
        for (Step step : detailPageCollection.getElement().getSteps()) {
            data.addLabel(String.valueOf(stepCounter));
            barDataSet.addData(step.getResult().getDurationInMilliseconds());
            barDataSet.addBackgroundColor(ChartColor.getChartColorByStatus(step.getStatus()));
            stepCounter++;
        }
        barDataSet.setLabel(status.getStatusString());
        data.addDataset(barDataSet);
        return data;
    }
}
