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
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.FeatureSummaryPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import com.trivago.rta.rendering.pages.pojos.TagSummaryPageCollection;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ReportGenerator {

    private TemplateEngine templateEngine;
    private FileIO fileIO;
    private PropertyManager propertyManager;
    private FileSystemManager fileSystemManager;
    private CluecumberLogger logger;

    @Inject
    public ReportGenerator(
            final TemplateEngine templateEngine,
            final FileIO fileIO,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final CluecumberLogger logger
    ) {
        this.templateEngine = templateEngine;
        this.fileIO = fileIO;
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
        this.logger = logger;
    }

    public void generateReport(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        copyReportAssets();
        fileSystemManager.createDirectory(
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY);

        List<Report> reports = startPageCollection.getReports();
        generateScenarioDetailPages(reports);
        generateFeatureSummaryPage(reports);
        generateTagSummaryPage(reports);
        generateStartPage(startPageCollection);
    }

    /**
     * Generate overview pages for features.
     *
     * @param reports The {@link Report} list.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateFeatureSummaryPage(final List<Report> reports) throws CluecumberPluginException {
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                System.out.println("Feature: " + report.getId() + " => " + report.getName());
            }
        }
        FeatureSummaryPageCollection featureSummaryPageCollection = new FeatureSummaryPageCollection(reports);
        fileIO.writeContentToFile(
                templateEngine.getRenderedFeatureSummaryPageContent(featureSummaryPageCollection),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.FEATURE_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }

    /**
     * Generate overview page for tags.
     *
     * @param reports The {@link Report} list.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateTagSummaryPage(final List<Report> reports) throws CluecumberPluginException {
        TagSummaryPageCollection tagSummaryPageCollection = new TagSummaryPageCollection(reports);
        fileIO.writeContentToFile(
                templateEngine.getRenderedTagSummaryPageContent(tagSummaryPageCollection),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.TAG_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }

    /**
     * Generate detail pages for scenarios.
     *
     * @param reports The {@link Report} list.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateScenarioDetailPages(final List<Report> reports) throws CluecumberPluginException {
        DetailPageCollection detailPageCollection;
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                detailPageCollection = new DetailPageCollection(element);
                fileIO.writeContentToFile(
                        templateEngine.getRenderedDetailPageContent(detailPageCollection),
                        propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                                PluginSettings.PAGES_DIRECTORY + PluginSettings.SCENARIO_DETAIL_PAGE_FRAGMENT +
                                element.getScenarioIndex() + PluginSettings.HTML_FILE_EXTENSION);
            }
        }
    }

    /**
     * Generate overview page for scenarios (this is the report start page).
     *
     * @param startPageCollection The {@link StartPageCollection}.
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void generateStartPage(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        fileIO.writeContentToFile(
                templateEngine.getRenderedStartPageContent(startPageCollection),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                        PluginSettings.SCENARIO_OVERVIEW_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }

    /**
     * Copy all needed report assets to the specified target directory.
     *
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void copyReportAssets() throws CluecumberPluginException {
        String reportDirectory = propertyManager.getGeneratedHtmlReportDirectory();
        fileSystemManager.createDirectory(reportDirectory);
        fileSystemManager.createDirectory(reportDirectory + "/js");
        fileSystemManager.createDirectory(reportDirectory + "/css");

        // Copy CSS resources
        copyFileFromJarToReportDirectory("/css/bootstrap.min.css");
        copyFileFromJarToReportDirectory("/css/cluecumber.css");
        copyFileFromJarToReportDirectory("/css/datatables.min.css");
        copyFileFromJarToReportDirectory("/css/jquery.fancybox.min.css");
        copyFileFromJarToReportDirectory("/css/dataTables.bootstrap4.min.css");

        // Copy Javascript resources
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
