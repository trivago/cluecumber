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

package com.trivago.rta.rendering.pages;

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
import com.trivago.rta.rendering.pages.pojos.TagStat;
import com.trivago.rta.rendering.pages.pojos.TagSummaryPageCollection;
import freemarker.template.Template;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class TagSummaryPageRenderer extends PageRenderer {

    public String getRenderedContent(
            final TagSummaryPageCollection tagSummaryPageCollection, final Template template)
            throws CluecumberPluginException {

        System.out.println("getRenderedContent");

        addChartJsonToReportDetails(tagSummaryPageCollection);
        return processedContent(template, tagSummaryPageCollection);
    }

    private void addChartJsonToReportDetails(final TagSummaryPageCollection tagSummaryPageCollection) {
        List<BarDataset> barDatasets = new ArrayList<>();

        List<BigDecimal> passed = new ArrayList<>();
        List<BigDecimal> failed = new ArrayList<>();
        List<BigDecimal> skipped = new ArrayList<>();

        int maxY = 0;

        for (Map.Entry<String, TagStat> entry : tagSummaryPageCollection.getTagStats().entrySet()) {
            passed.add(BigDecimal.valueOf(entry.getValue().getPassed()));
            failed.add(BigDecimal.valueOf(entry.getValue().getFailed()));
            skipped.add(BigDecimal.valueOf(entry.getValue().getSkipped()));
            maxY = entry.getValue().getTotal();
        }

        barDatasets.add(new BarDataset().setLabel("passed").setData(passed)
                .setBackgroundColor(ChartColor.getChartColorByStatus(Status.PASSED)));
        barDatasets.add(new BarDataset().setLabel("failed")
                .setData(failed).setBackgroundColor(ChartColor.getChartColorByStatus(Status.FAILED)));
        barDatasets.add(new BarDataset().setLabel("skipped").setData(skipped)
                .setBackgroundColor(ChartColor.getChartColorByStatus(Status.SKIPPED)));

        BarData barData = new BarData();
        barData.setDatasets(barDatasets);
        barData.setLabels(tagSummaryPageCollection.getTagStats().keySet());

        BarOptions barOptions = new BarOptions();
        BarScale barScale = new BarScale();

        List<XAxis<LinearTicks>> xAxisList = new ArrayList<>();
        xAxisList.add(new XAxis<LinearTicks>().setStacked(true).setTicks(new LinearTicks().setMin(0)));
        barScale.setxAxes(xAxisList);

        List<YAxis<LinearTicks>> yAxisList = new ArrayList<>();
        yAxisList.add(new YAxis<LinearTicks>().setStacked(true).setTicks(new LinearTicks().setMin(0).setStepSize(maxY)));
        barScale.setyAxes(yAxisList);

        barOptions.setScales(barScale);

        String chartJson = new BarChart(barData, barOptions).toJson();
        tagSummaryPageCollection.getReportDetails().setChartJson(chartJson);
    }
}

