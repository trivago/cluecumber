package com.trivago.rta.rendering;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.rendering.pages.ScenarioDetailPageRenderer;
import com.trivago.rta.rendering.pages.StartPageRenderer;
import com.trivago.rta.rendering.pages.TagSummaryPageRenderer;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import com.trivago.rta.rendering.pages.pojos.TagSummaryPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateEngineTest {
    private StartPageRenderer startPageRenderer;
    private ScenarioDetailPageRenderer scenarioDetailPageRenderer;
    private TemplateConfiguration templateConfiguration;
    private TagSummaryPageRenderer tagSummaryPageRenderer;

    private TemplateEngine templateEngine;

    @Before
    public void setup() {
        startPageRenderer = mock(StartPageRenderer.class);
        scenarioDetailPageRenderer = mock(ScenarioDetailPageRenderer.class);
        tagSummaryPageRenderer = mock(TagSummaryPageRenderer.class);
        templateConfiguration = mock(TemplateConfiguration.class);
        tagSummaryPageRenderer = mock(TagSummaryPageRenderer.class);
        templateEngine = new TemplateEngine(
                templateConfiguration, startPageRenderer, scenarioDetailPageRenderer, tagSummaryPageRenderer
        );
    }

    @Test
    public void getRenderedStartPageTest() throws CluecumberPluginException {
        StartPageCollection startPageCollection = new StartPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate("index")).thenReturn(template);
        when(startPageRenderer.getRenderedContent(startPageCollection, template)).thenReturn("START_PAGE_CONTENT");
        String renderedStartPage = templateEngine.getRenderedStartPageContent(startPageCollection);
        assertThat(renderedStartPage, is("<html>\n <head></head>\n <body>\n  START_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedDetailPageTest() throws CluecumberPluginException {
        DetailPageCollection detailPageCollection = new DetailPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate("scenario-detail")).thenReturn(template);
        when(scenarioDetailPageRenderer.getRenderedContent(detailPageCollection, template)).thenReturn("DETAIL_PAGE_CONTENT");
        String renderedDetailPage = templateEngine.getRenderedDetailPageContent(detailPageCollection);
        assertThat(renderedDetailPage, is("<html>\n <head></head>\n <body>\n  DETAIL_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedTagPageTest() throws CluecumberPluginException {
        TagSummaryPageCollection tagSummaryPageCollection = new TagSummaryPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate("tag-summary")).thenReturn(template);
        when(tagSummaryPageRenderer.getRenderedContent(tagSummaryPageCollection, template)).thenReturn("TAG_PAGE_CONTENT");
        String renderedTagSummaryPage = templateEngine.getRenderedTagSummaryPageContent(tagSummaryPageCollection);
        assertThat(renderedTagSummaryPage, is("<html>\n <head></head>\n <body>\n  TAG_PAGE_CONTENT\n </body>\n</html>"));
    }
}
