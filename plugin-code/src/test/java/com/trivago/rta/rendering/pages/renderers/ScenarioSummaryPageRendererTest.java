package com.trivago.rta.rendering.pages.renderers;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Tag;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioSummaryPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class ScenarioSummaryPageRendererTest {

    private ScenarioSummaryPageRenderer scenarioSummaryPageRenderer;

    @Before
    public void setup() {
        PropertyManager propertyManager = mock(PropertyManager.class);
        scenarioSummaryPageRenderer = new ScenarioSummaryPageRenderer(propertyManager);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        ScenarioSummaryPageCollection scenarioSummaryPageCollection = new ScenarioSummaryPageCollection();
        scenarioSummaryPageRenderer.getRenderedContent(scenarioSummaryPageCollection, template);
    }

    @Test
    public void getRenderedContentByTagFilterTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        ScenarioSummaryPageCollection scenarioSummaryPageCollection = new ScenarioSummaryPageCollection();
        Tag tag = new Tag();
        scenarioSummaryPageRenderer.getRenderedContentByTagFilter(scenarioSummaryPageCollection, template, tag);
    }

    @Test
    public void getRenderedContentByFeatureFilterTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        ScenarioSummaryPageCollection scenarioSummaryPageCollection = new ScenarioSummaryPageCollection();
        Feature feature = new Feature("feature", 0);
        scenarioSummaryPageRenderer.getRenderedContentByFeatureFilter(scenarioSummaryPageCollection, template, feature);
    }
}
