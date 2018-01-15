package com.trivago.rta.json.pojo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReportTest {
    Report report;

    @Before
    public void setup() {
        report = new Report();
    }

    @Test
    public void getEncodedNameTest() {
        report.setName("<Name/>");
        assertThat(report.getEncodedName(), is("&#60;Name/&#62;"));
    }

    @Test
    public void getEncodedDescriptionTest() {
        report.setName("<Description/>");
        assertThat(report.getEncodedDescription(), is("&#60;Description/&#62;"));
    }

    @Test
    public void getTotalDurationTest(){
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
