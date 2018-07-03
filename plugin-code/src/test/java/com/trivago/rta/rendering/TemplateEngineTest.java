package com.trivago.rta.rendering;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import com.trivago.rta.rendering.pages.renderers.AllFeaturesPageRenderer;
import com.trivago.rta.rendering.pages.renderers.AllScenariosPageRenderer;
import com.trivago.rta.rendering.pages.renderers.AllTagsPageRenderer;
import com.trivago.rta.rendering.pages.renderers.ScenarioDetailsPageRenderer;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateEngineTest {
    private TemplateConfiguration templateConfiguration;
    private AllFeaturesPageRenderer allFeaturesPageRenderer;
    private AllTagsPageRenderer allTagsPageRenderer;
    private ScenarioDetailsPageRenderer scenarioDetailsPageRenderer;
    private AllScenariosPageRenderer allScenariosPageRenderer;

    private TemplateEngine templateEngine;

    @Before
    public void setup() {
        templateConfiguration = mock(TemplateConfiguration.class);
        allFeaturesPageRenderer = mock(AllFeaturesPageRenderer.class);
        allTagsPageRenderer = mock(AllTagsPageRenderer.class);
        scenarioDetailsPageRenderer = mock(ScenarioDetailsPageRenderer.class);
        allScenariosPageRenderer = mock(AllScenariosPageRenderer.class);
        templateEngine = new TemplateEngine(
                templateConfiguration,
                allFeaturesPageRenderer,
                allTagsPageRenderer,
                scenarioDetailsPageRenderer,
                allScenariosPageRenderer
        );
    }

    @Test
    public void getRenderedFeatureSummaryPageTest() throws CluecumberPluginException {
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.FEATURE_SUMMARY_TEMPLATE)).thenReturn(template);
        when(allFeaturesPageRenderer.getRenderedContent(allFeaturesPageCollection, template)).thenReturn("FEATURE_PAGE_CONTENT");
        String renderedFeatureSummaryPage = templateEngine.getRenderedFeatureSummaryPageContent(allFeaturesPageCollection);
        assertThat(renderedFeatureSummaryPage, is("<html>\n <head></head>\n <body>\n  FEATURE_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedTagSummaryPageTest() throws CluecumberPluginException {
        AllTagsPageCollection allTagsPageCollection = new AllTagsPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.TAG_SUMMARY_TEMPLATE)).thenReturn(template);
        when(allTagsPageRenderer.getRenderedContent(allTagsPageCollection, template)).thenReturn("TAG_PAGE_CONTENT");
        String renderedTagSummaryPage = templateEngine.getRenderedTagSummaryPageContent(allTagsPageCollection);
        assertThat(renderedTagSummaryPage, is("<html>\n <head></head>\n <body>\n  TAG_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedDetailPageTest() throws CluecumberPluginException {
        ScenarioDetailsPageCollection scenarioDetailsPageCollection = new ScenarioDetailsPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_DETAIL_TEMPLATE)).thenReturn(template);
        when(scenarioDetailsPageRenderer.getRenderedContent(scenarioDetailsPageCollection, template)).thenReturn("DETAIL_PAGE_CONTENT");
        String renderedDetailPage = templateEngine.getRenderedScenarioDetailPageContent(scenarioDetailsPageCollection);
        assertThat(renderedDetailPage, is("<html>\n <head></head>\n <body>\n  DETAIL_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedStartPageTest() throws CluecumberPluginException {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE)).thenReturn(template);
        when(allScenariosPageRenderer.getRenderedContent(allScenariosPageCollection, template)).thenReturn("START_PAGE_CONTENT");
        String renderedStartPage = templateEngine.getRenderedScenarioSummaryPageContent(allScenariosPageCollection);
        assertThat(renderedStartPage, is("<html>\n <head></head>\n <body>\n  START_PAGE_CONTENT\n </body>\n</html>"));
    }
}
