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

public class ScenarioSummaryPageCollection extends SummaryPageCollection {
    private final Set<Integer> totalFailed = new HashSet<>();
    private final Set<Integer> totalPassed = new HashSet<>();
    private final Set<Integer> totalSkipped = new HashSet<>();

    ScenarioSummaryPageCollection(final String pageTitle) {
        super(pageTitle);
    }

    public int getTotalNumberOfScenarios() {
        return getTotalNumberOfPassed() + getTotalNumberOfFailed() + getTotalNumberOfSkipped();
    }

    public int getTotalNumberOfPassed() {
        return totalPassed.size();
    }

    public int getTotalNumberOfFailed() {
        return totalFailed.size();
    }

    public int getTotalNumberOfSkipped() {
        return totalSkipped.size();
    }

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
