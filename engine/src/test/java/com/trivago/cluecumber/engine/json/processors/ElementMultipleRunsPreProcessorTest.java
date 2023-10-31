package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElementMultipleRunsPreProcessorTest {
    private ElementMultipleRunsPreProcessor elementMultipleRunsPreProcessor;

    @BeforeEach
public void setup() {
        elementMultipleRunsPreProcessor = new ElementMultipleRunsPreProcessor();
    }

    @Test
    public void addMultipleRunsInformationToScenariosTest() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Element element = new Element();
        element.setName("Scenario 1");
        element.setType("scenario");
        element.setId("feature1-scenario1");
        element.setLine(3);
        element.setStartTimestamp("2019-04-11T08:00:21.123Z");
        elements.add(element);

        element = new Element();
        element.setName("Scenario 2");
        element.setType("scenario");
        element.setId("feature1-scenario2");
        element.setLine(15);
        element.setStartTimestamp("2019-04-11T08:00:21.123Z");
        elements.add(element);

        element = new Element();
        element.setName("Scenario 1");
        element.setType("scenario");
        element.setId("feature1-scenario1");
        element.setLine(3);
        element.setStartTimestamp("2019-04-11T08:00:25.829Z");
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        List<Element> e = reports.get(0).getElements();

        elementMultipleRunsPreProcessor.addMultipleRunsInformationToScenarios(reports);

        assertEquals(e.get(0).getIsNotLastOfMultipleScenarioRuns(), true);
        assertEquals(e.get(1).getIsNotLastOfMultipleScenarioRuns(), false);
        assertEquals(e.get(2).getIsNotLastOfMultipleScenarioRuns(), false);
        assertEquals(e.get(0).getIsLastOfMultipleScenarioRuns(), false);
        assertEquals(e.get(1).getIsLastOfMultipleScenarioRuns(), false);
        assertEquals(e.get(2).getIsLastOfMultipleScenarioRuns(), true);
        assertEquals(e.get(0).getChildrenElements().size(), 0);
        assertEquals(e.get(1).getChildrenElements().size(), 0);
        assertEquals(e.get(2).getChildrenElements().size(), 1);
    }
}
