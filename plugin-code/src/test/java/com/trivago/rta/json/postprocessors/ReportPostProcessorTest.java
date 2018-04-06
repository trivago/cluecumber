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

        Step backgroundStep1 = new Step();
        backgroundStep1.setName("background step 1");
        backgroundSteps.add(backgroundStep1);

        Step backgroundStep2 = new Step();
        backgroundStep2.setName("background step 2");
        backgroundSteps.add(backgroundStep2);

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
        List<Step> firstElementSteps = report.getElements().get(0).getSteps();
        assertThat(firstElementSteps.size(), is(4));
        assertThat(firstElementSteps.get(0).getName(), is("background step 1"));
        assertThat(firstElementSteps.get(1).getName(), is("background step 2"));
        assertThat(firstElementSteps.get(2).getName(), is("element step 1"));
        assertThat(firstElementSteps.get(3).getName(), is("element step 2"));
    }
}
