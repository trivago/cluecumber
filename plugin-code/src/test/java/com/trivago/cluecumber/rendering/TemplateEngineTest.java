package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Tag;
import com.trivago.cluecumber.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import com.trivago.cluecumber.rendering.pages.renderers.AllFeaturesPageRenderer;
import com.trivago.cluecumber.rendering.pages.renderers.AllScenariosPageRenderer;
import com.trivago.cluecumber.rendering.pages.renderers.AllTagsPageRenderer;
import com.trivago.cluecumber.rendering.pages.renderers.ScenarioDetailsPageRenderer;
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
    public void getRenderedScenarioSummaryPageContentTest() throws CluecumberPluginException {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE)).thenReturn(template);
        when(allScenariosPageRenderer.getRenderedContent(allScenariosPageCollection, template)).thenReturn("SCENARIO_SUMMARY_CONTENT");
        String pageContent = templateEngine.getRenderedScenarioSummaryPageContent(allScenariosPageCollection);
        assertThat(pageContent, is("<html>\n <head></head>\n <body>\n  SCENARIO_SUMMARY_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedScenarioSequencePageContentTest() throws CluecumberPluginException {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_SEQUENCE_TEMPLATE)).thenReturn(template);
        when(allScenariosPageRenderer.getRenderedContent(allScenariosPageCollection, template)).thenReturn("SCENARIO_SEQUENCE_CONTENT");
        String pageContent = templateEngine.getRenderedScenarioSequencePageContent(allScenariosPageCollection);
        assertThat(pageContent, is("<html>\n <head></head>\n <body>\n  SCENARIO_SEQUENCE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedScenarioSummaryPageContentByTagFilterTest() throws CluecumberPluginException {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE)).thenReturn(template);
        Tag tag = new Tag();
        when(allScenariosPageRenderer.getRenderedContentByTagFilter(allScenariosPageCollection, template, tag)).thenReturn("SCENARIO_SUMMARY_TAG_TEMPLATE");
        String pageContent = templateEngine.getRenderedScenarioSummaryPageContentByTagFilter(allScenariosPageCollection, tag);
        assertThat(pageContent, is("<html>\n <head></head>\n <body>\n  SCENARIO_SUMMARY_TAG_TEMPLATE\n </body>\n</html>"));
    }

    @Test
    public void getRenderedScenarioSummaryPageContentByFeatureFilterTest() throws CluecumberPluginException {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_SUMMARY_TEMPLATE)).thenReturn(template);
        Feature feature = new Feature("test", 0);
        when(allScenariosPageRenderer.getRenderedContentByFeatureFilter(allScenariosPageCollection, template, feature)).thenReturn("SCENARIO_SUMMARY_FEATURE_TEMPLATE");
        String pageContent = templateEngine.getRenderedScenarioSummaryPageContentByFeatureFilter(allScenariosPageCollection, feature);
        assertThat(pageContent, is("<html>\n <head></head>\n <body>\n  SCENARIO_SUMMARY_FEATURE_TEMPLATE\n </body>\n</html>"));
    }

    @Test
    public void getRenderedDetailPageContentTest() throws CluecumberPluginException {
        ScenarioDetailsPageCollection scenarioDetailsPageCollection = new ScenarioDetailsPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.SCENARIO_DETAIL_TEMPLATE)).thenReturn(template);
        when(scenarioDetailsPageRenderer.getRenderedContent(scenarioDetailsPageCollection, template)).thenReturn("DETAIL_PAGE_CONTENT");
        String pageContent = templateEngine.getRenderedScenarioDetailPageContent(scenarioDetailsPageCollection);
        assertThat(pageContent, is("<html>\n <head></head>\n <body>\n  DETAIL_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedTagSummaryPageContentTest() throws CluecumberPluginException {
        AllTagsPageCollection allTagsPageCollection = new AllTagsPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.TAG_SUMMARY_TEMPLATE)).thenReturn(template);
        when(allTagsPageRenderer.getRenderedContent(allTagsPageCollection, template)).thenReturn("TAG_PAGE_CONTENT");
        String pageContent = templateEngine.getRenderedTagSummaryPageContent(allTagsPageCollection);
        assertThat(pageContent, is("<html>\n <head></head>\n <body>\n  TAG_PAGE_CONTENT\n </body>\n</html>"));
    }

    @Test
    public void getRenderedFeatureSummaryPageContentTest() throws CluecumberPluginException {
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(null);
        Template template = mock(Template.class);
        when(templateConfiguration.getTemplate(PluginSettings.FEATURE_SUMMARY_TEMPLATE)).thenReturn(template);
        when(allFeaturesPageRenderer.getRenderedContent(allFeaturesPageCollection, template)).thenReturn("FEATURE_PAGE_CONTENT");
        String pageContent = templateEngine.getRenderedFeatureSummaryPageContent(allFeaturesPageCollection);
        assertThat(pageContent, is("<html>\n <head></head>\n <body>\n  FEATURE_PAGE_CONTENT\n </body>\n</html>"));
    }
}
