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

import com.trivago.cluecumber.engine.json.pojo.Argument;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Row;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.json.pojo.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
        Map<String, List<Element>> elementsByUniqueId = new HashMap<>();

        // Group elements by unique ID
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                String combinedId = generateCombinedId(element);
                elementsByUniqueId.computeIfAbsent(combinedId, k -> new ArrayList<>()).add(element);
            }
        }

        // Process each group of elements
        for (List<Element> group : elementsByUniqueId.values()) {
            if (group.size() < 2) continue;

            group.sort(Comparator.comparing(Element::getStartDateTime, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
            Element parentElement = group.remove(0);
            parentElement.setMultiRunParent(true);
            group.forEach(element -> element.isMultiRunChild(true));
            parentElement.setMultiRunChildren(group);
        }

        // Remove child elements from reports
        for (Report report : reports) {
            report.getElements().removeIf(Element::isMultiRunChild);
        }
    }

    private String generateCombinedId(Element element) {
        List<String> argumentValues = new ArrayList<>();
        List<String> docStrings = new ArrayList<>();
        List<List<String>> outputs = new ArrayList<>();
        List<String> rows = new ArrayList<>();

        for (Step step : element.getSteps()) {
            step.getMatch().getArguments().stream()
                    .map(argument -> argument.getOffset() + ": " + argument.getVal())
                    .forEach(argumentValues::add);

            if (step.getDocString() != null) {
                docStrings.add(step.getDocString().getLine() + ": " + step.getDocString().getValue());
            }

            if (step.getOutput() != null) {
                outputs.add(step.getOutput());
            }

            if (step.getRows() != null) {
                step.getRows().forEach(row -> rows.add(String.join(", ", row.getCells())));
            }
        }

        return String.valueOf((element.getFeatureIndex() +
                               element.getDescription() +
                               element.getLine() +
                               element.getFeatureName() +
                               element.getName() +
                               element.getId() +
                               element.getTags().stream().map(Tag::getName).collect(Collectors.joining(",")) +
                               element.getTotalNumberOfSteps() +
                               element.getSteps().stream().map(Step::getKeyword).collect(Collectors.joining(", ")) +
                               element.getSteps().stream().map(Step::getName).collect(Collectors.joining(", ")) +
                               String.join(", ", docStrings) +
                               String.join(", ", argumentValues) +
                               String.join(", ", rows) +
                               outputs.stream().map(output -> String.join(", ", output)).collect(Collectors.joining(", ")))
                .hashCode());
    }
}
