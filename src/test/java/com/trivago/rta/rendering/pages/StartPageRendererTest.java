package com.trivago.rta.rendering.pages;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class StartPageRendererTest {

    private StartPageRenderer startPageRenderer;

    @Before
    public void setup() {
        PropertyManager propertyManager = mock(PropertyManager.class);
        startPageRenderer = new StartPageRenderer(propertyManager);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        StartPageCollection startPageCollection = new StartPageCollection();
        startPageRenderer.getRenderedContent(startPageCollection, template);
    }
}
