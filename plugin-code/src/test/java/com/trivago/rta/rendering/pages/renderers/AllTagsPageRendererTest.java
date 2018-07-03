package com.trivago.rta.rendering.pages.renderers;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.rendering.charts.ChartJsonConverter;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AllTagsPageRendererTest {

    private AllTagsPageRenderer allTagsPageRenderer;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        allTagsPageRenderer = new AllTagsPageRenderer(chartJsonConverter);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Report report = new Report();
        List<Report> reports = new ArrayList<>();
        reports.add(report);
        AllTagsPageCollection allTagsPageCollection = new AllTagsPageCollection(reports);
        allTagsPageRenderer.getRenderedContent(allTagsPageCollection, template);
    }
}
