package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.filesystem.FileSystemManager;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.logging.CluecumberLogger;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.visitors.FeatureVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.ScenarioVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.StepVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.TagVisitor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReportGeneratorTest {

    private FileSystemManager fileSystemManager;
    private ReportGenerator reportGenerator;

    @Before
    public void setup() {
        fileSystemManager = mock(FileSystemManager.class);
        CluecumberLogger logger = mock(CluecumberLogger.class);
        PropertyManager propertyManager = new PropertyManager(logger);
        ScenarioVisitor scenarioVisitor = mock(ScenarioVisitor.class);
        FeatureVisitor featureVisitor = mock(FeatureVisitor.class);
        TagVisitor tagVisitor = mock(TagVisitor.class);
        StepVisitor stepVisitor = mock(StepVisitor.class);
        reportGenerator = new ReportGenerator(
                propertyManager,
                fileSystemManager,
                scenarioVisitor,
                featureVisitor,
                tagVisitor,
                stepVisitor
        );
    }

    @Test
    public void fileOperationsTest() throws Exception {
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection();

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

//        when(templateEngine.getRenderedScenarioSummaryPageContent(allScenariosPageCollection)).thenReturn("RENDERED_START_PAGE_CONTENT");
//        when(templateEngine.getRenderedScenarioDetailPageContent(any(ScenarioDetailsPageCollection.class))).thenReturn("RENDERED_DETAIL_PAGE_CONTENT");
//        when(templateEngine.getRenderedTagSummaryPageContent(any(AllTagsPageCollection.class))).thenReturn("RENDERED_TAG_PAGE_CONTENT");
//        when(templateEngine.getRenderedFeatureSummaryPageContent(any(AllFeaturesPageCollection.class))).thenReturn("RENDERED_FEATURE_PAGE_CONTENT");
//        when(templateEngine.getRenderedStepSummaryPageContent(any(AllStepsPageCollection.class))).thenReturn("RENDERED_STEPS_PAGE_CONTENT");

        reportGenerator.generateReport(allScenariosPageCollection);

        verify(fileSystemManager, times(8)).createDirectory(anyString());
        verify(fileSystemManager, times(17)).copyResourceFromJar(anyString(), anyString());
//        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_START_PAGE_CONTENT"), anyString());
//        verify(fileIO, times(2)).writeContentToFile(eq("RENDERED_DETAIL_PAGE_CONTENT"), anyString());
//        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_TAG_PAGE_CONTENT"), anyString());
//        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_STEPS_PAGE_CONTENT"), anyString());
//        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_FEATURE_PAGE_CONTENT"), anyString());
    }
}
