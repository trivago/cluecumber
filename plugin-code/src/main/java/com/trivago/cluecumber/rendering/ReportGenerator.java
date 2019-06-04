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

package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileSystemManager;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.visitors.FeatureVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.PageVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.ScenarioVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.StepVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.TagVisitor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ReportGenerator {

    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;

    private List<PageVisitor> visitors = new ArrayList<>();

    @Inject
    public ReportGenerator(
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final ScenarioVisitor scenarioVisitor,
            final FeatureVisitor featureVisitor,
            final TagVisitor tagVisitor,
            final StepVisitor stepVisitor
    ) {
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;

        visitors.add(scenarioVisitor);
        visitors.add(featureVisitor);
        visitors.add(tagVisitor);
        visitors.add(stepVisitor);
    }

    /**
     * Generate the full report.
     * @param allScenariosPageCollection {{@link AllScenariosPageCollection}.
     * @throws CluecumberPluginException In case of error.
     */
    public void generateReport(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        copyReportAssets();
        for (PageVisitor visitor : visitors) {
            allScenariosPageCollection.accept(visitor);
        }
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
        fileSystemManager.createDirectory(reportDirectory + "/" + PluginSettings.PAGES_DIRECTORY + "/" + PluginSettings.STEP_SCENARIO_PAGE_PATH);

        // Copy CSS resources
        fileSystemManager.createDirectory(reportDirectory + "/css");
        copyFileFromJarToReportDirectory("/css/bootstrap.min.css");
        copyFileFromJarToReportDirectory("/css/cluecumber.css");
        copyFileFromJarToReportDirectory("/css/datatables.min.css");
        copyFileFromJarToReportDirectory("/css/jquery.fancybox.min.css");
        copyFileFromJarToReportDirectory("/css/dataTables.bootstrap4.min.css");

        // Either use the custom CSS or the dummy CSS files that comes with Cluecumber
        String customCss = propertyManager.getCustomCss();
        if (customCss != null && !customCss.isEmpty()) {
            fileSystemManager.copyResource(customCss, reportDirectory + "/css/cluecumber_custom.css");
        } else {
            copyFileFromJarToReportDirectory("/css/cluecumber_custom.css");
        }

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
