package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.PageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class PageRendererTest {

    private PageWithChartRenderer pageWithChartRenderer;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        PropertyManager propertyManager = mock(PropertyManager.class);
        pageWithChartRenderer = new PageWithChartRenderer(chartJsonConverter);
    }

    @Test
    public void processedContentTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        PageCollection pageCollection = mock(PageCollection.class);
        String processedContent = pageWithChartRenderer.processedContent(template, pageCollection);
        assertThat(processedContent, is(""));
    }

    @Test(expected = CluecumberPluginException.class)
    public void processedContentTemplateExceptionTest() throws Exception {
        Template template = mock(Template.class);
        doThrow(new TemplateException("Test", null)).when(template).process(any(PageCollection.class), any(Writer.class));
        PageCollection pageCollection = mock(PageCollection.class);
        String processedContent = pageWithChartRenderer.processedContent(template, pageCollection);
        assertThat(processedContent, is(""));
    }

    @Test(expected = CluecumberPluginException.class)
    public void processedContentIoExceptionTest() throws Exception {
        Template template = mock(Template.class);
        doThrow(new IOException("Test", null)).when(template).process(any(PageCollection.class), any(Writer.class));
        PageCollection pageCollection = mock(PageCollection.class);
        String processedContent = pageWithChartRenderer.processedContent(template, pageCollection);
        assertThat(processedContent, is(""));
    }
}
