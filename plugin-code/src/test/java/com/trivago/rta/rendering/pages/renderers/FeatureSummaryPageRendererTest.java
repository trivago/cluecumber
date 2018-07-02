package com.trivago.rta.rendering.pages.renderers;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.rendering.charts.ChartJsonConverter;
import com.trivago.rta.rendering.pages.pojos.pagecollections.FeatureSummaryPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class FeatureSummaryPageRendererTest {

    private FeatureSummaryPageRenderer featureSummaryPageRenderer;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        featureSummaryPageRenderer = new FeatureSummaryPageRenderer(chartJsonConverter);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Report report = new Report();
        List<Report> reports = new ArrayList<>();
        reports.add(report);
        FeatureSummaryPageCollection featureSummaryPageCollection = new FeatureSummaryPageCollection(reports);
        featureSummaryPageRenderer.getRenderedContent(featureSummaryPageCollection, template);
    }
}
