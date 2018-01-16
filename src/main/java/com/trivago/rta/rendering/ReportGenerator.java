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

    private static final String BASE_PACKAGE_PATH = "/template";
    private static final String SCENARIO_DETAIL_DIR = "/scenario-detail";

    private TemplateEngine templateEngine;
    private FileIO fileIO;
    private PropertyManager propertyManager;
    private FileSystemManager fileSystemManager;

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
    }

    public void generateReports(final StartPageCollection startPageCollection) throws CluecumberPluginException {
        templateEngine.init(getClass(), BASE_PACKAGE_PATH);
        copyResources();
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + SCENARIO_DETAIL_DIR);

        List<Report> reports = startPageCollection.getReports();
        DetailPageCollection detailPageCollection;
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                detailPageCollection = new DetailPageCollection(element);
                String renderedDetailPage = templateEngine.getRenderedDetailPage(detailPageCollection);
                savePage(renderedDetailPage, SCENARIO_DETAIL_DIR + "/scenario_" + element.getScenarioIndex() + ".html");
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

    private void copyResources() throws CluecumberPluginException {
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory());
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/js");
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/img");
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/css");

        // Copy CSS resources
        copyResource("/css/bootstrap.min.css");
        copyResource("/css/cluecumber.css");
        copyResource("/css/dataTables.bootstrap4.min.css");
        copyResource("/css/jquery.fancybox.min.css");

        // Copy Javascript resources
        copyResource("/js/jquery-3.2.1.slim.min.js");
        copyResource("/js/bootstrap.min.js");
        copyResource("/js/popper.min.js");
        copyResource("/js/Chart.bundle.min.js");
        copyResource("/js/dataTables.bootstrap4.min.js");
        copyResource("/js/jquery.dataTables.min.js");
        copyResource("/js/jquery.fancybox.min.js");
    }

    private void copyResource(final String fileName) throws CluecumberPluginException {
        fileSystemManager.exportResource(getClass(),
                BASE_PACKAGE_PATH + fileName,
                propertyManager.getGeneratedHtmlReportDirectory() + fileName);
    }
}
