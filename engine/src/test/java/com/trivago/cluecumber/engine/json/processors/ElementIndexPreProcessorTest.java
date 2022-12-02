package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElementIndexPreProcessorTest {

    private ElementIndexPreProcessor elementIndexPreProcessor;

    @BeforeEach
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
        assertEquals(e.get(0).getScenarioIndex(), 0);
        assertEquals(e.get(1).getScenarioIndex(), 0);
        assertEquals(e.get(2).getScenarioIndex(), 0);

        elementIndexPreProcessor.addScenarioIndices(reports);

        assertEquals(e.get(0).getScenarioIndex(), 1);
        assertEquals(e.get(1).getScenarioIndex(), 2);
        assertEquals(e.get(2).getScenarioIndex(), 3);
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
        assertEquals(e.get(0).getScenarioIndex(), 0);
        assertEquals(e.get(1).getScenarioIndex(), 0);
        assertEquals(e.get(2).getScenarioIndex(), 0);

        elementIndexPreProcessor.addScenarioIndices(reports);

        assertEquals(e.get(0).getScenarioIndex(), 2);
        assertEquals(e.get(1).getScenarioIndex(), 1);
        assertEquals(e.get(2).getScenarioIndex(), 3);
    }
}