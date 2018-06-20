package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Result;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.rendering.pages.pojos.CustomParameter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ScenarioSummaryPageCollectionTest {

    private ScenarioSummaryPageCollection scenarioSummaryPageCollection;

    @Before
    public void setup() {
        scenarioSummaryPageCollection = new ScenarioSummaryPageCollection();
    }

    @Test
    public void addReportsNullReportListTest() {
        scenarioSummaryPageCollection.addReports(null);
        assertThat(scenarioSummaryPageCollection.getReports().size(), is(0));
        assertThat(scenarioSummaryPageCollection.getTotalNumberOfFeatures(), is(0));
        assertThat(scenarioSummaryPageCollection.getTotalNumberOfScenarios(), is(0));
    }

    @Test
    public void addReportsValidReportListTest() {
        Report[] reportList = new Report[2];
        reportList[0] = new Report();
        reportList[1] = new Report();
        scenarioSummaryPageCollection.addReports(reportList);
        assertThat(scenarioSummaryPageCollection.getReports().size(), is(2));
        assertThat(scenarioSummaryPageCollection.getTotalNumberOfFeatures(), is(2));
        assertThat(scenarioSummaryPageCollection.getTotalNumberOfScenarios(), is(0));
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

        scenarioSummaryPageCollection.addReports(reportList);
        assertThat(scenarioSummaryPageCollection.getReports().size(), is(1));
        assertThat(scenarioSummaryPageCollection.getTotalNumberOfFeatures(), is(1));
        assertThat(scenarioSummaryPageCollection.getTotalNumberOfScenarios(), is(1));
    }

    @Test
    public void getTotalDurationDefaultValueTest() {
        long totalDuration = scenarioSummaryPageCollection.getTotalDuration();
        assertThat(totalDuration, is(0L));
    }

    @Test
    public void hasFailedScenariosTest() {
        assertThat(scenarioSummaryPageCollection.hasFailedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasPassedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasSkippedScenarios(), is(false));

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
        scenarioSummaryPageCollection.addReports(reportList);

        assertThat(scenarioSummaryPageCollection.hasFailedScenarios(), is(true));
        assertThat(scenarioSummaryPageCollection.hasPassedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasSkippedScenarios(), is(false));
    }

    @Test
    public void hasPassedScenariosTest() {
        assertThat(scenarioSummaryPageCollection.hasFailedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasPassedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasSkippedScenarios(), is(false));

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
        scenarioSummaryPageCollection.addReports(reportList);

        assertThat(scenarioSummaryPageCollection.hasFailedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasPassedScenarios(), is(true));
        assertThat(scenarioSummaryPageCollection.hasSkippedScenarios(), is(false));
    }

    @Test
    public void hasSkippedScenariosTest() {
        assertThat(scenarioSummaryPageCollection.hasFailedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasPassedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasSkippedScenarios(), is(false));

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
        scenarioSummaryPageCollection.addReports(reportList);

        assertThat(scenarioSummaryPageCollection.hasFailedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasPassedScenarios(), is(false));
        assertThat(scenarioSummaryPageCollection.hasSkippedScenarios(), is(true));
    }

    @Test
    public void hasCustomParametersTest() {
        assertThat(scenarioSummaryPageCollection.hasCustomParameters(), is(false));
        List<CustomParameter> customParameters = new ArrayList<>();
        CustomParameter parameter = new CustomParameter("key", "value");
        customParameters.add(parameter);
        scenarioSummaryPageCollection.setCustomParameters(customParameters);
        assertThat(scenarioSummaryPageCollection.hasCustomParameters(), is(true));
    }
}
