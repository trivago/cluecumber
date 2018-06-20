package com.trivago.rta.rendering;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.rendering.pages.pojos.pagecollections.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.FeatureSummaryPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioSummaryPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.TagSummaryPageCollection;
import com.trivago.rta.rendering.pages.renderers.FeatureSummaryPageRenderer;
import com.trivago.rta.rendering.pages.renderers.ScenarioDetailPageRenderer;
import com.trivago.rta.rendering.pages.renderers.StartPageRenderer;
import com.trivago.rta.rendering.pages.renderers.TagSummaryPageRenderer;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateEngineTest {
    private TemplateConfiguration templateConfiguration;
    private FeatureSummaryPageRenderer featureSummaryPageRenderer;
    private TagSummaryPageRenderer tagSummaryPageRenderer;
    private ScenarioDetailPageRenderer scenarioDetailPageRenderer;
    private StartPageRenderer startPageRenderer;

    private TemplateEngine templateEngine;

    @Before
    public void setup() {
        templateConfiguration = mock(TemplateConfiguration.class);
        featureSummaryPageRenderer = mock(FeatureSummaryPageRenderer.class);
        tagSummaryPageRenderer = mock(TagSummaryPageRenderer.class);
        scenarioDetailPageRenderer = mock(ScenarioDetailPageRenderer.class);
        startPageRenderer = mock(StartPageRenderer.class);
        templateEngine = new TemplateEngine(
                templateConfiguration,
                featureSummaryPageRenderer,
                tagSummaryPageRenderer,
                scenarioDetailPageRenderer,
                startPageRenderer
        );
    }

    @Test
    public void getRenderedFeatureSummaryPageTest() throws CluecumberPluginException {
        FeatureSummaryPageCollection featureSummaryPageCollection = new FeatureSummaryPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.FEATURE_SUMMARY_TEMPLATE)).thenReturn(template);
        when(featureSummaryPageRenderer.getRenderedContent(featureSummaryPageCollection, template)).thenReturn("FEATURE_PAGE_CONTENT");
        String renderedFeatureSummaryPage = templateEngine.getRenderedFeatureSummaryPageContent(featureSummaryPageCollection);
        assertThat(renderedFeatureSummaryPage, is("<html>\n <head></head>\n <body>\n  FEATURE_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedTagSummaryPageTest() throws CluecumberPluginException {
        TagSummaryPageCollection tagSummaryPageCollection = new TagSummaryPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.TAG_SUMMARY_TEMPLATE)).thenReturn(template);
        when(tagSummaryPageRenderer.getRenderedContent(tagSummaryPageCollection, template)).thenReturn("TAG_PAGE_CONTENT");
        String renderedTagSummaryPage = templateEngine.getRenderedTagSummaryPageContent(tagSummaryPageCollection);
        assertThat(renderedTagSummaryPage, is("<html>\n <head></head>\n <body>\n  TAG_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedDetailPageTest() throws CluecumberPluginException {
        DetailPageCollection detailPageCollection = new DetailPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_DETAIL_TEMPLATE)).thenReturn(template);
        when(scenarioDetailPageRenderer.getRenderedContent(detailPageCollection, template)).thenReturn("DETAIL_PAGE_CONTENT");
        String renderedDetailPage = templateEngine.getRenderedDetailPageContent(detailPageCollection);
        assertThat(renderedDetailPage, is("<html>\n <head></head>\n <body>\n  DETAIL_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedStartPageTest() throws CluecumberPluginException {
        ScenarioSummaryPageCollection scenarioSummaryPageCollection = new ScenarioSummaryPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_OVERVIEW_TEMPLATE)).thenReturn(template);
        when(startPageRenderer.getRenderedContent(scenarioSummaryPageCollection, template)).thenReturn("START_PAGE_CONTENT");
        String renderedStartPage = templateEngine.getRenderedStartPageContent(scenarioSummaryPageCollection);
        assertThat(renderedStartPage, is("<html>\n <head></head>\n <body>\n  START_PAGE_CONTENT\n </body>\n</html>"));
    }
}
