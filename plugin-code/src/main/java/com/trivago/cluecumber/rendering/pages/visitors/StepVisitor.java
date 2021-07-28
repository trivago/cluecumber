package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllStepsPageCollection;
import com.trivago.cluecumber.rendering.pages.renderering.AllScenariosPageRenderer;
import com.trivago.cluecumber.rendering.pages.renderering.AllStepsPageRenderer;
import com.trivago.cluecumber.rendering.pages.templates.TemplateEngine;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.trivago.cluecumber.rendering.pages.templates.TemplateEngine.Template.ALL_SCENARIOS;
import static com.trivago.cluecumber.rendering.pages.templates.TemplateEngine.Template.ALL_STEPS;

@Singleton
public class StepVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final AllStepsPageRenderer allStepsPageRenderer;
    private final AllScenariosPageRenderer allScenariosPageRenderer;

    @Inject
    public StepVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllStepsPageRenderer allStepsPageRenderer,
            final AllScenariosPageRenderer allScenariosPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.allStepsPageRenderer = allStepsPageRenderer;
        this.allScenariosPageRenderer = allScenariosPageRenderer;
    }

    @Override
    public void visit(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        AllStepsPageCollection allStepsPageCollection = new AllStepsPageCollection(
                allScenariosPageCollection.getReports(), propertyManager.getCustomPageTitle()
        );

        // All steps page
        fileIO.writeContentToFile(
                allStepsPageRenderer.getRenderedContent(
                        allStepsPageCollection,
                        templateEngine.getTemplate(ALL_STEPS)
                ),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.STEP_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);

        // Scenarios by step pages
        for (Step step : allStepsPageCollection.getSteps()) {
            fileIO.writeContentToFile(
                    allScenariosPageRenderer.getRenderedContentByStepFilter(
                            allScenariosPageCollection, templateEngine.getTemplate(ALL_SCENARIOS), step),
                    propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                            PluginSettings.PAGES_DIRECTORY + PluginSettings.STEP_SCENARIO_PAGE_FRAGMENT +
                            step.getUrlFriendlyName() + PluginSettings.HTML_FILE_EXTENSION);
        }
    }
}