package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.StartPageCollection;
import freemarker.template.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class StartPageRendererTest {
    private StartPageRenderer startPageRenderer;

    @BeforeEach
    public void setup() {
        startPageRenderer = new StartPageRenderer();
    }

    @Test
    public void startPageRendererTest() throws CluecumberException {
        Template template = mock(Template.class);
        StartPageCollection startPageCollection = mock(StartPageCollection.class);
        String processedContent = startPageRenderer.processedContent(template, startPageCollection, null);
        assertEquals(processedContent, "");
    }
}
