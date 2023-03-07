package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.renderering.AllScenariosPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.renderering.ScenarioDetailsPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.ALL_SCENARIOS;
import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.SCENARIO_DETAILS;
import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.SCENARIO_SEQUENCE;

/**
 * The visitor for scenario related pages.
 */
@Singleton
public class ScenarioVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final AllScenariosPageRenderer allScenariosPageRenderer;
    private final ScenarioDetailsPageRenderer scenarioDetailsPageRenderer;

    /**
     * The constructor for dependency injection.
     *
     * @param fileIO                      The {@link FileIO} instance.
     * @param templateEngine              The Freemarker template engine.
     * @param propertyManager             The {@link PropertyManager} instance.
     * @param allScenariosPageRenderer    The renderer for the scenario pages.
     * @param scenarioDetailsPageRenderer The renderer for scenario detail pages.
     */
    @Inject
    public ScenarioVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllScenariosPageRenderer allScenariosPageRenderer,
            final ScenarioDetailsPageRenderer scenarioDetailsPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.allScenariosPageRenderer = allScenariosPageRenderer;
        this.scenarioDetailsPageRenderer = scenarioDetailsPageRenderer;
    }

    /**
     * The main method that is called on this visitor.
     *
     * @param allScenariosPageCollection The scenarios page collection.
     * @throws CluecumberException Thrown on all errors.
     */
    @Override
    public void visit(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberException {
        // All scenarios page
        fileIO.writeContentToFile(
                allScenariosPageRenderer.getRenderedContent(
                        allScenariosPageCollection,
                        templateEngine.getTemplate(ALL_SCENARIOS)
                ),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.SCENARIO_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);

        // Scenario sequence page
        fileIO.writeContentToFile(
                allScenariosPageRenderer.getRenderedContent(
                        allScenariosPageCollection,
                        templateEngine.getTemplate(SCENARIO_SEQUENCE)
                ),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.SCENARIO_SEQUENCE_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);

        // Scenario detail pages
        ScenarioDetailsPageCollection scenarioDetailsPageCollection;
        for (Report report : allScenariosPageCollection.getReports()) {
            for (Element element : report.getElements()) {
                scenarioDetailsPageCollection = new ScenarioDetailsPageCollection(element, propertyManager.getCustomPageTitle());
                fileIO.writeContentToFile(
                        scenarioDetailsPageRenderer.getRenderedContent(
                                scenarioDetailsPageCollection,
                                templateEngine.getTemplate(SCENARIO_DETAILS)
                        ),
                        propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                                PluginSettings.PAGES_DIRECTORY + PluginSettings.SCENARIO_DETAIL_PAGE_FRAGMENT +
                                element.getScenarioIndex() + PluginSettings.HTML_FILE_EXTENSION);
            }
        }
    }
}
