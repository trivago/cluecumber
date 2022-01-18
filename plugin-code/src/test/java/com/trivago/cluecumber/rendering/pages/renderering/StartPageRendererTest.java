package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.StartPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class StartPageRendererTest {
    private StartPageRenderer startPageRenderer;

    @Before
    public void setup() {
        startPageRenderer = new StartPageRenderer();
    }

    @Test
    public void startPageRendererTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        StartPageCollection startPageCollection = mock(StartPageCollection.class);
        String processedContent = startPageRenderer.processedContent(template, startPageCollection);
        assertThat(processedContent, is(""));
    }
}
