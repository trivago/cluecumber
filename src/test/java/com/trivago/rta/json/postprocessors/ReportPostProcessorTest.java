package com.trivago.rta.json.postprocessors;

import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.logging.CluecumberLogger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReportPostProcessorTest {
    private ReportPostProcessor reportPostProcessor;

    @Before
    public void setup() {
        CluecumberLogger logger = new CluecumberLogger();
        reportPostProcessor = new ReportPostProcessor(logger);
    }

    @Test
    public void postDeserializeTest(){
        Report report = new Report();

        List<Element> elements = new ArrayList<>();

        Element backgroundElement = new Element();
        List<Step> backgroundSteps = new ArrayList<>();
        Step backgroundStep = new Step();
        backgroundStep.setName("background step");
        backgroundSteps.add(backgroundStep);
        backgroundElement.setSteps(backgroundSteps);
        backgroundElement.setType("background");
        elements.add(backgroundElement);

        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        step.setName("element step 1");
        steps.add(step);
        Step step2 = new Step();
        step2.setName("element step 2");
        steps.add(step2);
        element.setSteps(steps);
        elements.add(element);

        report.setElements(elements);

        assertThat(report.getElements().size(), is(2));
        reportPostProcessor.postDeserialize(report, null,null);
        assertThat(report.getElements().size(), is(1));
        assertThat(report.getElements().get(0).getSteps().size(), is(3));
    }
}
