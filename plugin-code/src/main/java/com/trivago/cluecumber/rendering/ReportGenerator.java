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

package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.exceptions.filesystem.PathCreationException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.filesystem.FileSystemManager;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.StartPageCollection;
import com.trivago.cluecumber.rendering.pages.renderering.CustomCssRenderer;
import com.trivago.cluecumber.rendering.pages.renderering.StartPageRenderer;
import com.trivago.cluecumber.rendering.pages.templates.TemplateEngine;
import com.trivago.cluecumber.rendering.pages.visitors.PageVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.VisitorDirectory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static com.trivago.cluecumber.constants.PluginSettings.HTML_FILE_EXTENSION;
import static com.trivago.cluecumber.constants.PluginSettings.START_PAGE_PATH;

@Singleton
public class ReportGenerator {
    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;

    private final StartPageRenderer startPageRenderer;
    private final CustomCssRenderer customCssRenderer;
    private final List<PageVisitor> visitors;

    @Inject
    ReportGenerator(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final StartPageRenderer startPageRenderer,
            final CustomCssRenderer customCssRenderer,
            final VisitorDirectory visitorDirectory
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
        this.startPageRenderer = startPageRenderer;
        this.customCssRenderer = customCssRenderer;

        visitors = visitorDirectory.getVisitors();
    }

    /**
     * Generate the full report.
     *
     * @param allScenariosPageCollection {{@link AllScenariosPageCollection}.
     * @throws CluecumberPluginException In case of error.
     */
    public void generateReport(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        String reportDirectory = propertyManager.getGeneratedHtmlReportDirectory();
        createDirectories(reportDirectory);
        copyStaticReportAssets(reportDirectory);
        copyCustomCss(reportDirectory);

        boolean redirectToFirstScenarioPage =
                propertyManager.getStartPage() == PluginSettings.StartPage.ALL_SCENARIOS &&
                        allScenariosPageCollection.getTotalNumberOfScenarios() == 1;

        generateStartPage(redirectToFirstScenarioPage);
        for (PageVisitor visitor : visitors) {
            allScenariosPageCollection.accept(visitor);
        }
    }

    private void generateStartPage(boolean redirectToFirstScenarioPage) throws CluecumberPluginException {
        StartPageCollection startPageCollection = new StartPageCollection(propertyManager.getStartPage(), redirectToFirstScenarioPage);
        fileIO.writeContentToFile(startPageRenderer.getRenderedContent(
                templateEngine.getTemplate(TemplateEngine.Template.START_PAGE), startPageCollection
        ), propertyManager.getGeneratedHtmlReportDirectory() + "/" + START_PAGE_PATH + HTML_FILE_EXTENSION);
    }

    /**
     * Create all needed sub directories in the specified target directory.
     *
     * @throws PathCreationException The {@link PathCreationException}.
     */
    private void createDirectories(final String reportDirectory) throws PathCreationException {
        fileSystemManager.createDirectory(reportDirectory);
        fileSystemManager.createDirectory(
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY);
        String pagesDirectory = reportDirectory + "/" + PluginSettings.PAGES_DIRECTORY + "/";
        fileSystemManager.createDirectory(pagesDirectory + PluginSettings.SCENARIO_DETAIL_PAGE_PATH);
        fileSystemManager.createDirectory(pagesDirectory + PluginSettings.FEATURE_SCENARIOS_PAGE_PATH);
        fileSystemManager.createDirectory(pagesDirectory + PluginSettings.TAG_SCENARIO_PAGE_PATH);
        fileSystemManager.createDirectory(pagesDirectory + PluginSettings.STEP_SCENARIO_PAGE_PATH);
    }

    /**
     * Render and copy custom css assets to the specified target directory.
     *
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void copyCustomCss(final String reportDirectory) throws CluecumberPluginException {
        // Either use the specified custom CSS file or the empty cluecumber-additional.css file that comes with Cluecumber
        String customCss = propertyManager.getCustomCssFile();
        if (customCss != null && !customCss.isEmpty()) {
            fileSystemManager.copyResource(customCss, reportDirectory + "/css/cluecumber-additional.css");
        } else {
            copyFileFromJarToReportDirectory("/css/cluecumber-additional.css");
        }

        // Render the custom-css.ftl and copy it to the css directory
        fileIO.writeContentToFile(customCssRenderer.getRenderedCustomCssContent(
                templateEngine.getTemplate(TemplateEngine.Template.CUSTOM_CSS)
        ), reportDirectory + "/css/cluecumber-custom.css");
    }

    /**
     * Copy all needed static report assets to the specified target directory.
     *
     * @throws CluecumberPluginException The {@link CluecumberPluginException}.
     */
    private void copyStaticReportAssets(final String reportDirectory) throws CluecumberPluginException {
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
        fileSystemManager.copyResourceFromJar(
                PluginSettings.BASE_TEMPLATE_PATH + fileName,
                propertyManager.getGeneratedHtmlReportDirectory() + fileName);
    }
}
