package com.trivago.rta.rendering.pages.renderers;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Tag;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.charts.ChartJsonConverter;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AllScenariosPageRendererTest {

    private AllScenariosPageRenderer allScenariosPageRenderer;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        PropertyManager propertyManager = mock(PropertyManager.class);
        allScenariosPageRenderer = new AllScenariosPageRenderer(chartJsonConverter, propertyManager);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        allScenariosPageRenderer.getRenderedContent(allScenariosPageCollection, template);
    }

    @Test
    public void getRenderedContentByTagFilterTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        Tag tag = new Tag();
        allScenariosPageRenderer.getRenderedContentByTagFilter(allScenariosPageCollection, template, tag);
    }

    @Test
    public void getRenderedContentByFeatureFilterTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();
        Feature feature = new Feature("feature", 0);
        allScenariosPageRenderer.getRenderedContentByFeatureFilter(allScenariosPageCollection, template, feature);
    }
}
