package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.StartPageCollection;
import io.pebbletemplates.pebble.template.PebbleTemplate;
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
        PebbleTemplate template = mock(PebbleTemplate.class);
        StartPageCollection startPageCollection = mock(StartPageCollection.class);
        String processedContent = startPageRenderer.processedContent(template, startPageCollection, null);
        assertEquals(processedContent, "");
    }
}
