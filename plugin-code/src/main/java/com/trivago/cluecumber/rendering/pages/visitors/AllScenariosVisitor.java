package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.TemplateEngine;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.renderers.AllScenariosPageRenderer;

import javax.inject.Inject;

import static com.trivago.cluecumber.rendering.TemplateEngine.Template.ALL_SCENARIOS;

public class AllScenariosVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private AllScenariosPageRenderer pageRenderer;

    @Inject
    public AllScenariosVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllScenariosPageRenderer allScenariosPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        pageRenderer = allScenariosPageRenderer;
    }

    @Override
    public void visit(final AllScenariosPageCollection pageCollection) throws CluecumberPluginException {
        fileIO.writeContentToFile(
                pageRenderer.getRenderedContent(pageCollection,
                        templateEngine.getTemplate(ALL_SCENARIOS)),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                        PluginSettings.SCENARIO_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);
    }
}
