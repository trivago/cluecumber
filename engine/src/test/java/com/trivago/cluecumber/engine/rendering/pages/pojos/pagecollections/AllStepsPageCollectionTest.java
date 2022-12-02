package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Match;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Result;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllStepsPageCollectionTest {
    private AllStepsPageCollection allStepsPageCollection;

    @Test
    public void getEmptyStepStatsTest() {
        List<Report> reports = new ArrayList<>();
        allStepsPageCollection = new AllStepsPageCollection(reports, "");
        Map<Step, ResultCount> tagStats = allStepsPageCollection.getStepResultCounts();
        assertEquals(tagStats.size(), 0);
    }

    @Test
    public void getStepStatsTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Map<Step, ResultCount> stepStats = allStepsPageCollection.getStepResultCounts();
        assertEquals(stepStats.size(), 2);

        Step step1 = new Step();
        step1.setName("step1");
        Match match1 = new Match();
        match1.setLocation("location1");
        step1.setMatch(match1);
        ResultCount step1Stats = stepStats.get(step1);
        assertEquals(step1Stats.getTotal(), 1);
        assertEquals(step1Stats.getPassed(), 0);
        assertEquals(step1Stats.getFailed(), 1);
        assertEquals(step1Stats.getSkipped(), 0);

        Step step2 = new Step();
        step2.setName("step2");
        Match match2 = new Match();
        match2.setLocation("location2");
        step2.setMatch(match2);
        ResultCount step2Stats = stepStats.get(step2);
        assertEquals(step2Stats.getTotal(), 2);
        assertEquals(step2Stats.getPassed(), 1);
        assertEquals(step2Stats.getFailed(), 0);
        assertEquals(step2Stats.getSkipped(), 1);
    }

    @Test
    public void getStepResultsTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        assertEquals(allStepsPageCollection.getTotalNumberOfSteps(), 2);
        assertEquals(allStepsPageCollection.getTotalNumberOfFailed(), 1);
        assertEquals(allStepsPageCollection.getTotalNumberOfPassed(), 1);
        assertEquals(allStepsPageCollection.getTotalNumberOfSkipped(), 1);
    }

    @Test
    public void getTotalNumberOfStepScenariosTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        assertEquals(allStepsPageCollection.getTotalNumberOfScenarios(), 3);
    }

    @Test
    public void getTotalNumberOfStepsTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        assertEquals(allStepsPageCollection.getTotalNumberOfSteps(), 2);
    }

    @Test
    public void getMinimumTimeFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertEquals(allStepsPageCollection.getMinimumTimeFromStep(step), "0m 00s 000ms");
    }

    @Test
    public void getMinimumTimeScenarioIndexFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertEquals(allStepsPageCollection.getMaximumTimeScenarioIndexFromStep(step), 0);
    }

    @Test
    public void getMaximumTimeFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertEquals(allStepsPageCollection.getMaximumTimeFromStep(step), "0m 00s 000ms");
    }

    @Test
    public void getMaximumTimeScenarioIndexFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertEquals(allStepsPageCollection.getMinimumTimeScenarioIndexFromStep(step), 0);
    }

    @Test
    public void getAverageTimeFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertEquals(allStepsPageCollection.getAverageTimeFromStep(step), "0m 00s 000ms");
    }

    private List<Report> getTestReports() {
        List<Report> reports = new ArrayList<>();

        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        step.setName("step1");
        Result result = new Result();
        result.setStatus(Status.FAILED.getStatusString());
        step.setResult(result);
        Match match = new Match();
        match.setLocation("location1");
        step.setMatch(match);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);

        element = new Element();
        steps = new ArrayList<>();
        step = new Step();
        step.setName("step2");
        result = new Result();
        result.setStatus(Status.PASSED.getStatusString());
        step.setResult(result);
        match = new Match();
        match.setLocation("location2");
        step.setMatch(match);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);

        element = new Element();
        steps = new ArrayList<>();
        step = new Step();
        step.setName("step3");
        result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        step.setResult(result);
        match = new Match();
        match.setLocation("location2");
        step.setMatch(match);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);

        reports.add(report);
        return reports;
    }
}
