package com.trivago.rta.rendering;

import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.pages.pojos.DetailPageCollection;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import com.trivago.rta.rendering.pages.pojos.TagSummaryPageCollection;
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
                templateEngine, fileIO, propertyManager, fileSystemManager, logger
        );
    }

    @Test
    public void fileOperationsTest() throws Exception {
        StartPageCollection startPageCollection = new StartPageCollection();

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
        startPageCollection.addReports(reportList);

        when(templateEngine.getRenderedStartPageContent(startPageCollection)).thenReturn("RENDERED_START_PAGE_CONTENT");
        when(templateEngine.getRenderedDetailPageContent(any(DetailPageCollection.class))).thenReturn("RENDERED_DETAIL_PAGE_CONTENT");
        when(templateEngine.getRenderedTagSummaryPageContent(any(TagSummaryPageCollection.class))).thenReturn("RENDERED_TAG_PAGE_CONTENT");

        reportGenerator.generateReport(startPageCollection);

        verify(fileSystemManager, times(4)).createDirectory(anyString());
        verify(fileSystemManager, times(10)).exportResource(any(Class.class), anyString(), anyString());
        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_START_PAGE_CONTENT"), anyString());
        verify(fileIO, times(2)).writeContentToFile(eq("RENDERED_DETAIL_PAGE_CONTENT"), anyString());
        verify(fileIO, times(1)).writeContentToFile(eq("RENDERED_TAG_PAGE_CONTENT"), anyString());
    }
}
