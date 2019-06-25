package com.trivago.cluecumber.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Match;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Result;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AllStepsPageCollectionTest {
    private AllStepsPageCollection allStepsPageCollection;

    @Test
    public void getEmptyStepStatsTest() {
        List<Report> reports = new ArrayList<>();
        allStepsPageCollection = new AllStepsPageCollection(reports, "");
        Map<Step, ResultCount> tagStats = allStepsPageCollection.getStepResultCounts();
        assertThat(tagStats.size(), is(0));
    }

    @Test
    public void getStepStatsTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Map<Step, ResultCount> stepStats = allStepsPageCollection.getStepResultCounts();
        assertThat(stepStats.size(), is(2));

        Step step1 = new Step();
        step1.setName("step1");
        Match match1 = new Match();
        match1.setLocation("location1");
        step1.setMatch(match1);
        ResultCount step1Stats = stepStats.get(step1);
        assertThat(step1Stats.getTotal(), is(1));
        assertThat(step1Stats.getPassed(), is(0));
        assertThat(step1Stats.getFailed(), is(1));
        assertThat(step1Stats.getSkipped(), is(0));

        Step step2 = new Step();
        step2.setName("step2");
        Match match2 = new Match();
        match2.setLocation("location2");
        step2.setMatch(match2);
        ResultCount step2Stats = stepStats.get(step2);
        assertThat(step2Stats.getTotal(), is(2));
        assertThat(step2Stats.getPassed(), is(1));
        assertThat(step2Stats.getFailed(), is(0));
        assertThat(step2Stats.getSkipped(), is(1));
    }

    @Test
    public void getStepResultsTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        assertThat(allStepsPageCollection.getTotalNumberOfSteps(), is(2));
        assertThat(allStepsPageCollection.getTotalNumberOfFailed(), is(1));
        assertThat(allStepsPageCollection.getTotalNumberOfPassed(), is(1));
        assertThat(allStepsPageCollection.getTotalNumberOfSkipped(), is(1));
    }

    @Test
    public void getTotalNumberOfStepScenariosTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        assertThat(allStepsPageCollection.getTotalNumberOfScenarios(), is(3));
    }

    @Test
    public void getTotalNumberOfStepsTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        assertThat(allStepsPageCollection.getTotalNumberOfSteps(), is(2));
    }

    @Test
    public void getMinimumTimeFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertThat(allStepsPageCollection.getMinimumTimeFromStep(step), is("0m 00s 000ms"));
    }

    @Test
    public void getMinimumTimeScenarioIndexFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertThat(allStepsPageCollection.getMaximumTimeScenarioIndexFromStep(step), is(0));
    }

    @Test
    public void getMaximumTimeFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertThat(allStepsPageCollection.getMaximumTimeFromStep(step), is("0m 00s 000ms"));
    }

    @Test
    public void getMaximumTimeScenarioIndexFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertThat(allStepsPageCollection.getMinimumTimeScenarioIndexFromStep(step), is(0));
    }

    @Test
    public void getAverageTimeFromStepTest() {
        allStepsPageCollection = new AllStepsPageCollection(getTestReports(), "");
        Step step  = allStepsPageCollection.getSteps().iterator().next();
        assertThat(allStepsPageCollection.getAverageTimeFromStep(step), is("0m 00s 000ms"));
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
