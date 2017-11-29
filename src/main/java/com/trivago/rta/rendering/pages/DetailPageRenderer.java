/*
 * Copyright 2017 trivago N.V.
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
import be.ceau.chart.options.ticks.LinearTicks;
import com.trivago.rta.constants.ChartColor;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.ReportDetails;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class DetailPageRenderer extends PageRenderer {

    public String getRenderedContent(final DetailPageCollection detailPageCollection, final Template template)
            throws CluecumberPluginException {

        System.out.println(detailPageCollection.getElement().getName());

        ReportDetails reportDetails = new ReportDetails();
        addChartJsonToReportDetails(detailPageCollection.getElement(), reportDetails);
        addCurrentDateToReportDetails(reportDetails);
        detailPageCollection.setReportDetails(reportDetails);

        Writer stringWriter = new StringWriter();
        try {
            template.process(detailPageCollection, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new CluecumberPluginException(e.getMessage());
        }
        return stringWriter.toString();
    }

    private void addChartJsonToReportDetails(final Element element, final ReportDetails reportDetails) {
        BarDataset barDataSet = new BarDataset();

        BarData barData = new BarData();
        int stepCounter = 1;

        for (Step step : element.getSteps()) {
            barData.addLabel("Step " + stepCounter);
            barDataSet.addData(step.getResult().getDurationInMilliseconds());
            barDataSet.addBackgroundColor(ChartColor.getChartColorByStatus(step.getStatus()));
            stepCounter++;
        }
        barDataSet.setLabel("Step runtime");
        barData.addDataset(barDataSet);

        LinearTicks ticks = new LinearTicks().setMin(0);
        BarScale scale = new BarScale()
                .addyAxes(BarScale.yAxis().setTicks(ticks));
        BarOptions barOptions = new BarOptions().setScales(scale);

        reportDetails.setChartJson(new BarChart(barData, barOptions).toJson());
    }
}
