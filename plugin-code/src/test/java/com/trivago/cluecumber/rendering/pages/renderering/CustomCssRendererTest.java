package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.properties.PropertyManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomCssRendererTest {
    private CustomCssRenderer customCssRenderer;
    private PropertyManager propertyManager;

    @Before
    public void setup() {
        propertyManager = mock(PropertyManager.class);
        customCssRenderer = new CustomCssRenderer(propertyManager);
    }

    @Test
    public void getRenderedCustomCssContentTest() throws CluecumberPluginException, IOException {
        when(propertyManager.getCustomStatusColorPassed()).thenReturn("passed");
        when(propertyManager.getCustomStatusColorFailed()).thenReturn("failed");
        when(propertyManager.getCustomStatusColorSkipped()).thenReturn("skipped");
        Template template = new Template("test", "${passedColor} - ${failedColor} - ${skippedColor}", new Configuration());
        String renderedCustomCssContent = customCssRenderer.getRenderedCustomCssContent(template);
        System.out.println(renderedCustomCssContent);
        assertThat(renderedCustomCssContent, is("passed - failed - skipped"));
    }
}
