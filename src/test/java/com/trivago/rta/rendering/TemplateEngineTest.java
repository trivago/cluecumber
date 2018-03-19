package com.trivago.rta.rendering;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.rendering.pages.ScenarioDetailPageRenderer;
import com.trivago.rta.rendering.pages.StartPageRenderer;
import com.trivago.rta.rendering.pages.TagSummaryPageRenderer;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
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

    private TemplateEngine templateEngine;

    @Before
    public void setup() {
        startPageRenderer = mock(StartPageRenderer.class);
        scenarioDetailPageRenderer = mock(ScenarioDetailPageRenderer.class);
        templateConfiguration = mock(TemplateConfiguration.class);
        TagSummaryPageRenderer tagSummaryPageRenderer = mock(TagSummaryPageRenderer.class);
        templateEngine = new TemplateEngine(
                templateConfiguration, startPageRenderer, scenarioDetailPageRenderer, tagSummaryPageRenderer
        );
    }

    @Test
    public void getRenderedStartPageTest() throws CluecumberPluginException {
        StartPageCollection startPageCollection = new StartPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate("index.html")).thenReturn(template);
        when(startPageRenderer.getRenderedContent(startPageCollection, template)).thenReturn("START_PAGE_CONTENT");
        String renderedStartPage = templateEngine.getRenderedStartPageContent(startPageCollection);
        assertThat(renderedStartPage, is("START_PAGE_CONTENT"));
    }

    @Test
    public void getRenderedDetailPageTest() throws CluecumberPluginException {
        DetailPageCollection detailPageCollection = new DetailPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate("pages/scenario-detail/scenario-detail.html")).thenReturn(template);
        when(scenarioDetailPageRenderer.getRenderedContent(detailPageCollection, template)).thenReturn("DETAIL_PAGE_CONTENT");
        String renderedDetailPage = templateEngine.getRenderedDetailPageContent(detailPageCollection);
        assertThat(renderedDetailPage, is("DETAIL_PAGE_CONTENT"));
    }
}
