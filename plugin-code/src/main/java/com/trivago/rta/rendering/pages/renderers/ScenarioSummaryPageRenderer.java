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

import be.ceau.chart.PieChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.PieData;
import be.ceau.chart.dataset.PieDataset;
import be.ceau.chart.options.PieOptions;
import com.rits.cloning.Cloner;
import com.trivago.rta.constants.ChartColor;
import com.trivago.rta.constants.Status;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Tag;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.CustomParameter;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioSummaryPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class ScenarioSummaryPageRenderer extends PageRenderer {
    public enum Filter {
        TAG, FEATURE
    }

    private PropertyManager propertyManager;
    private Cloner cloner;

    @Inject
    public ScenarioSummaryPageRenderer(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
        cloner = new Cloner();
    }

    public String getRenderedContent(
            final ScenarioSummaryPageCollection scenarioSummaryPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(scenarioSummaryPageCollection);
        addCustomParametersToReportDetails(scenarioSummaryPageCollection);
        return processedContent(template, scenarioSummaryPageCollection);
    }

    public String getRenderedContentByTagFilter(
            final ScenarioSummaryPageCollection scenarioSummaryPageCollection,
            final Template template,
            final Tag tag) throws CluecumberPluginException {

        ScenarioSummaryPageCollection scenarioSummaryPageCollectionClone = cloner.deepClone(scenarioSummaryPageCollection);
        scenarioSummaryPageCollectionClone.setTagFilter(tag);
        for (Report report : scenarioSummaryPageCollectionClone.getReports()) {
            List<Element> elements = new ArrayList<>();
            for (Element element : report.getElements()) {
                if (element.getTags().contains(tag)){
                    elements.add(element);
                }
            }
            report.setElements(elements);
        }
        addChartJsonToReportDetails(scenarioSummaryPageCollectionClone);
        return processedContent(template, scenarioSummaryPageCollectionClone);
    }

    public String getRenderedContentByFeatureFilter(
            final ScenarioSummaryPageCollection scenarioSummaryPageCollection,
            final Template template,
            final Feature feature) throws CluecumberPluginException {

        ScenarioSummaryPageCollection scenarioSummaryPageCollectionClone = cloner.deepClone(scenarioSummaryPageCollection);
        scenarioSummaryPageCollectionClone.setFeatureFilter(feature);
        List<Report> reports = new ArrayList<>();
        for (Report report : scenarioSummaryPageCollectionClone.getReports()) {
            if(report.getFeatureIndex() == feature.getIndex()){
                reports.add(report);
            }
        }
        Report[] reportArray = reports.toArray(new Report[0]);
        scenarioSummaryPageCollectionClone.clearReports();
        scenarioSummaryPageCollectionClone.addReports(reportArray);
        addChartJsonToReportDetails(scenarioSummaryPageCollectionClone);
        return processedContent(template, scenarioSummaryPageCollectionClone);
    }


    private void addChartJsonToReportDetails(final ScenarioSummaryPageCollection scenarioSummaryPageCollection) {
        PieDataset pieDataset = new PieDataset();
        pieDataset.setData(
                scenarioSummaryPageCollection.getTotalNumberOfPassedScenarios(),
                scenarioSummaryPageCollection.getTotalNumberOfFailedScenarios(),
                scenarioSummaryPageCollection.getTotalNumberOfSkippedScenarios()
        );

        Color passedColor = ChartColor.getChartColorByStatus(Status.PASSED);
        Color failedColor = ChartColor.getChartColorByStatus(Status.FAILED);
        Color skippedColor = ChartColor.getChartColorByStatus(Status.SKIPPED);

        pieDataset.addBackgroundColors(passedColor, failedColor, skippedColor);
        PieData pieData = new PieData();
        pieData.addDataset(pieDataset);
        pieData.addLabels(Status.PASSED.getStatusString(), Status.FAILED.getStatusString(), Status.SKIPPED.getStatusString());
        PieOptions pieOptions = new PieOptions();

        scenarioSummaryPageCollection.getReportDetails().setChartJson(new PieChart(pieData, pieOptions).toJson());
    }

    private void addCustomParametersToReportDetails(final ScenarioSummaryPageCollection scenarioSummaryPageCollection) {
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

        scenarioSummaryPageCollection.setCustomParameters(customParameters);
    }
}
