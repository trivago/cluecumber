package com.trivago.rta.json.pojo;

import com.trivago.rta.constants.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ElementTest {
    private Element element;

    @Before
    public void setup() {
        element = new Element();
    }

    @Test
    public void testGetSkippedStatusInEmptyElements() {
        Status status = element.getStatus();
        assertThat(status, is(Status.SKIPPED));
        assertThat(element.isSkipped(), is(true));
    }

    @Test
    public void testGetPassedStatus() {
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
    public void testGetFailedStatus() {
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
    public void totalDurationTest() {
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setDuration(9991000003L);
        step.setResult(result);
        steps.add(step);

        Step step2 = new Step();
        Result result2 = new Result();
        result2.setDuration(123667782L);
        step2.setResult(result2);
        steps.add(step2);

        element.setSteps(steps);

        assertThat(element.getTotalDuration(), is(10114667785L));
        assertThat(element.returnTotalDurationString(), is("0m 10s 114ms"));
    }
}
