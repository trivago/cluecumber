package cluecumber.rendering.pages.renderering;

import com.trivago.cluecumberCore.constants.ChartConfiguration;
import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.json.pojo.*;
import com.trivago.cluecumberCore.properties.PropertyManager;
import com.trivago.cluecumberCore.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumberCore.rendering.pages.pojos.Feature;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllScenariosPageRenderer;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllScenariosPageRendererTest {

    private AllScenariosPageRenderer allScenariosPageRenderer;
    private PropertyManager propertyManager;

    @Before
    public void setup() {
        ChartJsonConverter chartJsonConverter = mock(ChartJsonConverter.class);
        propertyManager = mock(PropertyManager.class);
        ChartConfiguration chartConfiguration = new ChartConfiguration(propertyManager);
        allScenariosPageRenderer = new AllScenariosPageRenderer(chartJsonConverter, chartConfiguration, propertyManager);
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");
        allScenariosPageRenderer.getRenderedContent(allScenariosPageCollection, template);
    }

    @Test
    public void getRenderedContentParametersTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("key", "value");
        when(propertyManager.getCustomParameters()).thenReturn(customParameters);
        allScenariosPageRenderer.getRenderedContent(allScenariosPageCollection, template);
    }

    @Test
    public void getRenderedContentByTagFilterTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");
        Tag tag = new Tag();
        tag.setName("test");
        Report report = new Report();
        report.setFeatureIndex(12);
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        element.setTags(tags);
        elements.add(element);
        report.setElements(elements);
        Report[] reportList = new Report[]{report};
        allScenariosPageCollection.addReports(reportList);
        allScenariosPageRenderer.getRenderedContentByTagFilter(allScenariosPageCollection, template, tag);
    }

    @Test
    public void getRenderedContentByStepFilterTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");

        Step step = new Step();
        step.setName("test");
        Match match = new Match();
        match.setLocation("myLocation");
        step.setMatch(match);

        Report report = new Report();
        report.setFeatureIndex(12);
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        report.setElements(elements);
        Report[] reportList = new Report[]{report};
        allScenariosPageCollection.addReports(reportList);
        allScenariosPageRenderer.getRenderedContentByStepFilter(allScenariosPageCollection, template, step);
    }

    @Test
    public void getRenderedContentByFeatureFilterTest() throws CluecumberPluginException {
        Template template = mock(Template.class);
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");
        Report report = new Report();
        report.setFeatureIndex(12);
        Report[] reportList = new Report[]{report};
        allScenariosPageCollection.addReports(reportList);
        Feature feature = new Feature("feature", 12);
        allScenariosPageRenderer.getRenderedContentByFeatureFilter(allScenariosPageCollection, template, feature);
    }
}
