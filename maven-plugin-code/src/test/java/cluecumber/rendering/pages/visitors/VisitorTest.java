package cluecumber.rendering.pages.visitors;

import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.filesystem.FileIO;
import com.trivago.cluecumberCore.json.pojo.Element;
import com.trivago.cluecumberCore.json.pojo.Report;
import com.trivago.cluecumberCore.json.pojo.Step;
import com.trivago.cluecumberCore.json.pojo.Tag;
import com.trivago.cluecumberCore.properties.PropertyManager;
import com.trivago.cluecumberCore.rendering.pages.pojos.Feature;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllScenariosPageRenderer;
import com.trivago.cluecumberCore.rendering.pages.templates.TemplateEngine;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VisitorTest {
    FileIO fileIo;
    TemplateEngine templateEngine;
    PropertyManager propertyManager;
    AllScenariosPageRenderer allScenariosPageRenderer;

    @Before
    public void setUp() throws CluecumberPluginException {
        fileIo = mock(FileIO.class);
        templateEngine = mock(TemplateEngine.class);
        propertyManager = mock(PropertyManager.class);
        allScenariosPageRenderer = mock(AllScenariosPageRenderer.class);
        when(propertyManager.getGeneratedHtmlReportDirectory()).thenReturn("dummyPath");
    }

    AllScenariosPageCollection getAllScenarioPageCollection() {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");
        Feature feature = new Feature("MyFeature", 12);
        Report[] reportList = new Report[1];
        Report report = new Report();
        report.setName(feature.getName());
        report.setFeatureIndex(feature.getIndex());
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        element.setType("scenario");
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("myTag");
        tags.add(tag);
        element.setTags(tags);
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        step.setName("MyStep");
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);
        reportList[0] = report;
        allScenariosPageCollection.addReports(reportList);
        return allScenariosPageCollection;
    }
}
