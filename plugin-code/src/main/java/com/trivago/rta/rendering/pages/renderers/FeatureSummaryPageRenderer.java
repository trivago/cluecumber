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
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.ResultCount;
import com.trivago.rta.rendering.pages.pojos.pagecollections.FeatureSummaryPageCollection;
import freemarker.template.Template;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class FeatureSummaryPageRenderer extends PageRenderer {
    public String getRenderedContent(
            final FeatureSummaryPageCollection featureSummaryPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(featureSummaryPageCollection);
        return processedContent(template, featureSummaryPageCollection);
    }

    private void addChartJsonToReportDetails(final FeatureSummaryPageCollection featureSummaryPageCollection) {
        /*
        {
  "data" : {
    "labels" : [ "login page", "Feature 2", "Feature 1" ],
    "datasets" : [ {
      "data" : [ 1, 0, 2 ],
      "backgroundColor" : "rgba(40,167,69,1.000)",
      "label" : "passed"
    }, {
      "data" : [ 0, 2, 0 ],
      "backgroundColor" : "rgba(220,53,69,1.000)",
      "label" : "failed"
    }, {
      "data" : [ 0, 1, 0 ],
      "backgroundColor" : "rgba(255,193,7,1.000)",
      "label" : "skipped"
    } ]
  },
  "options" : {
    "scales" : {
      "xAxes" : [ {
        "ticks" : {
          "min" : 0
        },
        "stacked" : true
      } ],
      "yAxes" : [ {
        "ticks" : {
          "min" : 0,
          "stepSize" : 2
        },
        "stacked" : true
      } ]
    }
  },
  "type" : "bar"
}
         */


        List<BarDataset> barDatasets = new ArrayList<>();

        List<BigDecimal> passed = new ArrayList<>();
        List<BigDecimal> failed = new ArrayList<>();
        List<BigDecimal> skipped = new ArrayList<>();

        int maxY = 0;

        for (Map.Entry<Feature, ResultCount> entry : featureSummaryPageCollection.getFeatureResultCounts().entrySet()) {
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

        List<String> keys = new ArrayList<>();
        for (Feature feature : featureSummaryPageCollection.getFeatureResultCounts().keySet()) {
            keys.add(feature.getName());
        }
        barData.setLabels(keys);

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
        featureSummaryPageCollection.getReportDetails().setChartJson(chartJson);
    }
}

