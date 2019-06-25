package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScenarioDetailsPageRendererTest {

    private ScenarioDetailsPageRenderer scenarioDetailsPageRenderer;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        PropertyManager propertyManager = mock(PropertyManager.class);
        when(propertyManager.getCustomStatusColorFailed()).thenReturn("#ff0000");
        when(propertyManager.getCustomStatusColorPassed()).thenReturn("#00ff00");
        when(propertyManager.getCustomStatusColorSkipped()).thenReturn("#00ffff");
        ChartConfiguration chartConfiguration = new ChartConfiguration(propertyManager);
        scenarioDetailsPageRenderer = new ScenarioDetailsPageRenderer(chartJsonConverter, chartConfiguration, propertyManager);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        element.setSteps(steps);
        ScenarioDetailsPageCollection scenarioDetailsPageCollection = new ScenarioDetailsPageCollection(element, "");
        scenarioDetailsPageRenderer.getRenderedContent(scenarioDetailsPageCollection, template);
    }
}
