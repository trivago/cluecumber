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

import be.ceau.chart.PieChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.PieData;
import be.ceau.chart.dataset.PieDataset;
import be.ceau.chart.options.PieOptions;
import com.trivago.rta.constants.ChartColor;
import com.trivago.rta.constants.Status;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.CustomParameter;
import com.trivago.rta.rendering.pages.pojos.ReportDetails;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class StartPageRenderer extends PageRenderer {

    private PropertyManager propertyManager;

    @Inject
    public StartPageRenderer(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public String getRenderedContent(
            final StartPageCollection startPageCollection, final Template template)
            throws CluecumberPluginException {

        ReportDetails reportDetails = new ReportDetails();
        addChartJsonToReportDetails(startPageCollection, reportDetails);
        addCurrentDateToReportDetails(reportDetails);
        addCustomParametersToReportDetails(startPageCollection);
        startPageCollection.setReportDetails(reportDetails);

        Writer stringWriter = new StringWriter();
        try {
            template.process(startPageCollection, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new CluecumberPluginException("Could not render start page content: " + e.getMessage());
        }
        return stringWriter.toString();
    }

    private void addChartJsonToReportDetails(final StartPageCollection startPageCollection, final ReportDetails reportDetails) {
        PieDataset pieDataset = new PieDataset();
        pieDataset.setData(
                startPageCollection.getTotalNumberOfPassedScenarios(),
                startPageCollection.getTotalNumberOfFailedScenarios(),
                startPageCollection.getTotalNumberOfSkippedScenarios()
        );

        Color passedColor = ChartColor.getChartColorByStatus(Status.PASSED);
        Color failedColor = ChartColor.getChartColorByStatus(Status.FAILED);
        Color skippedColor = ChartColor.getChartColorByStatus(Status.SKIPPED);

        pieDataset.addBackgroundColors(passedColor, failedColor, skippedColor);
        PieData pieData = new PieData();
        pieData.addDataset(pieDataset);
        pieData.addLabels(Status.PASSED.getStatusString(), Status.FAILED.getStatusString(), Status.SKIPPED.getStatusString());
        PieOptions pieOptions = new PieOptions();

        reportDetails.setChartJson(new PieChart(pieData, pieOptions).toJson());
    }

    private void addCustomParametersToReportDetails(final StartPageCollection startPageCollection) {
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

        startPageCollection.setCustomParameters(customParameters);
    }
}
