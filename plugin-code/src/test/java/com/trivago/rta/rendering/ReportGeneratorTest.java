package com.trivago.rta.rendering;

import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.AllTagsPageCollection;
import com.trivago.rta.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportGeneratorTest {

    private TemplateEngine templateEngine;
    private FileIO fileIO;
    private FileSystemManager fileSystemManager;

    private ReportGenerator reportGenerator;

    @Before
    public void setup() {
        templateEngine = mock(TemplateEngine.class);
        fileSystemManager = mock(FileSystemManager.class);
        fileIO = mock(FileIO.class);
        CluecumberLogger logger = mock(CluecumberLogger.class);
        PropertyManager propertyManager = new PropertyManager(logger);
        reportGenerator = new ReportGenerator(
                templateEngine, fileIO, propertyManager, fileSystemManager
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

        when(templateEngine.getRenderedScenarioSummaryPageContent(allScenariosPageCollection)).thenReturn("RENDERED_START_PAGE_CONTENT");
        when(templateEngine.getRenderedScenarioDetailPageContent(any(ScenarioDetailsPageCollection.class))).thenReturn("RENDERED_DETAIL_PAGE_CONTENT");
        when(templateEngine.getRenderedTagSummaryPageContent(any(AllTagsPageCollection.class))).thenReturn("RENDERED_TAG_PAGE_CONTENT");
        when(templateEngine.getRenderedFeatureSummaryPageContent(any(AllFeaturesPageCollection.class))).thenReturn("RENDERED_FEATURE_PAGE_CONTENT");

        reportGenerator.generateReport(allScenariosPageCollection);

        verify(fileSystemManager, times(7)).createDirectory(anyString());
        verify(fileSystemManager, times(17)).copyResourceFromJar(anyString(), anyString());
        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_START_PAGE_CONTENT"), anyString());
        verify(fileIO, times(2)).writeContentToFile(eq("RENDERED_DETAIL_PAGE_CONTENT"), anyString());
        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_TAG_PAGE_CONTENT"), anyString());
        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_FEATURE_PAGE_CONTENT"), anyString());
    }
}
