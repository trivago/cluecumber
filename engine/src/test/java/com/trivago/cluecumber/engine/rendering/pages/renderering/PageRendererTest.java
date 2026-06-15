package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.PageCollection;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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
        PebbleTemplate template = mock(PebbleTemplate.class);
        PageCollection pageCollection = mock(PageCollection.class);
        String processedContent = pageWithChartRenderer.processedContent(template, pageCollection, null);
        assertEquals(processedContent, "");
    }

    @Test
    public void processedContentTemplateExceptionTest() throws Exception {
        PebbleTemplate template = mock(PebbleTemplate.class);
        doThrow(new IOException("Test")).when(template).evaluate(any(StringWriter.class), any(Map.class));
        PageCollection pageCollection = mock(PageCollection.class);
        assertThrows(CluecumberException.class, () -> pageWithChartRenderer.processedContent(template, pageCollection, null));
    }

    @Test
    public void processedContentIoExceptionTest() throws Exception {
        PebbleTemplate template = mock(PebbleTemplate.class);
        doThrow(new IOException("Test")).when(template).evaluate(any(StringWriter.class), any(Map.class));
        PageCollection pageCollection = mock(PageCollection.class);
        assertThrows(CluecumberException.class, () -> pageWithChartRenderer.processedContent(template, pageCollection, null));
    }
}
