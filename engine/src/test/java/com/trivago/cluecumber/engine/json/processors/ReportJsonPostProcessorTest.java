package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.json.pojo.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportJsonPostProcessorTest {
    private ReportJsonPostProcessor reportJsonPostProcessor;

    @BeforeEach
    public void setup() {
        reportJsonPostProcessor = new ReportJsonPostProcessor();
    }

    @Test
    public void postDeserializeTest() {
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
        List<Tag> scenarioTags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("@tag");
        scenarioTags.add(tag);
        element.setTags(scenarioTags);
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        step.setName("element step 1");
        steps.add(step);
        Step step2 = new Step();
        step2.setName("element step 2");
        steps.add(step2);
        element.setSteps(steps);
        elements.add(element);

        List<Tag> featureTags = new ArrayList<>();
        Tag featureTag = new Tag();
        tag.setName("@feature");
        featureTags.add(featureTag);
        report.setTags(featureTags);
        report.setElements(elements);

        assertEquals(report.getElements().size(), 2);
        assertEquals(report.getElements().get(1).getTags().size(), 1);
//        reportJsonPostProcessor.postDeserialize(report, null, null);
//        assertEquals(report.getElements().size(), 1);
//        Element firstElement = report.getElements().get(0);
//        assertEquals(firstElement.getTags().size(), 2);
//        List<Step> firstElementSteps = firstElement.getSteps();
//        assertEquals(firstElementSteps.size(), 2);
//        assertEquals(firstElementSteps.get(0).getName(), "element step 1");
//        assertEquals(firstElementSteps.get(1).getName(), "element step 2");
//
//        List<Step> firstElementBackgroundSteps = firstElement.getBackgroundSteps();
//        assertEquals(firstElementBackgroundSteps.size(), 2);
//        assertEquals(firstElementBackgroundSteps.get(0).getName(), "background step 1");
//        assertEquals(firstElementBackgroundSteps.get(1).getName(), "background step 2");
    }

    @Test
    public void postSerializeTest() {
        reportJsonPostProcessor.postSerialize(null, null, null);
    }
}
