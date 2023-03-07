package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class preprocesses {@link Element} JSON to add additional information to it and extract attachments.
 */
@Singleton
public class ElementIndexPreProcessor {

    /**
     * The default constructor.
     */
    @Inject
    public ElementIndexPreProcessor() {
    }

    /**
     * This adds the internal scenario index to each scenario.
     * It is a sequential number that is unique for every scenario.
     *
     * @param reports The list of reports to cycle  through.
     */
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
