package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.filesystem.FileSystemManager;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.logging.CluecumberLogger;
import com.trivago.cluecumber.properties.PropertiesFileLoader;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.renderering.CustomCssRenderer;
import com.trivago.cluecumber.rendering.pages.renderering.StartPageRenderer;
import com.trivago.cluecumber.rendering.pages.templates.TemplateEngine;
import com.trivago.cluecumber.rendering.pages.visitors.FeatureVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.PageVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.ScenarioVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.StepVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.TagVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.VisitorDirectory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportGeneratorTest {

    private FileSystemManager fileSystemManager;
    private ReportGenerator reportGenerator;

    @Before
    public void setup() {
        fileSystemManager = mock(FileSystemManager.class);
        CluecumberLogger logger = mock(CluecumberLogger.class);
        FileIO fileIO = mock(FileIO.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);
        PropertiesFileLoader propertiesFileLoader = mock(PropertiesFileLoader.class);
        PropertyManager propertyManager = new PropertyManager(logger, fileIO, propertiesFileLoader);
        propertyManager.setStartPage("ALL_SCENARIOS");

        CustomCssRenderer customCssRenderer = mock(CustomCssRenderer.class);
        StartPageRenderer startPageRenderer = mock(StartPageRenderer.class);

        ScenarioVisitor scenarioVisitor = mock(ScenarioVisitor.class);
        FeatureVisitor featureVisitor = mock(FeatureVisitor.class);
        TagVisitor tagVisitor = mock(TagVisitor.class);
        StepVisitor stepVisitor = mock(StepVisitor.class);

        VisitorDirectory visitorDirectory = mock(VisitorDirectory.class);
        List<PageVisitor> visitors = new ArrayList<>();
        visitors.add(scenarioVisitor);
        visitors.add(featureVisitor);
        visitors.add(tagVisitor);
        visitors.add(stepVisitor);
        when(visitorDirectory.getVisitors()).thenReturn(visitors);

        reportGenerator = new ReportGenerator(
                fileIO,
                templateEngine,
                propertyManager,
                fileSystemManager,
                startPageRenderer,
                customCssRenderer,
                visitorDirectory);
    }

    @Test
    public void fileOperationsTest() throws Exception {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");

        Report report1 = new Report();
        List<Element> elements1 = new ArrayList<>();
        Element element1 = new Element();
        elements1.add(element1);
        report1.setElements(elements1);

        Report report2 = new Report();
        List<Element> elements2 = new ArrayList<>();
        Element element2 = new Element();
        elements2.add(element2);
        report2.setElements(elements2);

        Report[] reportList = {report1, report2};
        allScenariosPageCollection.addReports(reportList);

        reportGenerator.generateReport(allScenariosPageCollection);

        verify(fileSystemManager, times(8)).createDirectory(anyString());
        verify(fileSystemManager, times(17)).copyResourceFromJar(anyString(), anyString());
    }
}
