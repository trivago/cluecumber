package com.trivago.cluecumber.json.pojo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportTest {
    private Report report;

    @Before
    public void setup() {
        report = new Report();
    }

    @Test
    public void getTotalDurationTest() {
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setDuration(10000000);
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);
        assertThat(report.getTotalDuration(), is(10000000L));
    }
}
