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

package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.json.pojo.Tag;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.charts.PieChartBuilder;
import com.trivago.cluecumber.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class AllScenariosPageRenderer extends PageWithChartRenderer {

    private final PropertyManager propertyManager;
    private final ChartConfiguration chartConfiguration;

    @Inject
    AllScenariosPageRenderer(
            final PropertyManager propertyManager,
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration
    ) {
        super(chartJsonConverter);
        this.propertyManager = propertyManager;
        this.chartConfiguration = chartConfiguration;
    }

    public String getRenderedContent(
            final AllScenariosPageCollection allScenariosPageCollection, final Template template)
            throws CluecumberPluginException {

        return processedContent(template, getAllScenariosPageCollectionClone(allScenariosPageCollection));
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

    private AllScenariosPageCollection getAllScenariosPageCollectionClone(
            final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        AllScenariosPageCollection clone;
        try {
            clone = (AllScenariosPageCollection) allScenariosPageCollection.clone();
        } catch (CloneNotSupportedException e) {
            throw new CluecumberPluginException("Clone of AllScenariosPageCollection not supported: " + e.getMessage());
        }
        addChartJsonToReportDetails(clone);
        addCustomParametersToReportDetails(clone, propertyManager.getCustomParameters());
        return clone;
    }
}
