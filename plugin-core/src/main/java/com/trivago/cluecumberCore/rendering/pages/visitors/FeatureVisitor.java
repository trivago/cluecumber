package com.trivago.cluecumberCore.rendering.pages.visitors;

import com.trivago.cluecumberCore.constants.PluginSettings;
import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.filesystem.FileIO;
import com.trivago.cluecumberCore.properties.PropertyManager;
import com.trivago.cluecumberCore.rendering.pages.templates.TemplateEngine;
import com.trivago.cluecumberCore.rendering.pages.pojos.Feature;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllFeaturesPageRenderer;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllScenariosPageRenderer;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.trivago.cluecumberCore.rendering.pages.templates.TemplateEngine.Template.ALL_FEATURES;
import static com.trivago.cluecumberCore.rendering.pages.templates.TemplateEngine.Template.ALL_SCENARIOS;

@Singleton
public class FeatureVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final AllFeaturesPageRenderer allFeaturesPageRenderer;
    private final AllScenariosPageRenderer allScenariosPageRenderer;

    @Inject
    public FeatureVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllFeaturesPageRenderer allFeaturesPageRenderer,
            final AllScenariosPageRenderer allScenariosPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.allFeaturesPageRenderer = allFeaturesPageRenderer;
        this.allScenariosPageRenderer = allScenariosPageRenderer;
    }

    @Override
    public void visit(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
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
    }
}
