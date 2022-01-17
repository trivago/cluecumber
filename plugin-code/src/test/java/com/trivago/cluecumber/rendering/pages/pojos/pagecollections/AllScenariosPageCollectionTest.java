package com.trivago.cluecumber.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Result;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.rendering.pages.pojos.CustomParameter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllScenariosPageCollectionTest {

    private AllScenariosPageCollection allScenariosPageCollection;

    @Before
    public void setup() {
        allScenariosPageCollection = new AllScenariosPageCollection("");
    }

    @Test
    public void addReportsNullReportListTest() {
        allScenariosPageCollection.addReports(null);
        assertThat(allScenariosPageCollection.getReports().size(), is(0));
        assertThat(allScenariosPageCollection.getTotalNumberOfScenarios(), is(0));
    }

    @Test
    public void addReportsValidReportListTest() {
        Report[] reportList = new Report[2];
        reportList[0] = new Report();
        reportList[1] = new Report();
        allScenariosPageCollection.addReports(reportList);
        assertThat(allScenariosPageCollection.getReports().size(), is(2));
        assertThat(allScenariosPageCollection.getTotalNumberOfScenarios(), is(0));
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
        assertThat(allScenariosPageCollection.getReports().size(), is(1));
        assertThat(allScenariosPageCollection.getTotalNumberOfScenarios(), is(1));
    }

    @Test
    public void getTotalDurationDefaultValueTest() {
        long totalDuration = allScenariosPageCollection.getTotalDuration();
        assertThat(totalDuration, is(0L));
    }

    @Test
    public void getTotalDurationTest() {
        Report report = mock(Report.class);
        when(report.getTotalDuration()).thenReturn(5000000000L);
        Report[] reports = new Report[]{report, report};

        allScenariosPageCollection.addReports(reports);
        long totalDuration = allScenariosPageCollection.getTotalDuration();
        assertThat(totalDuration, is(10000000000L));
    }

    @Test
    public void getTotalDurationStringTest() {
        Report report = mock(Report.class);
        when(report.getTotalDuration()).thenReturn(5000000000L);
        Report[] reports = new Report[]{report};
        allScenariosPageCollection.addReports(reports);
        assertThat(allScenariosPageCollection.getTotalDurationString(), is("0m 05s 000ms"));
    }

    @Test
    public void hasFailedScenariosTest() {
        assertThat(allScenariosPageCollection.hasFailedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasPassedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasSkippedScenarios(), is(false));

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

        assertThat(allScenariosPageCollection.hasFailedScenarios(), is(true));
        assertThat(allScenariosPageCollection.hasPassedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasSkippedScenarios(), is(false));
    }

    @Test
    public void hasPassedScenariosTest() {
        assertThat(allScenariosPageCollection.hasFailedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasPassedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasSkippedScenarios(), is(false));

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

        assertThat(allScenariosPageCollection.hasFailedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasPassedScenarios(), is(true));
        assertThat(allScenariosPageCollection.hasSkippedScenarios(), is(false));
    }

    @Test
    public void hasSkippedScenariosTest() {
        assertThat(allScenariosPageCollection.hasFailedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasPassedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasSkippedScenarios(), is(false));

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

        assertThat(allScenariosPageCollection.hasFailedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasPassedScenarios(), is(false));
        assertThat(allScenariosPageCollection.hasSkippedScenarios(), is(true));
    }

    @Test
    public void hasCustomParametersTest() {
        assertThat(allScenariosPageCollection.hasCustomParameters(), is(false));
        List<CustomParameter> customParameters = new ArrayList<>();
        CustomParameter parameter = new CustomParameter("key", "value");
        customParameters.add(parameter);
        allScenariosPageCollection.setCustomParameters(customParameters);
        assertThat(allScenariosPageCollection.hasCustomParameters(), is(true));
    }
}
