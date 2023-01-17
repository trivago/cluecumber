package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.renderering.AllFeaturesPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.renderering.AllScenariosPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.renderering.TreeViewPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.ALL_FEATURES;
import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.ALL_SCENARIOS;
import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.TREE_VIEW;

@Singleton
public class FeatureVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final AllFeaturesPageRenderer allFeaturesPageRenderer;
    private final AllScenariosPageRenderer allScenariosPageRenderer;
    private final TreeViewPageRenderer treeViewPageRenderer;

    @Inject
    public FeatureVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllFeaturesPageRenderer allFeaturesPageRenderer,
            final AllScenariosPageRenderer allScenariosPageRenderer,
            final TreeViewPageRenderer treeViewPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.allFeaturesPageRenderer = allFeaturesPageRenderer;
        this.allScenariosPageRenderer = allScenariosPageRenderer;
        this.treeViewPageRenderer = treeViewPageRenderer;
    }

    @Override
    public void visit(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberException {
        AllFeaturesPageCollection allFeaturesPageCollection =
                new AllFeaturesPageCollection(allScenariosPageCollection.getReports(), propertyManager.getCustomPageTitle());

        // All features summary page
        fileIO.writeContentToFile(
                allFeaturesPageRenderer.getRenderedContent(allFeaturesPageCollection,
                        templateEngine.getTemplate(ALL_FEATURES)),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.FEATURE_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);

        // Scenarios by feature pages
        for (Feature feature : allFeaturesPageCollection.getFeatures()) {
            fileIO.writeContentToFile(
                    allScenariosPageRenderer.getRenderedContentByFeatureFilter(
                            allScenariosPageCollection,
                            templateEngine.getTemplate(ALL_SCENARIOS),
                            feature
                    ),
                    propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                            PluginSettings.PAGES_DIRECTORY + PluginSettings.FEATURE_SCENARIOS_PAGE_FRAGMENT +
                            feature.getIndex() + PluginSettings.HTML_FILE_EXTENSION);
        }

        // Tree view page
        fileIO.writeContentToFile(
                treeViewPageRenderer.getRenderedContent(
                        allFeaturesPageCollection,
                        allScenariosPageCollection,
                        templateEngine.getTemplate(TREE_VIEW)),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.TREE_VIEW_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }
}
