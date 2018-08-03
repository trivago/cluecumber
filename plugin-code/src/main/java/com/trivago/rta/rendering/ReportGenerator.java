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

package com.trivago.rta.rendering;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Tag;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReportGenerator {

    private final TemplateEngine templateEngine;
    private final FileIO fileIO;
    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;

    @Inject
    public ReportGenerator(
            final TemplateEngine templateEngine,
            final FileIO fileIO,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager
    ) {
        this.templateEngine = templateEngine;
        this.fileIO = fileIO;
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
    }

    public void generateReport(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        copyReportAssets();
        generateScenarioDetailPages(allScenariosPageCollection);
        generateFeaturePages(allScenariosPageCollection);
        generateTagPages(allScenariosPageCollection);
        generateScenarioSummaryPage(allScenariosPageCollection);
    }

    /**
     * Generate pages for features.
     *
     * @param allScenariosPageCollection The {@link AllScenariosPageCollection}.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateFeaturePages(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        // Feature summary page
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(allScenariosPageCollection.getReports());
        fileIO.writeContentToFile(
                templateEngine.getRenderedFeatureSummaryPageContent(allFeaturesPageCollection),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.FEATURE_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);

        // Feature scenario list pages
        for (Feature feature : allFeaturesPageCollection.getFeatures()) {
            fileIO.writeContentToFile(
                    templateEngine.getRenderedScenarioSummaryPageContentByFeatureFilter(allScenariosPageCollection, feature),
                    propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                            PluginSettings.PAGES_DIRECTORY + PluginSettings.FEATURE_SCENARIOS_PAGE_FRAGMENT +
                            feature.getIndex() + PluginSettings.HTML_FILE_EXTENSION);
        }
    }

    /**
     * Generate pages for tags.
     *
     * @param allScenariosPageCollection The {@link AllScenariosPageCollection}.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateTagPages(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        // Tag summary page
        AllTagsPageCollection allTagsPageCollection = new AllTagsPageCollection(allScenariosPageCollection.getReports());
        fileIO.writeContentToFile(
                templateEngine.getRenderedTagSummaryPageContent(allTagsPageCollection),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.TAG_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);

        // Tag scenario list pages
        for (Tag tag : allTagsPageCollection.getTags()) {
            fileIO.writeContentToFile(
                    templateEngine.getRenderedScenarioSummaryPageContentByTagFilter(allScenariosPageCollection, tag),
                    propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                            PluginSettings.PAGES_DIRECTORY + PluginSettings.TAG_SCENARIO_PAGE_FRAGMENT +
                            tag.getUrlFriendlyName() + PluginSettings.HTML_FILE_EXTENSION);
        }
    }

    /**
     * Generate detail pages for scenarios.
     *
     * @param allScenariosPageCollection The {@link AllScenariosPageCollection}.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateScenarioDetailPages(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        ScenarioDetailsPageCollection scenarioDetailsPageCollection;
        for (Report report : allScenariosPageCollection.getReports()) {
            for (Element element : report.getElements()) {
                scenarioDetailsPageCollection = new ScenarioDetailsPageCollection(element);
                fileIO.writeContentToFile(
                        templateEngine.getRenderedScenarioDetailPageContent(scenarioDetailsPageCollection),
                        propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                                PluginSettings.PAGES_DIRECTORY + PluginSettings.SCENARIO_DETAIL_PAGE_FRAGMENT +
                                element.getScenarioIndex() + PluginSettings.HTML_FILE_EXTENSION);
            }
        }
    }

    /**
     * Generate overview page for scenarios (this is the report start page).
     *
     * @param allScenariosPageCollection The {@link AllScenariosPageCollection}.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateScenarioSummaryPage(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        fileIO.writeContentToFile(
                templateEngine.getRenderedScenarioSummaryPageContent(allScenariosPageCollection),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                        PluginSettings.SCENARIO_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }

    /**
     * Copy all needed report assets to the specified target directory.
     *
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void copyReportAssets() throws CluecumberPluginException {
        String reportDirectory = propertyManager.getGeneratedHtmlReportDirectory();
        fileSystemManager.createDirectory(reportDirectory);
        fileSystemManager.createDirectory(
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY);
        fileSystemManager.createDirectory(reportDirectory + "/" + PluginSettings.PAGES_DIRECTORY + "/" + PluginSettings.SCENARIO_DETAIL_PAGE_PATH);
        fileSystemManager.createDirectory(reportDirectory + "/" + PluginSettings.PAGES_DIRECTORY + "/" + PluginSettings.FEATURE_SCENARIOS_PAGE_PATH);
        fileSystemManager.createDirectory(reportDirectory + "/" + PluginSettings.PAGES_DIRECTORY + "/" + PluginSettings.TAG_SCENARIO_PAGE_PATH);

        // Copy CSS resources
        fileSystemManager.createDirectory(reportDirectory + "/css");
        copyFileFromJarToReportDirectory("/css/bootstrap.min.css");
        copyFileFromJarToReportDirectory("/css/cluecumber.css");
        copyFileFromJarToReportDirectory("/css/datatables.min.css");
        copyFileFromJarToReportDirectory("/css/jquery.fancybox.min.css");
        copyFileFromJarToReportDirectory("/css/dataTables.bootstrap4.min.css");

        // Copy webfont resources
        fileSystemManager.createDirectory(reportDirectory + "/font");
        copyFileFromJarToReportDirectory("/font/cluecumber.eot");
        copyFileFromJarToReportDirectory("/font/cluecumber.svg");
        copyFileFromJarToReportDirectory("/font/cluecumber.ttf");
        copyFileFromJarToReportDirectory("/font/cluecumber.woff");
        copyFileFromJarToReportDirectory("/font/cluecumber.woff2");

        // Copy Javascript resources
        fileSystemManager.createDirectory(reportDirectory + "/js");
        copyFileFromJarToReportDirectory("/js/jquery.min.js");
        copyFileFromJarToReportDirectory("/js/bootstrap.min.js");
        copyFileFromJarToReportDirectory("/js/popper.min.js");
        copyFileFromJarToReportDirectory("/js/Chart.bundle.min.js");
        copyFileFromJarToReportDirectory("/js/datatables.min.js");
        copyFileFromJarToReportDirectory("/js/jquery.fancybox.min.js");
    }

    /**
     * Copy a specific resource from the jar file to the report directory.
     *
     * @param fileName The file name of the source inside of the jar.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void copyFileFromJarToReportDirectory(final String fileName) throws CluecumberPluginException {
        fileSystemManager.exportResource(getClass(),
                PluginSettings.BASE_TEMPLATE_PATH + fileName,
                propertyManager.getGeneratedHtmlReportDirectory() + fileName);
    }
}
