package com.trivago.cluecumber.json.processors;

import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ElementIndexPreProcessorTest {

    private ElementIndexPreProcessor elementIndexPreProcessor;

    @Before
    public void setup() {
        elementIndexPreProcessor = new ElementIndexPreProcessor();
    }

    @Test
    public void addScenarioIndicesWithoutTimestampTest() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Element element = new Element();
        element.setName("Scenario 1");
        element.setType("scenario");
        elements.add(element);

        element = new Element();
        element.setName("Scenario 2");
        element.setType("scenario");
        elements.add(element);

        element = new Element();
        element.setName("Scenario 3");
        element.setType("scenario");
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        List<Element> e = reports.get(0).getElements();
        assertThat(e.get(0).getScenarioIndex(), is(0));
        assertThat(e.get(1).getScenarioIndex(), is(0));
        assertThat(e.get(2).getScenarioIndex(), is(0));

        elementIndexPreProcessor.addScenarioIndices(reports);

        assertThat(e.get(0).getScenarioIndex(), is(1));
        assertThat(e.get(1).getScenarioIndex(), is(2));
        assertThat(e.get(2).getScenarioIndex(), is(3));
    }

    @Test
    public void addScenarioIndicesWithTimestampTest() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Element element = new Element();
        element.setName("Scenario 1");
        element.setType("scenario");
        element.setStartTimestamp("2019-04-11T08:00:25.829Z");
        elements.add(element);

        element = new Element();
        element.setName("Scenario 2");
        element.setType("scenario");
        element.setStartTimestamp("2019-04-11T08:00:21.123Z");
        elements.add(element);

        element = new Element();
        element.setName("Scenario 3");
        element.setType("scenario");
        element.setStartTimestamp("2019-04-11T09:00:21.123Z");
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        List<Element> e = reports.get(0).getElements();
        assertThat(e.get(0).getScenarioIndex(), is(0));
        assertThat(e.get(1).getScenarioIndex(), is(0));
        assertThat(e.get(2).getScenarioIndex(), is(0));

        elementIndexPreProcessor.addScenarioIndices(reports);

        assertThat(e.get(0).getScenarioIndex(), is(2));
        assertThat(e.get(1).getScenarioIndex(), is(1));
        assertThat(e.get(2).getScenarioIndex(), is(3));
    }
}