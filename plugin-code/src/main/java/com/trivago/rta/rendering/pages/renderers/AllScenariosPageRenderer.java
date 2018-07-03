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

import com.rits.cloning.Cloner;
import com.trivago.rta.constants.ChartColor;
import com.trivago.rta.constants.Status;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Tag;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.charts.ChartJsonConverter;
import com.trivago.rta.rendering.charts.pojos.Chart;
import com.trivago.rta.rendering.charts.pojos.Data;
import com.trivago.rta.rendering.charts.pojos.Dataset;
import com.trivago.rta.rendering.pages.pojos.CustomParameter;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class AllScenariosPageRenderer extends PageRenderer {
    private PropertyManager propertyManager;
    private Cloner cloner;
    @Inject
    public AllScenariosPageRenderer(final ChartJsonConverter chartJsonConverter, PropertyManager propertyManager) {
        super(chartJsonConverter);
        this.propertyManager = propertyManager;
        cloner = new Cloner();
    }

    public String getRenderedContent(
            final AllScenariosPageCollection allScenariosPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(allScenariosPageCollection);
        addCustomParametersToReportDetails(allScenariosPageCollection);
        return processedContent(template, allScenariosPageCollection);
    }

    public String getRenderedContentByTagFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Tag tag) throws CluecumberPluginException {

        AllScenariosPageCollection allScenariosPageCollectionClone = cloner.deepClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setTagFilter(tag);
        for (Report report : allScenariosPageCollectionClone.getReports()) {
            List<Element> elements = new ArrayList<>();
            for (Element element : report.getElements()) {
                if (element.getTags().contains(tag)) {
                    elements.add(element);
                }
            }
            report.setElements(elements);
        }
        addChartJsonToReportDetails(allScenariosPageCollectionClone);
        return processedContent(template, allScenariosPageCollectionClone);
    }

    public String getRenderedContentByFeatureFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Feature feature) throws CluecumberPluginException {

        AllScenariosPageCollection allScenariosPageCollectionClone = cloner.deepClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setFeatureFilter(feature);
        List<Report> reports = new ArrayList<>();
        for (Report report : allScenariosPageCollectionClone.getReports()) {
            if (report.getFeatureIndex() == feature.getIndex()) {
                reports.add(report);
            }
        }
        Report[] reportArray = reports.toArray(new Report[0]);
        allScenariosPageCollectionClone.clearReports();
        allScenariosPageCollectionClone.addReports(reportArray);
        addChartJsonToReportDetails(allScenariosPageCollectionClone);
        return processedContent(template, allScenariosPageCollectionClone);
    }

    private void addChartJsonToReportDetails(final AllScenariosPageCollection allScenariosPageCollection) {

        Chart chart = new Chart();
        Data data = new Data();

        List<String> labels = new ArrayList<>();
        labels.add(Status.PASSED.getStatusString());
        labels.add(Status.FAILED.getStatusString());
        labels.add(Status.SKIPPED.getStatusString());
        data.setLabels(labels);

        List<Dataset> datasets = new ArrayList<>();
        Dataset dataset = new Dataset();
        List<Integer> values = new ArrayList<>();
        values.add(allScenariosPageCollection.getTotalNumberOfPassedScenarios());
        values.add(allScenariosPageCollection.getTotalNumberOfFailedScenarios());
        values.add(allScenariosPageCollection.getTotalNumberOfSkippedScenarios());
        dataset.setData(values);
        datasets.add(dataset);

        List<String> backgroundColors = new ArrayList<>();
        backgroundColors.add(ChartColor.getChartColorStringByStatus(Status.PASSED));
        backgroundColors.add(ChartColor.getChartColorStringByStatus(Status.FAILED));
        backgroundColors.add(ChartColor.getChartColorStringByStatus(Status.SKIPPED));
        dataset.setBackgroundColor(backgroundColors);
        data.setDatasets(datasets);


        chart.setData(data);
        chart.setType("pie");

        /*
        {
  "type" : "pie",
  "data" : {
    "labels" : [ "passed", "failed", "skipped" ],
    "datasets" : [ {
      "data" : [ 3, 2, 1 ],
      "backgroundColor" : [ "rgba(40,167,69,1.000)", "rgba(220,53,69,1.000)", "rgba(255,193,7,1.000)" ]
    } ]
  },
  "options" : { }
}
         */
//
//        PieDataset pieDataset = new PieDataset();
//        pieDataset.setData(
//                allScenariosPageCollection.getTotalNumberOfPassedScenarios(),
//                allScenariosPageCollection.getTotalNumberOfFailedScenarios(),
//                allScenariosPageCollection.getTotalNumberOfSkippedScenarios()
//        );
//
//        Color passedColor = ChartColor.getChartColorByStatus(Status.PASSED);
//        Color failedColor = ChartColor.getChartColorByStatus(Status.FAILED);
//        Color skippedColor = ChartColor.getChartColorByStatus(Status.SKIPPED);
//
//        pieDataset.addBackgroundColors(passedColor, failedColor, skippedColor);
//        PieData pieData = new PieData();
//        pieData.addDataset(pieDataset);
//        pieData.addLabels(Status.PASSED.getStatusString(), Status.FAILED.getStatusString(), Status.SKIPPED.getStatusString());
//        PieOptions pieOptions = new PieOptions();
//
        allScenariosPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }

    private void addCustomParametersToReportDetails(final AllScenariosPageCollection allScenariosPageCollection) {
        Map<String, String> customParameterMap = propertyManager.getCustomParameters();
        if (customParameterMap == null || customParameterMap.isEmpty()) {
            return;
        }

        // <customParameters> in the pom configuration section
        List<CustomParameter> customParameters = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : customParameterMap.entrySet()) {
            String key = stringStringEntry.getKey().replace("_", " ");
            CustomParameter customParameter = new CustomParameter(key, stringStringEntry.getValue());
            customParameters.add(customParameter);
        }

        allScenariosPageCollection.setCustomParameters(customParameters);
    }

    public enum Filter {
        TAG, FEATURE
    }
}
