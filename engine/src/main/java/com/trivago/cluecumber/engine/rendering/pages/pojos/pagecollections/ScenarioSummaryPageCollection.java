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
package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.constants.Status;

import java.util.HashSet;
import java.util.Set;

/**
 * Page collection for the display of scenario results on the tag and step summary pages.
 */
public class ScenarioSummaryPageCollection extends SummaryPageCollection {
    private final Set<Integer> totalFailed = new HashSet<>();
    private final Set<Integer> totalPassed = new HashSet<>();
    private final Set<Integer> totalSkipped = new HashSet<>();

    /**
     * Constructor.
     *
     * @param pageTitle The page title.
     */
    ScenarioSummaryPageCollection(final String pageTitle) {
        super(pageTitle);
    }

    /**
     * Return the number of scenarios.
     *
     * @return The count.
     */
    public int getTotalNumberOfScenarios() {
        return getTotalNumberOfPassed() + getTotalNumberOfFailed() + getTotalNumberOfSkipped();
    }

    /**
     * Return the number of passed scenarios.
     *
     * @return The count.
     */
    public int getTotalNumberOfPassed() {
        return totalPassed.size();
    }

    /**
     * Return the number of failed scenarios.
     *
     * @return The count.
     */
    public int getTotalNumberOfFailed() {
        return totalFailed.size();
    }

    /**
     * Return the number of skipped scenarios.
     *
     * @return The count.
     */
    public int getTotalNumberOfSkipped() {
        return totalSkipped.size();
    }

    /**
     * Add a specific scenario index to an associated status.
     *
     * @param status        The {@link Status} instance.
     * @param scenarioIndex The scenario index.
     */
    public void addScenarioIndexByStatus(final Status status, final int scenarioIndex) {
        switch (status) {
            case FAILED:
                totalFailed.add(scenarioIndex);
                break;
            case PASSED:
                totalPassed.add(scenarioIndex);
                break;
            case SKIPPED:
                totalSkipped.add(scenarioIndex);
            default:
        }
    }
}
