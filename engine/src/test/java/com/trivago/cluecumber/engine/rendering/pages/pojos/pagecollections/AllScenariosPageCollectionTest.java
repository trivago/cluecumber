package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Result;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.rendering.pages.pojos.CustomParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllScenariosPageCollectionTest {

    private AllScenariosPageCollection allScenariosPageCollection;

    @BeforeEach
    public void setup() {
        allScenariosPageCollection = new AllScenariosPageCollection("");
    }

    @Test
    public void addReportsNullReportListTest() {
        allScenariosPageCollection.addReports(null);
        assertEquals(allScenariosPageCollection.getReports().size(), 0);
        assertEquals(allScenariosPageCollection.getTotalNumberOfScenarios(), 0);
    }

    @Test
    public void addReportsValidReportListTest() {
        Report[] reportList = new Report[2];
        reportList[0] = new Report();
        reportList[1] = new Report();
        allScenariosPageCollection.addReports(reportList);
        assertEquals(allScenariosPageCollection.getReports().size(), 2);
        assertEquals(allScenariosPageCollection.getTotalNumberOfScenarios(), 0);
    }

    @Test
    public void addReportsValidReportListWithScenariosTest() {
        Report[] reportList = new Report[1];

        Report report1 = new Report();
        List<Element> elements = new ArrayList<>();
        Element element1 = new Element();
        element1.setType("scenario");
        elements.add(element1);
        report1.setElements(elements);
        reportList[0] = report1;

        allScenariosPageCollection.addReports(reportList);
        assertEquals(allScenariosPageCollection.getReports().size(), 1);
        assertEquals(allScenariosPageCollection.getTotalNumberOfScenarios(), 1);
    }

    @Test
    public void getTotalDurationDefaultValueTest() {
        long totalDuration = allScenariosPageCollection.getTotalDuration();
        assertEquals(totalDuration, 0L);
    }

    @Test
    public void getTotalDurationTest() {
        Report report = mock(Report.class);
        when(report.getTotalDuration()).thenReturn(5000000000L);
        Report[] reports = new Report[]{report, report};

        allScenariosPageCollection.addReports(reports);
        long totalDuration = allScenariosPageCollection.getTotalDuration();
        assertEquals(totalDuration, 10000000000L);
    }

    @Test
    public void getTotalDurationStringTest() {
        Report report = mock(Report.class);
        when(report.getTotalDuration()).thenReturn(5000000000L);
        Report[] reports = new Report[]{report};
        allScenariosPageCollection.addReports(reports);
        assertEquals(allScenariosPageCollection.getTotalDurationString(), "0m 05s 000ms");
    }

    @Test
    public void hasFailedScenariosTest() {
        assertFalse(allScenariosPageCollection.hasFailedScenarios());
        assertFalse(allScenariosPageCollection.hasPassedScenarios());
        assertFalse(allScenariosPageCollection.hasSkippedScenarios());

        Report[] reportList = new Report[1];
        Report report = new Report();
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("failed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);
        reportList[0] = report;
        allScenariosPageCollection.addReports(reportList);

        assertTrue(allScenariosPageCollection.hasFailedScenarios());
        assertFalse(allScenariosPageCollection.hasPassedScenarios());
        assertFalse(allScenariosPageCollection.hasSkippedScenarios());
    }

    @Test
    public void hasPassedScenariosTest() {
        assertFalse(allScenariosPageCollection.hasFailedScenarios());
        assertFalse(allScenariosPageCollection.hasPassedScenarios());
        assertFalse(allScenariosPageCollection.hasSkippedScenarios());

        Report[] reportList = new Report[1];
        Report report = new Report();
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);
        reportList[0] = report;
        allScenariosPageCollection.addReports(reportList);

        assertFalse(allScenariosPageCollection.hasFailedScenarios());
        assertTrue(allScenariosPageCollection.hasPassedScenarios());
        assertFalse(allScenariosPageCollection.hasSkippedScenarios());
    }

    @Test
    public void hasSkippedScenariosTest() {
        assertFalse(allScenariosPageCollection.hasFailedScenarios());
        assertFalse(allScenariosPageCollection.hasPassedScenarios());
        assertFalse(allScenariosPageCollection.hasSkippedScenarios());

        Report[] reportList = new Report[1];
        Report report = new Report();
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("skipped");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);
        reportList[0] = report;
        allScenariosPageCollection.addReports(reportList);

        assertFalse(allScenariosPageCollection.hasFailedScenarios());
        assertFalse(allScenariosPageCollection.hasPassedScenarios());
        assertTrue(allScenariosPageCollection.hasSkippedScenarios());
    }

    @Test
    public void hasNotLastRunScenariosTest() {
        assertFalse(allScenariosPageCollection.hasNotLastRunScenarios());
        Report[] reportList = new Report[1];
        Report report = new Report();
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        element.isMultiRunChild(true);
        elements.add(element);
        report.setElements(elements);
        reportList[0] = report;
        allScenariosPageCollection.addReports(reportList);
        assertTrue(allScenariosPageCollection.hasNotLastRunScenarios());
    }

    @Test
    public void hasCustomParametersTest() {
        assertFalse(allScenariosPageCollection.hasCustomParameters());
        List<CustomParameter> customParameters = new ArrayList<>();
        CustomParameter parameter = new CustomParameter("key", "value");
        customParameters.add(parameter);
        allScenariosPageCollection.setCustomParameters(customParameters);
        assertTrue(allScenariosPageCollection.hasCustomParameters());
    }
}
