package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void getRenderedCustomCssContentTest() throws IOException, CluecumberException {
        when(propertyManager.getCustomStatusColorPassed()).thenReturn("passed");
        when(propertyManager.getCustomStatusColorFailed()).thenReturn("failed");
        when(propertyManager.getCustomStatusColorSkipped()).thenReturn("skipped");
        Template template = new Template("test", "${passedColor} - ${failedColor} - ${skippedColor}", new Configuration(new Version("2.3.0")));
        String renderedCustomCssContent = customCssRenderer.getRenderedCustomCssContent(template);
        System.out.println(renderedCustomCssContent);
        assertEquals(renderedCustomCssContent, "passed - failed - skipped");
    }
}
