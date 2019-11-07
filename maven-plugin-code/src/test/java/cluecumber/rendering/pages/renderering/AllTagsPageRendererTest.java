package cluecumber.rendering.pages.renderering;

import com.trivago.cluecumberCore.constants.ChartConfiguration;
import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.json.pojo.Report;
import com.trivago.cluecumberCore.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllTagsPageRenderer;
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
        ChartConfiguration chartConfiguration = mock(ChartConfiguration.class);
        allTagsPageRenderer = new AllTagsPageRenderer(chartJsonConverter, chartConfiguration);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Report report = new Report();
        List<Report> reports = new ArrayList<>();
        reports.add(report);
        AllTagsPageCollection allTagsPageCollection = new AllTagsPageCollection(reports, "");
        allTagsPageRenderer.getRenderedContent(allTagsPageCollection, template);
    }
}
