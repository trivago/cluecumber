package com.trivago.rta.rendering.pages;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ScenarioDetailPageRendererTest {

    private ScenarioDetailPageRenderer scenarioDetailPageRenderer;

    @Before
    public void setup(){
        scenarioDetailPageRenderer = new ScenarioDetailPageRenderer();
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        element.setSteps(steps);
        DetailPageCollection detailPageCollection = new DetailPageCollection(element);
        scenarioDetailPageRenderer.getRenderedContent(detailPageCollection, template);
    }
}
