package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ElementIndexPreProcessor {

    @Inject
    public ElementIndexPreProcessor() {
    }

    public void addScenarioIndices(final List<Report> reports) {
        List<Element> elements = new ArrayList<>();
        for (Report report : reports) {
            elements.addAll(report.getElements());
        }

        List<Element> sortedElements =
                elements.stream().
                        sorted(Comparator.comparing(Element::getStartTimestamp)).
                        collect(Collectors.toList());

        int scenarioIndex = 0;
        for (Element element : sortedElements) {
            if (element.isScenario()) {
                scenarioIndex++;
                element.setScenarioIndex(scenarioIndex);
            }
        }
    }
}
