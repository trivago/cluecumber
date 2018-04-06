package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Result;
import com.trivago.rta.json.pojo.Step;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartPageCollectionTest {

    private StartPageCollection startPageCollection;

    @Before
    public void setup() {
        startPageCollection = new StartPageCollection();
    }

    @Test
    public void addReportsNullReportListTest() {
        startPageCollection.addReports(null);
        assertThat(startPageCollection.getReports().size(), is(0));
        assertThat(startPageCollection.getTotalNumberOfFeatures(), is(0));
        assertThat(startPageCollection.getTotalNumberOfScenarios(), is(0));
    }

    @Test
    public void addReportsValidReportListTest() {
        Report[] reportList = new Report[2];
        reportList[0] = new Report();
        reportList[1] = new Report();
        startPageCollection.addReports(reportList);
        assertThat(startPageCollection.getReports().size(), is(2));
        assertThat(startPageCollection.getTotalNumberOfFeatures(), is(2));
        assertThat(startPageCollection.getTotalNumberOfScenarios(), is(0));
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

        startPageCollection.addReports(reportList);
        assertThat(startPageCollection.getReports().size(), is(1));
        assertThat(startPageCollection.getTotalNumberOfFeatures(), is(1));
        assertThat(startPageCollection.getTotalNumberOfScenarios(), is(1));
    }

    @Test
    public void getTotalDurationDefaultValueTest() {
        long totalDuration = startPageCollection.getTotalDuration();
        assertThat(totalDuration, is(0L));
    }

    @Test
    public void hasFailedScenariosTest() {
        assertThat(startPageCollection.hasFailedScenarios(), is(false));
        assertThat(startPageCollection.hasPassedScenarios(), is(false));
        assertThat(startPageCollection.hasSkippedScenarios(), is(false));

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
        startPageCollection.addReports(reportList);

        assertThat(startPageCollection.hasFailedScenarios(), is(true));
        assertThat(startPageCollection.hasPassedScenarios(), is(false));
        assertThat(startPageCollection.hasSkippedScenarios(), is(false));
    }

    @Test
    public void hasPassedScenariosTest() {
        assertThat(startPageCollection.hasFailedScenarios(), is(false));
        assertThat(startPageCollection.hasPassedScenarios(), is(false));
        assertThat(startPageCollection.hasSkippedScenarios(), is(false));

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
        startPageCollection.addReports(reportList);

        assertThat(startPageCollection.hasFailedScenarios(), is(false));
        assertThat(startPageCollection.hasPassedScenarios(), is(true));
        assertThat(startPageCollection.hasSkippedScenarios(), is(false));
    }

    @Test
    public void hasSkippedScenariosTest() {
        assertThat(startPageCollection.hasFailedScenarios(), is(false));
        assertThat(startPageCollection.hasPassedScenarios(), is(false));
        assertThat(startPageCollection.hasSkippedScenarios(), is(false));

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
        startPageCollection.addReports(reportList);

        assertThat(startPageCollection.hasFailedScenarios(), is(false));
        assertThat(startPageCollection.hasPassedScenarios(), is(false));
        assertThat(startPageCollection.hasSkippedScenarios(), is(true));
    }

    @Test
    public void hasCustomParametersTest() {
        assertThat(startPageCollection.hasCustomParameters(), is(false));
        List<CustomParameter> customParameters = new ArrayList<>();
        CustomParameter parameter = new CustomParameter("key", "value");
        customParameters.add(parameter);
        startPageCollection.setCustomParameters(customParameters);
        assertThat(startPageCollection.hasCustomParameters(), is(true));
    }
}
