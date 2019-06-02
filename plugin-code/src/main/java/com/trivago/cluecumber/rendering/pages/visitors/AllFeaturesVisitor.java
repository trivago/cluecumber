package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.TemplateEngine;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.renderers.AllFeaturesPageRenderer;

import javax.inject.Inject;

import static com.trivago.cluecumber.rendering.TemplateEngine.Template.ALL_FEATURES;

public class AllFeaturesVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private AllFeaturesPageRenderer pageRenderer;

    @Inject
    public AllFeaturesVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllFeaturesPageRenderer allScenariosPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        pageRenderer = allScenariosPageRenderer;
    }

    @Override
    public void visit(final AllScenariosPageCollection pageCollection) throws CluecumberPluginException {
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(pageCollection.getReports());
        fileIO.writeContentToFile(
                pageRenderer.getRenderedContent(allFeaturesPageCollection,
                        templateEngine.getTemplate(ALL_FEATURES)),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.FEATURE_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }
}
