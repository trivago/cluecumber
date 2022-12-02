package com.trivago.cluecumber.engine.json.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportTest {
    private Report report;

    @BeforeEach
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
        assertEquals(report.getTotalDuration(), 10000000L);
    }
}
