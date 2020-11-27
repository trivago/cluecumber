package com.trivago.cluecumber.json.processors;

import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.json.pojo.Tag;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReportJsonPostProcessorTest {
    private ReportJsonPostProcessor reportJsonPostProcessor;

    @Before
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

        assertThat(report.getElements().size(), is(2));
        assertThat(report.getElements().get(1).getTags().size(), is(1));
        reportJsonPostProcessor.postDeserialize(report, null, null);
        assertThat(report.getElements().size(), is(1));
        Element firstElement = report.getElements().get(0);
        assertThat(firstElement.getTags().size(), is(2));
        List<Step> firstElementSteps = firstElement.getSteps();
        assertThat(firstElementSteps.size(), is(2));
        assertThat(firstElementSteps.get(0).getName(), is("element step 1"));
        assertThat(firstElementSteps.get(1).getName(), is("element step 2"));

        List<Step> firstElementBackgroundSteps = firstElement.getBackgroundSteps();
        assertThat(firstElementBackgroundSteps.size(), is(2));
        assertThat(firstElementBackgroundSteps.get(0).getName(), is("background step 1"));
        assertThat(firstElementBackgroundSteps.get(1).getName(), is("background step 2"));
    }

    @Test
    public void postSerializeTest() {
        reportJsonPostProcessor.postSerialize(null, null, null);
    }
}
