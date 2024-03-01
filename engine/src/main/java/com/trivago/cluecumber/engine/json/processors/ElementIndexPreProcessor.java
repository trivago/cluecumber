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
import com.trivago.cluecumber.engine.json.pojo.Step;

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
    public void process(final List<Report> reports) {
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
                int stepIndex = 0;
                for (Step step : element.getSteps()) {
                    System.out.println("Step name: " + step.getKeyword());
                    int count = 0;
                    step.setIndex(stepIndex);
                    for (int i = 0; i < step.getKeyword().length(); i++) {
                        if (step.getKeyword().charAt(i) == '>') {
                            count++;
                        } else {
                            break;
                        }
                    }
                    System.out.println("Count: " + count);
                    if (count > 0) {
                        step.setCollapseLevel(count);
                        System.out.println("Collapse level: " + step.getCollapseLevel());
                        step.setKeyword(step.getKeyword().substring(count).trim());
                        System.out.println("New name: " + step.getName());
                    }
                    stepIndex++;
                }
            }
        }
    }
}
