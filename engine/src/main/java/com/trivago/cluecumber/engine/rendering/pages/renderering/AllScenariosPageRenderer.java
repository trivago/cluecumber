/*
 * Copyright 2023 trivago N.V.
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
package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.constants.ChartConfiguration;
import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.json.pojo.Tag;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.charts.PieChartBuilder;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The renderer for the scenario overview page.
 */
@Singleton
public class AllScenariosPageRenderer extends PageWithChartRenderer {

    private final PropertyManager propertyManager;
    private final ChartConfiguration chartConfiguration;

    /**
     * Constructor for dependency injection.
     *
     * @param chartJsonConverter The {@link ChartJsonConverter} instance.
     * @param chartConfiguration The {@link ChartConfiguration} instance.
     * @param propertyManager    The {@link PropertyManager} instance.
     */
    @Inject
    AllScenariosPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration,
            final PropertyManager propertyManager
    ) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
        this.propertyManager = propertyManager;
    }

    public String getRenderedContent(
            final AllScenariosPageCollection allScenariosPageCollection, final Template template)
            throws CluecumberException {

        return processedContent(template, getAllScenariosPageCollectionClone(allScenariosPageCollection), propertyManager.getNavigationLinks());
    }

    public String getRenderedContentByTagFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Tag tag) throws CluecumberException {

        AllScenariosPageCollection allScenariosPageCollectionClone = getAllScenariosPageCollectionClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setTagFilter(tag);
        allScenariosPageCollectionClone.getReports().forEach(report -> {
            List<Element> elements = report.getElements()
                    .stream()
                    .filter(element -> element.getTags().contains(tag))
                    .collect(Collectors.toList());
            report.setElements(elements);
        });
        return processedContent(template, allScenariosPageCollectionClone, propertyManager.getNavigationLinks());
    }

    public String getRenderedContentByStepFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Step step) throws CluecumberException {

        AllScenariosPageCollection allScenariosPageCollectionClone = getAllScenariosPageCollectionClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setStepFilter(step);
        allScenariosPageCollectionClone.getReports().forEach(report -> {
            List<Element> elements = report.getElements()
                    .stream()
                    .filter(element -> element.getSteps().contains(step))
                    .collect(Collectors.toList());
            report.setElements(elements);
        });
        return processedContent(template, allScenariosPageCollectionClone, propertyManager.getNavigationLinks());
    }

    public String getRenderedContentByFeatureFilter(
            final AllScenariosPageCollection allScenariosPageCollection,
            final Template template,
            final Feature feature) throws CluecumberException {

        AllScenariosPageCollection allScenariosPageCollectionClone = getAllScenariosPageCollectionClone(allScenariosPageCollection);
        allScenariosPageCollectionClone.setFeatureFilter(feature);
        Report[] reportArray = allScenariosPageCollectionClone.getReports()
                .stream()
                .filter(report -> report.getFeatureIndex() == feature.getIndex())
                .toArray(Report[]::new);
        allScenariosPageCollectionClone.clearReports();
        allScenariosPageCollectionClone.addReports(reportArray);
        return processedContent(template, allScenariosPageCollectionClone, propertyManager.getNavigationLinks());
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
            final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberException {
        AllScenariosPageCollection clone;
        try {
            clone = (AllScenariosPageCollection) allScenariosPageCollection.clone();
        } catch (CloneNotSupportedException e) {
            throw new CluecumberException("Clone of AllScenariosPageCollection not supported: " + e.getMessage());
        }
        addChartJsonToReportDetails(clone);
        addCustomParametersToReportDetails(clone, propertyManager.getCustomParameters());
        return clone;
    }
}
