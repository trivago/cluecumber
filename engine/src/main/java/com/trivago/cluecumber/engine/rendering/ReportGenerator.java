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
package com.trivago.cluecumber.engine.rendering;

import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.exceptions.filesystem.PathCreationException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.filesystem.FileSystemManager;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.StartPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.renderering.CustomCssRenderer;
import com.trivago.cluecumber.engine.rendering.pages.renderering.StartPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine;
import com.trivago.cluecumber.engine.rendering.pages.visitors.PageVisitor;
import com.trivago.cluecumber.engine.rendering.pages.visitors.VisitorDirectory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static com.trivago.cluecumber.engine.constants.Settings.HTML_FILE_EXTENSION;
import static com.trivago.cluecumber.engine.constants.Settings.START_PAGE;

/**
 * The main report generator.
 */
@Singleton
public class ReportGenerator {
    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;

    private final StartPageRenderer startPageRenderer;
    private final CustomCssRenderer customCssRenderer;
    private final List<PageVisitor> visitors;

    /**
     * Constructor for dependency injection.
     *
     * @param customCssRenderer The {@link CustomCssRenderer} instance.
     * @param fileIO            The {@link FileIO} instance.
     * @param fileSystemManager The {@link FileSystemManager} instance.
     * @param propertyManager   The {@link PropertyManager} instance.
     * @param startPageRenderer The {@link StartPageRenderer} instance.
     * @param templateEngine    The {@link TemplateEngine} instance.
     * @param visitorDirectory  The {@link VisitorDirectory} instance.
     */
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
     * @throws CluecumberException In case of error.
     */
    public void generateReport(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberException {
        String reportDirectory = propertyManager.getGeneratedHtmlReportDirectory();
        createDirectories(reportDirectory);
        copyStaticReportAssets(reportDirectory);
        copyCustomCss(reportDirectory);
        copyCustomFavicon(reportDirectory);

        boolean redirectToFirstScenarioPage =
                propertyManager.getStartPage() == Settings.StartPage.ALL_SCENARIOS &&
                allScenariosPageCollection.getTotalNumberOfScenarios() == 1;

        generateStartPage(redirectToFirstScenarioPage);
        for (PageVisitor visitor : visitors) {
            allScenariosPageCollection.accept(visitor);
        }
    }

    private void generateStartPage(boolean redirectToFirstScenarioPage) throws CluecumberException {
        StartPageCollection startPageCollection = new StartPageCollection(propertyManager.getStartPage(), redirectToFirstScenarioPage);
        fileIO.writeContentToFile(startPageRenderer.getRenderedContent(
                templateEngine.getTemplate(TemplateEngine.Template.START_PAGE),
                startPageCollection,
                propertyManager.getNavigationLinks()
        ), propertyManager.getGeneratedHtmlReportDirectory() + "/" + START_PAGE + HTML_FILE_EXTENSION);
    }

    /**
     * Create all needed sub directories in the specified target directory.
     *
     * @throws PathCreationException The {@link PathCreationException}.
     */
    private void createDirectories(final String reportDirectory) throws PathCreationException {
        fileSystemManager.createDirectory(reportDirectory);
        fileSystemManager.createDirectory(
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + Settings.PAGES_DIRECTORY);
        String pagesDirectory = reportDirectory + "/" + Settings.PAGES_DIRECTORY + "/";
        fileSystemManager.createDirectory(pagesDirectory + Settings.SCENARIO_DETAIL_PAGE_PATH);
        fileSystemManager.createDirectory(pagesDirectory + Settings.FEATURE_SCENARIOS_PAGE_PATH);
        fileSystemManager.createDirectory(pagesDirectory + Settings.TAG_SCENARIO_PAGE_PATH);
        fileSystemManager.createDirectory(pagesDirectory + Settings.STEP_SCENARIO_PAGE_PATH);
    }

    /**
     * Render and copy custom css assets to the specified target directory.
     *
     * @throws CluecumberException The {@link CluecumberException}.
     */
    private void copyCustomCss(final String reportDirectory) throws CluecumberException {
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
     * Copy custom favicon to the specified target directory.
     */
    private void copyCustomFavicon(final String reportDirectory) throws CluecumberException {
        String customFavicon = propertyManager.getCustomFaviconFile();
        if (customFavicon != null && !customFavicon.isEmpty()) {
            fileSystemManager.copyResource(customFavicon, reportDirectory + "/img/favicon.png");
        } else {
            copyFileFromJarToReportDirectory("/img/favicon.png");
        }
    }

    /**
     * Copy all needed static report assets to the specified target directory.
     *
     * @throws CluecumberException The {@link CluecumberException}.
     */
    private void copyStaticReportAssets(final String reportDirectory) throws CluecumberException {
        // Copy image resources
        fileSystemManager.createDirectory(reportDirectory + "/img");

        // Copy CSS resources
        fileSystemManager.createDirectory(reportDirectory + "/css");
        copyFileFromJarToReportDirectory("/css/bootstrap.min.css");
        copyFileFromJarToReportDirectory("/css/cluecumber.css");
        copyFileFromJarToReportDirectory("/css/cluecumber-dark-mode.css");
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
     * @throws CluecumberException The {@link CluecumberException}.
     */
    private void copyFileFromJarToReportDirectory(final String fileName) throws CluecumberException {
        fileSystemManager.copyResourceFromJar(
                Settings.BASE_TEMPLATE_PATH + fileName,
                propertyManager.getGeneratedHtmlReportDirectory() + fileName);
    }
}
