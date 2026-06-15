package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomCssRendererTest {
    private CustomCssRenderer customCssRenderer;
    private PropertyManager propertyManager;

    @BeforeEach
    public void setup() {
        propertyManager = mock(PropertyManager.class);
        customCssRenderer = new CustomCssRenderer(propertyManager);
    }

    @Test
    public void getRenderedCustomCssContentTest() throws CluecumberException, java.io.IOException {
        when(propertyManager.getCustomStatusColorPassed()).thenReturn("passed");
        when(propertyManager.getCustomStatusColorFailed()).thenReturn("failed");
        when(propertyManager.getCustomStatusColorSkipped()).thenReturn("skipped");

        PebbleTemplate template = mock(PebbleTemplate.class);
        doAnswer(invocation -> {
            StringWriter writer = invocation.getArgument(0);
            Map<String, Object> context = invocation.getArgument(1);
            writer.write(context.get("passedColor") + " - " + context.get("failedColor") + " - " + context.get("skippedColor"));
            return null;
        }).when(template).evaluate(any(StringWriter.class), any(Map.class));

        String renderedCustomCssContent = customCssRenderer.getRenderedCustomCssContent(template);
        assertEquals("passed - failed - skipped", renderedCustomCssContent);
    }
}
