/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumberCore.rendering.pages.renderering;

import com.trivago.cluecumberCore.constants.ChartConfiguration;
import com.trivago.cluecumberCore.constants.Status;
import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.json.pojo.Element;
import com.trivago.cluecumberCore.json.pojo.Report;
import com.trivago.cluecumberCore.json.pojo.Step;
import com.trivago.cluecumberCore.json.pojo.Tag;
import com.trivago.cluecumberCore.properties.PropertyManager;
import com.trivago.cluecumberCore.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumberCore.rendering.pages.charts.PieChartBuilder;
import com.trivago.cluecumberCore.rendering.pages.pojos.CustomParameter;
import com.trivago.cluecumberCore.rendering.pages.pojos.Feature;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class AllScenariosPageRenderer extends PageRenderer {

    private final PropertyManager propertyManager;
    private final ChartConfiguration chartConfiguration;

    @Inject
    public AllScenariosPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration,
            final PropertyManager propertyManager
    ) {
        super(chartJsonConverter);
        this.propertyManager = propertyManager;
        this.chartConfiguration = chartConfiguration;
    }

    public String getRenderedContent(
            final AllScenariosPageCollection allScenariosPageCollection, final Template template)
            throws CluecumberPluginException {

        AllScenariosPageCollection allScenariosPageCollectionClone = getAllScenariosPageCollectionClone(allScenariosPageCollection);
        addChartJsonToReportDetails(allScenariosPageCollectionClone);
        addCustomParametersToReportDetails(allScenariosPageCollectionClone);
        return processedContent(template, allScenariosPageCollectionClone);
    }

    public String getRenderedContentByTagFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Tag tag) throws CluecumberPluginException {

        AllScenariosPageCollection allScenariosPageCollectionClone = getAllScenariosPageCollectionClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setTagFilter(tag);
        allScenariosPageCollectionClone.getReports().forEach(report -> {
            List<Element> elements = report.getElements()
                    .stream()
                    .filter(element -> element.getTags().contains(tag))
                    .collect(Collectors.toList());
            report.setElements(elements);
        });
        addChartJsonToReportDetails(allScenariosPageCollectionClone);
        return processedContent(template, allScenariosPageCollectionClone);
    }

    public String getRenderedContentByStepFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Step step) throws CluecumberPluginException {

        AllScenariosPageCollection allScenariosPageCollectionClone = getAllScenariosPageCollectionClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setStepFilter(step);
        allScenariosPageCollectionClone.getReports().forEach(report -> {
            List<Element> elements = report.getElements()
                    .stream()
                    .filter(element -> element.getSteps().contains(step))
                    .collect(Collectors.toList());
            report.setElements(elements);
        });
        addChartJsonToReportDetails(allScenariosPageCollectionClone);
        return processedContent(template, allScenariosPageCollectionClone);
    }

    public String getRenderedContentByFeatureFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Feature feature) throws CluecumberPluginException {

        AllScenariosPageCollection allScenariosPageCollectionClone = getAllScenariosPageCollectionClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setFeatureFilter(feature);
        Report[] reportArray = allScenariosPageCollectionClone.getReports()
                .stream()
                .filter(report -> report.getFeatureIndex() == feature.getIndex())
                .toArray(Report[]::new);
        allScenariosPageCollectionClone.clearReports();
        allScenariosPageCollectionClone.addReports(reportArray);
        addChartJsonToReportDetails(allScenariosPageCollectionClone);
        return processedContent(template, allScenariosPageCollectionClone);
    }

    private void addChartJsonToReportDetails(final AllScenariosPageCollection allScenariosPageCollection) {
        allScenariosPageCollection.getReportDetails()
                .setChartJson(convertChartToJson(new PieChartBuilder(chartConfiguration)
                        .addValue(allScenariosPageCollection.getTotalNumberOfPassedScenarios(), Status.PASSED)
                        .addValue(allScenariosPageCollection.getTotalNumberOfFailedScenarios(), Status.FAILED)
                        .addValue(allScenariosPageCollection.getTotalNumberOfSkippedScenarios(), Status.SKIPPED)
                        .build()));

    }

    private void addCustomParametersToReportDetails(final AllScenariosPageCollection allScenariosPageCollection) {
        Map<String, String> customParameterMap = propertyManager.getCustomParameters();
        if (customParameterMap == null || customParameterMap.isEmpty()) {
            return;
        }

        // <customParameters> in the pom configuration section
        List<CustomParameter> customParameters = new ArrayList<>();
        customParameterMap.forEach((key1, value) -> {
            if (value == null || value.trim().isEmpty()) {
                return;
            }
            String key = key1.replace("_", " ");
            CustomParameter customParameter = new CustomParameter(key, value);
            customParameters.add(customParameter);
        });

        allScenariosPageCollection.setCustomParameters(customParameters);
    }

    private AllScenariosPageCollection getAllScenariosPageCollectionClone(
            final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        AllScenariosPageCollection clone;
        try {
            clone = (AllScenariosPageCollection) allScenariosPageCollection.clone();
        } catch (CloneNotSupportedException e) {
            throw new CluecumberPluginException("Clone of AllScenariosPageCollection not supported: " + e.getMessage());
        }
        return clone;
    }
}
