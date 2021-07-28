package com.trivago.cluecumber.json.pojo;

import com.trivago.cluecumber.constants.Status;
import org.junit.Before;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ElementTest {
    private Element element;
    private String oldTimeZoneId;

    @Before
    public void setup() {
        element = new Element();
    }

    @Test
    public void getSkippedStatusInEmptyElementsTest() {
        Status status = element.getStatus();
        assertThat(status, is(Status.SKIPPED));
        assertThat(element.isSkipped(), is(true));
    }

    @Test
    public void getPassedStatusTest() {
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.PASSED));
        assertThat(element.isPassed(), is(true));
    }

    @Test
    public void passedStatusOnPassedAndSkippedStepsTest() {
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);

        step = new Step();
        result = new Result();
        result.setStatus("skipped");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.PASSED));
    }

    @Test
    public void failedStatusOnFailedBeforeHookTest() {
        List<com.trivago.cluecumber.json.pojo.ResultMatch> before = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch beforeHook = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeHookResult = new Result();
        beforeHookResult.setStatus("failed");
        beforeHook.setResult(beforeHookResult);
        before.add(beforeHook);
        element.setBefore(before);

        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);

        step = new Step();
        result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.FAILED));
    }

    @Test
    public void failedStatusOnFailedAfterHookTest() {
        List<com.trivago.cluecumber.json.pojo.ResultMatch> after = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch afterHook = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result afterHookResult = new Result();
        afterHookResult.setStatus("failed");
        afterHook.setResult(afterHookResult);
        after.add(afterHook);
        element.setAfter(after);

        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);

        step = new Step();
        result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.FAILED));
    }

    @Test
    public void failedStatusOnFailedAfterStepHookTest() {
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("passed");
        step.setResult(result);

        List<com.trivago.cluecumber.json.pojo.ResultMatch> after = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch afterStepHook = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result afterStepHookResult = new Result();
        afterStepHookResult.setStatus("failed");
        afterStepHook.setResult(afterStepHookResult);
        after.add(afterStepHook);
        step.setAfter(after);

        steps.add(step);

        step = new Step();
        result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.FAILED));
    }

    @Test
    public void failedStatusOnFailedBeforeStepHookTest() {
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("passed");
        step.setResult(result);

        List<com.trivago.cluecumber.json.pojo.ResultMatch> before = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch beforeStepHook = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeStepHookResult = new Result();
        beforeStepHookResult.setStatus("failed");
        step.setResult(beforeStepHookResult);
        before.add(beforeStepHook);
        step.setBefore(before);

        steps.add(step);

        step = new Step();
        result = new Result();
        result.setStatus("passed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.FAILED));
    }

    @Test
    public void getFailedStatusTest() {
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("failed");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.FAILED));
        assertThat(element.isFailed(), is(true));
    }

    @Test
    public void getUndefinedStatusTest() {
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus("undefined");
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);

        Status status = element.getStatus();
        assertThat(status, is(Status.SKIPPED));
        assertThat(element.isSkipped(), is(true));
    }

    @Test
    public void totalDurationTest() {
        List<com.trivago.cluecumber.json.pojo.ResultMatch> beforeSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch before = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeResult = new Result();
        beforeResult.setDuration(1000000);
        before.setResult(beforeResult);
        beforeSteps.add(before);
        element.setBefore(beforeSteps);

        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result stepResult = new Result();
        stepResult.setDuration(9991000003L);
        step.setResult(stepResult);
        steps.add(step);

        Step step2 = new Step();
        Result stepResult2 = new Result();
        stepResult2.setDuration(123667782L);
        step2.setResult(stepResult2);
        steps.add(step2);

        element.setSteps(steps);

        List<com.trivago.cluecumber.json.pojo.ResultMatch> afterSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch after = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result afterResult = new Result();
        afterResult.setDuration(2000000);
        after.setResult(afterResult);
        afterSteps.add(after);
        element.setAfter(afterSteps);

        assertThat(element.getTotalDuration(), is(10117667785L));
        assertThat(element.returnTotalDurationString(), is("0m 10s 117ms"));
    }

    @Test
    public void stepSummaryTest() {
        List<Step> steps = new ArrayList<>();

        Step step1 = new Step();
        Result result1 = new Result();
        result1.setStatus("passed");
        step1.setResult(result1);
        steps.add(step1);
        steps.add(step1);
        steps.add(step1);

        Step step2 = new Step();
        Result result2 = new Result();
        result2.setStatus("skipped");
        step2.setResult(result2);
        steps.add(step2);

        Step step3 = new Step();
        Result result3 = new Result();
        result3.setStatus("pending");
        step3.setResult(result3);
        steps.add(step3);

        Step step4 = new Step();
        Result result4 = new Result();
        result4.setStatus("failed");
        step4.setResult(result4);
        steps.add(step4);

        element.setSteps(steps);

        assertThat(element.getTotalNumberOfSteps(), is(6));
        assertThat(element.getTotalNumberOfPassedSteps(), is(3));
        assertThat(element.getTotalNumberOfFailedSteps(), is(1));
        assertThat(element.getTotalNumberOfSkippedSteps(), is(2));
    }

    @Test
    public void hasHooksTest() {
        assertThat(element.hasHooks(), is(false));
        List<ResultMatch> before = new ArrayList<>();
        before.add(new ResultMatch());
        element.setBefore(before);
        assertThat(element.hasHooks(), is(true));
    }

    @Test
    public void hasDocStringsTest() {
        assertThat(element.hasDocStrings(), is(false));

        List<Step> steps = new ArrayList<>();

        Step step1 = new Step();
        steps.add(step1);

        Step step2 = new Step();
        step2.setDocString(new DocString());
        steps.add(step2);

        element.setSteps(steps);

        assertThat(element.hasDocStrings(), is(true));
    }

    @Test
    public void hasStepHooksBeforeTest() {
        assertThat(element.hasStepHooks(), is(false));

        List<Step> steps = new ArrayList<>();

        Step step1 = new Step();
        steps.add(step1);

        Step step2 = new Step();
        List<ResultMatch> beforeStepHooks = new ArrayList<>();
        beforeStepHooks.add(new ResultMatch());
        step2.setBefore(beforeStepHooks);
        steps.add(step2);

        element.setSteps(steps);

        assertThat(element.hasStepHooks(), is(true));
    }

    @Test
    public void hasStepHooksAfterTest() {
        assertThat(element.hasStepHooks(), is(false));

        List<Step> steps = new ArrayList<>();

        Step step1 = new Step();
        steps.add(step1);

        Step step2 = new Step();
        List<ResultMatch> afterStepHooks = new ArrayList<>();
        afterStepHooks.add(new ResultMatch());
        step2.setAfter(afterStepHooks);
        steps.add(step2);

        element.setSteps(steps);
        assertThat(element.hasStepHooks(), is(true));
    }

    @Test
    public void getStartDateTimeTest() {
        element.setStartTimestamp("2019-04-11T08:00:23.668Z");
        assertThat(element.getStartDateTime().format(DateTimeFormatter.ISO_DATE_TIME), is("2019-04-11T08:00:23.668Z"));
    }

    @Test
    public void getEndDateTimeTest() {
        element.setStartTimestamp("2019-04-11T08:00:23.668Z");
        List<com.trivago.cluecumber.json.pojo.ResultMatch> beforeSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch before = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeResult = new Result();
        beforeResult.setDuration(10000000000000L);
        before.setResult(beforeResult);
        beforeSteps.add(before);
        element.setBefore(beforeSteps);
        assertThat(element.getEndDateTime().format(DateTimeFormatter.ISO_DATE_TIME), is("2019-04-11T10:47:03.668Z"));
    }

    @Test
    public void getEndDateTimeNoStartDateTest() {
        List<com.trivago.cluecumber.json.pojo.ResultMatch> beforeSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch before = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeResult = new Result();
        beforeResult.setDuration(10000000000000L);
        before.setResult(beforeResult);
        beforeSteps.add(before);
        element.setBefore(beforeSteps);
        assertThat(element.getEndDateTime(), is(nullValue()));
    }

    @Test
    public void getStartDateStringTest() {
        element.setStartTimestamp("2019-04-11T08:00:23.668Z");
        assertThat(element.getStartDateString(), is("2019-04-11"));
    }

    @Test
    public void getStartTimeStringTest() {
        element.setStartTimestamp("2019-04-11T08:00:23.668Z");
        assertThat(element.getStartTimeString(), endsWith(":00:23"));
    }

    @Test
    public void getEndDateStringTest() {
        element.setStartTimestamp("2019-04-11T08:00:23.668Z");
        List<com.trivago.cluecumber.json.pojo.ResultMatch> beforeSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch before = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeResult = new Result();
        beforeResult.setDuration(10000000000000L);
        before.setResult(beforeResult);
        beforeSteps.add(before);
        element.setBefore(beforeSteps);
        assertThat(element.getEndDateString(), is("2019-04-11"));
    }

    @Test
    public void getEndTimeStringTest() {
        element.setStartTimestamp("2019-04-11T08:00:23.668Z");
        List<com.trivago.cluecumber.json.pojo.ResultMatch> beforeSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch before = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeResult = new Result();
        beforeResult.setDuration(10000000000000L);
        before.setResult(beforeResult);
        beforeSteps.add(before);
        element.setBefore(beforeSteps);
        assertThat(element.getEndTimeString(), endsWith(":47:03"));
    }
}
