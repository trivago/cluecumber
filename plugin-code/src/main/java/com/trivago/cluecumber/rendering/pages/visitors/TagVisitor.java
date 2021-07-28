package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.json.pojo.Tag;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.templates.TemplateEngine;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import com.trivago.cluecumber.rendering.pages.renderering.AllScenariosPageRenderer;
import com.trivago.cluecumber.rendering.pages.renderering.AllTagsPageRenderer;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.trivago.cluecumber.rendering.pages.templates.TemplateEngine.Template.ALL_SCENARIOS;
import static com.trivago.cluecumber.rendering.pages.templates.TemplateEngine.Template.ALL_TAGS;

@Singleton
public class TagVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final AllTagsPageRenderer allTagsPageRenderer;
    private final AllScenariosPageRenderer allScenariosPageRenderer;

    @Inject
    public TagVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllTagsPageRenderer allTagsPageRenderer,
            final AllScenariosPageRenderer allScenariosPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.allTagsPageRenderer = allTagsPageRenderer;
        this.allScenariosPageRenderer = allScenariosPageRenderer;
    }

    @Override
    public void visit(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberPluginException {
        AllTagsPageCollection allTagsPageCollection = new AllTagsPageCollection(
                allScenariosPageCollection.getReports(), propertyManager.getCustomPageTitle()
        );

        // All tags page
        fileIO.writeContentToFile(
                allTagsPageRenderer.getRenderedContent(
                        allTagsPageCollection,
                        templateEngine.getTemplate(ALL_TAGS)
                ),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + PluginSettings.PAGES_DIRECTORY + "/" +
                        PluginSettings.TAG_SUMMARY_PAGE_PATH + PluginSettings.HTML_FILE_EXTENSION);

        // Scenarios by tag pages
        for (Tag tag : allTagsPageCollection.getTags()) {
            fileIO.writeContentToFile(
                    allScenariosPageRenderer.getRenderedContentByTagFilter(
                            allScenariosPageCollection, templateEngine.getTemplate(ALL_SCENARIOS), tag),
                    propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                            PluginSettings.PAGES_DIRECTORY + PluginSettings.TAG_SCENARIO_PAGE_FRAGMENT +
                            tag.getUrlFriendlyName() + PluginSettings.HTML_FILE_EXTENSION);
        }
    }
}