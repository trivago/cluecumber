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
public class ElementRerunPreProcessor {

    /**
     * The default constructor.
     */
    @Inject
    public ElementRerunPreProcessor() {
    }

    /**
     * This adds the rerun information to each scenario.
     * It specifies if it triggered a rerun or if it is a rerun.
     *
     * @param reports The list of reports to cycle  through.
     */
    public void addRerunInformationToScenarios(final List<Report> reports) {

        // Get all elements
        List<Element> elements = new ArrayList<>();
        for (Report report : reports) {
            elements.addAll(report.getElements());
        }

        // keyword to identify reruns by URI element, to replace with property
        String rerunKeyword = "parallel_rerun";

        // Check if any element contains the rerun keyword
        boolean containsKeyword = elements.stream()
                .anyMatch(element -> element.getFeatureUri() != null && element.getFeatureUri().contains(rerunKeyword));

        if (containsKeyword) {
            // Group elements by URI and name, to ensure that scenario outlines are properly handled
            Map<String, Map<String, List<Element>>> groupedElements = elements.stream()
                    .collect(Collectors.groupingBy(
                            element -> extractPartialUri(element.getFeatureUri()),
                            Collectors.groupingBy(Element::getName)
                    ));
            // set flags based on start time
            for (Map<String, List<Element>> uriGroup : groupedElements.values()) {
                System.out.println("Found a group with " + uriGroup.size() + " elements");
                if (uriGroup.size() < 2) {
                    continue;
                }
                for (List<Element> nameGroup : uriGroup.values()) {
                    nameGroup.sort(Comparator.comparing(Element::getStartDateTime).reversed());
                    boolean first = true;
                    for (Element element : nameGroup) {
                        System.out.println("Element " + element.getName() + " has start time " + element.getStartDateTime());
                        if (first) {
                            element.setIsRerun(true);
                            first = false;
                        } else {
                            element.setHasTriggeredRerun(true);
                        }
                    }
                }
            }
        }
    }

    private static String extractPartialUri(String uri) {
        String partialScenarioUri = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf(".feature"));
        // handle Cucable parallel rerun scenario URIs
        if (uri.contains("_IT_scenario")) {
            partialScenarioUri = partialScenarioUri.split("_IT_scenario")[0] + "_IT";
        }
        return partialScenarioUri;
    }

}
