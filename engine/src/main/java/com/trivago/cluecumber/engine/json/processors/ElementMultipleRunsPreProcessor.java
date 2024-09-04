/*
 * Copyright 2023 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class preprocesses {@link Element} JSON to add additional information to it and extract attachments.
 */
@Singleton
public class ElementMultipleRunsPreProcessor {

    /**
     * The default constructor.
     */
    @Inject
    public ElementMultipleRunsPreProcessor() {
    }

    /**
     * This adds the latest run information to each scenario run multiple times.
     * It specifies if it was the last run started or not.
     *
     * @param reports The list of reports to cycle  through.
     */
    public void addMultipleRunsInformationToScenarios(final List<Report> reports) {

        List<Element> elements = new ArrayList<>();
        for (Report report : reports) {
            elements.addAll(report.getElements());
        }

        // Group elements by id (that should combine feature and scenario names) and line, to also ensure that scenario outlines are properly handled
        Map<String, Map<Integer, List<Element>>> groupedElements = elements.stream()
                .collect(Collectors.groupingBy(
                        Element::getId,
                        Collectors.groupingBy(Element::getLine)
                ));

        // set flags based on start time and add children element to last run element
        groupedElements.values().forEach(idGroup -> idGroup.values().forEach(group -> {
            if (group.size() < 2) {
                return;
            }
            group.sort(Comparator.comparing(
                    Element::getStartDateTime, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
            Element lastRunElement = group.get(0);
            lastRunElement.setMultiRunChildren(group);
            lastRunElement.setMultiRunParent(true);
            // remove first of line group as it is the last run element
            group.remove(0);
            for (Element element : group) {
                element.isMultiRunChild(true);
            }
            lastRunElement.setMultiRunChildren(group);
        }));

        // Remove elements marked as isMultiRunChild from each report
        for (Report report : reports) {
            report.getElements().removeIf(Element::isMultiRunChild);
        }
    }
}
