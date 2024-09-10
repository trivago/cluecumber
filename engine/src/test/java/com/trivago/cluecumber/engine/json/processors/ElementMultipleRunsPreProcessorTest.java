package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.json.pojo.DocString;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Row;
import com.trivago.cluecumber.engine.json.pojo.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElementMultipleRunsPreProcessorTest {
    private ElementMultipleRunsPreProcessor elementMultipleRunsPreProcessor;

    @BeforeEach
    public void setup() {
        elementMultipleRunsPreProcessor = new ElementMultipleRunsPreProcessor();
    }

    @Test
    public void addMultipleRunsInformationToGroupableScenariosTest() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        // First run of scenario 1
        Element element = new Element();
        element.setName("Scenario 1");
        element.setType("scenario");
        element.setId("feature1-scenario1");
        element.setLine(3);
        element.setStartTimestamp("2019-04-11T08:00:21.123Z");
        elements.add(element);

        // Scenario 2 without reruns
        element = new Element();
        element.setName("Scenario 2");
        element.setType("scenario");
        element.setId("feature1-scenario2");
        element.setLine(15);
        element.setStartTimestamp("2019-04-11T08:00:21.123Z");
        elements.add(element);

        // Last run of scenario 1
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
        assertEquals(2, e.size());

        assertFalse(e.get(0).isPartOfMultiRun());
        assertFalse(e.get(0).isMultiRunParent());
        assertFalse(e.get(0).isMultiRunChild());
        assertEquals(e.get(0).getMultiRunChildren().size(), 0);

        assertTrue(e.get(1).isPartOfMultiRun());
        assertTrue(e.get(1).isMultiRunParent());
        assertFalse(e.get(1).isMultiRunChild());
        assertEquals(e.get(1).getMultiRunChildren().size(), 1);
    }

    @Test
    public void addMultipleRunsInformationToSameScenariosWithDifferentKeywords() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Step step = new Step();
        step.setKeyword("Given");
        Element element = createNewElement(List.of(step));
        elements.add(element);

        step = new Step();
        step.setKeyword("And");
        element = createNewElement(List.of(step));
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        elementMultipleRunsPreProcessor.addMultipleRunsInformationToScenarios(reports);

        List<Element> newElements = reports.get(0).getElements();
        assertEquals(newElements.size(), 2);
    }

    @Test
    public void addMultipleRunsInformationToSameScenariosWithDifferentParameterValues() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Step step = new Step();
        step.setName("Given I have 4 apples");
        Element element = createNewElement(List.of(step));
        elements.add(element);

        step = new Step();
        step.setName("Given I have 5 apples");
        element = createNewElement(List.of(step));
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        elementMultipleRunsPreProcessor.addMultipleRunsInformationToScenarios(reports);

        List<Element> newElements = reports.get(0).getElements();
        assertEquals(newElements.size(), 2);
    }

    @Test
    public void addMultipleRunsInformationToSameScenariosWithDifferentDocStrings() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Step step = new Step();
        DocString docString = new DocString();
        docString.setValue("abc");
        step.setDocString(docString);
        Element element = createNewElement(List.of(step));
        elements.add(element);

        step = new Step();
        docString = new DocString();
        docString.setValue("def");
        step.setDocString(docString);
        element = createNewElement(List.of(step));
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        elementMultipleRunsPreProcessor.addMultipleRunsInformationToScenarios(reports);

        List<Element> newElements = reports.get(0).getElements();
        assertEquals(newElements.size(), 2);
    }

    @Test
    public void addMultipleRunsInformationToSameScenariosWithDifferentOutputs() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Step step = new Step();
        step.setOutput(List.of("abc"));
        Element element = createNewElement(List.of(step));
        elements.add(element);

        step = new Step();
        step.setOutput(List.of("def"));
        element = createNewElement(List.of(step));
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        elementMultipleRunsPreProcessor.addMultipleRunsInformationToScenarios(reports);

        List<Element> newElements = reports.get(0).getElements();
        assertEquals(newElements.size(), 2);
    }

    @Test
    public void addMultipleRunsInformationToSameScenariosWithDifferentDataTableRows() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Step step = new Step();
        List<Row> rows = new ArrayList<>();
        Row row = new Row();
        row.setCells(List.of("abc"));
        step.setRows(rows);
        Element element = createNewElement(List.of(step));
        elements.add(element);

        step = new Step();
        rows = new ArrayList<>();
        row = new Row();
        row.setCells(List.of("def"));
        step.setRows(rows);
        element = createNewElement(List.of(step));
        elements.add(element);

        report.setElements(elements);
        reports.add(report);

        elementMultipleRunsPreProcessor.addMultipleRunsInformationToScenarios(reports);

        List<Element> newElements = reports.get(0).getElements();
        assertEquals(newElements.size(), 2);
    }

    private Element createNewElement(final List<Step> steps) {
        Element element = new Element();
        element.setName("Scenario 1");
        element.setType("scenario");
        element.setId("feature1-scenario1");
        element.setSteps(steps);
        element.setLine(3);
        element.setStartTimestamp("2019-04-11T08:00:21.123Z");
        return element;
    }
}
