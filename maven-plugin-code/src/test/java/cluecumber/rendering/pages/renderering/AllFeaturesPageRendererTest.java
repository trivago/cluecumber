package cluecumber.rendering.pages.renderering;

import com.trivago.cluecumberCore.constants.ChartConfiguration;
import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.json.pojo.Report;
import com.trivago.cluecumberCore.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllFeaturesPageRenderer;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AllFeaturesPageRendererTest {

    private AllFeaturesPageRenderer allFeaturesPageRenderer;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        ChartConfiguration chartConfiguration = mock(ChartConfiguration.class);
        allFeaturesPageRenderer = new AllFeaturesPageRenderer(chartJsonConverter, chartConfiguration);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Report report = new Report();
        List<Report> reports = new ArrayList<>();
        reports.add(report);
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(reports, "");
        allFeaturesPageRenderer.getRenderedContent(allFeaturesPageCollection, template);
    }
}
