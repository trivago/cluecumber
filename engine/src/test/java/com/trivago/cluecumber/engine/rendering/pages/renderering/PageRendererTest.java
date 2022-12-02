package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.PageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class PageRendererTest {

    private PageWithChartRenderer pageWithChartRenderer;

    @BeforeEach
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        pageWithChartRenderer = new PageWithChartRenderer(chartJsonConverter);
    }

    @Test
    public void processedContentTest() throws CluecumberException {
        Template template = mock(Template.class);
        PageCollection pageCollection = mock(PageCollection.class);
        String processedContent = pageWithChartRenderer.processedContent(template, pageCollection, null);
        assertEquals(processedContent, "");
    }

    @Test
    public void processedContentTemplateExceptionTest() throws Exception {
        Template template = mock(Template.class);
        doThrow(new TemplateException("Test", null)).when(template).process(any(PageCollection.class), any(Writer.class));
        PageCollection pageCollection = mock(PageCollection.class);
        assertThrows(CluecumberException.class, () -> pageWithChartRenderer.processedContent(template, pageCollection, null));
    }

    @Test
    public void processedContentIoExceptionTest() throws Exception {
        Template template = mock(Template.class);
        doThrow(new IOException("Test", null)).when(template).process(any(PageCollection.class), any(Writer.class));
        PageCollection pageCollection = mock(PageCollection.class);
        assertThrows(CluecumberException.class, () -> pageWithChartRenderer.processedContent(template, pageCollection, null));
    }
}
