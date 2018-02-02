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
import com.trivago.rta.exceptions.filesystem.FileCreationException;
import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;

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

    public void generateReports(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        templateEngine.init(getClass(), PluginSettings.BASE_TEMPLATE_PATH);
        copyReportAssets();
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIR);

        List<Report> reports = startPageCollection.getReports();
        DetailPageCollection detailPageCollection;
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                detailPageCollection = new DetailPageCollection(element);
                String renderedDetailPage = templateEngine.getRenderedDetailPage(detailPageCollection);
                savePage(renderedDetailPage, PluginSettings.PAGES_DIR + "/scenario_" + element.getScenarioIndex() + ".html");
            }
        }

        String renderedStartPage = templateEngine.getRenderedStartPage(startPageCollection);
        fileIO.writeContentToFile(renderedStartPage, propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.START_PAGE_NAME);
    }

    private void savePage(final String renderedDetailPage, final String fileName) throws FileCreationException {
        fileIO.writeContentToFile(
                renderedDetailPage,
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + fileName);
    }

    private void copyReportAssets() throws CluecumberPluginException {
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory());
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/js");
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/img");
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/css");

        // Copy CSS resources
        copyFileFromJarToFilesystemDestination("/css/bootstrap.min.css");
        copyFileFromJarToFilesystemDestination("/css/cluecumber.css");
        copyFileFromJarToFilesystemDestination("/css/dataTables.bootstrap4.min.css");
        copyFileFromJarToFilesystemDestination("/css/jquery.fancybox.min.css");

        // Copy Javascript resources
        copyFileFromJarToFilesystemDestination("/js/jquery-3.2.1.slim.min.js");
        copyFileFromJarToFilesystemDestination("/js/bootstrap.min.js");
        copyFileFromJarToFilesystemDestination("/js/popper.min.js");
        copyFileFromJarToFilesystemDestination("/js/Chart.bundle.min.js");
        copyFileFromJarToFilesystemDestination("/js/dataTables.bootstrap4.min.js");
        copyFileFromJarToFilesystemDestination("/js/jquery.dataTables.min.js");
        copyFileFromJarToFilesystemDestination("/js/jquery.fancybox.min.js");
    }

    private void copyFileFromJarToFilesystemDestination(final String fileName) throws CluecumberPluginException {
        fileSystemManager.exportResource(getClass(),
                PluginSettings.BASE_TEMPLATE_PATH + fileName,
                propertyManager.getGeneratedHtmlReportDirectory() + fileName);
    }
}
