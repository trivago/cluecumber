package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllStepsPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AllStepsPageRendererTest {

    private AllStepsPageRenderer allStepsPageRenderer;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        ChartConfiguration chartConfiguration = mock(ChartConfiguration.class);
        PropertyManager propertyManager = mock(PropertyManager.class);
        allStepsPageRenderer = new AllStepsPageRenderer(chartJsonConverter, chartConfiguration, propertyManager);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Report report = new Report();
        List<Report> reports = new ArrayList<>();
        reports.add(report);
        AllStepsPageCollection allStepsPageCollection = new AllStepsPageCollection(reports, "");
        allStepsPageRenderer.getRenderedContent(allStepsPageCollection, template);
    }
}
