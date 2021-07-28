/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumber.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.rendering.pages.pojos.Times;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllStepsPageCollection extends ScenarioSummaryPageCollection {
    private final Map<Step, ResultCount> stepResultCounts = new HashMap<>();
    private final Map<Step, Times> stepTimes = new HashMap<>();

    public AllStepsPageCollection(List<Report> reports, final String pageTitle) {
        super(pageTitle);
        calculateStepResultCounts(reports);
    }

    /**
     * Get a map of {@link ResultCount} lists connected to step names.
     *
     * @return a map of {@link ResultCount} lists with steps as keys.
     */
    public Map<Step, ResultCount> getStepResultCounts() {
        return stepResultCounts;
    }

    public Set<Step> getSteps() {
        return stepResultCounts.keySet();
    }

    public int getTotalNumberOfSteps() {
        return stepResultCounts.size();
    }

    public String getMinimumTimeFromStep(final Step step) {
        return stepTimes.get(step).getMinimumTimeString();
    }

    public int getMinimumTimeScenarioIndexFromStep(final Step step) {
        return stepTimes.get(step).getMinimumTimeScenarioIndex();
    }

    public String getMaximumTimeFromStep(final Step step) {
        return stepTimes.get(step).getMaximumTimeString();
    }

    public int getMaximumTimeScenarioIndexFromStep(final Step step) {
        return stepTimes.get(step).getMaximumTimeScenarioIndex();
    }

    public String getAverageTimeFromStep(final Step step) {
        return stepTimes.get(step).getAverageTimeString();
    }

    /**
     * Calculate the numbers of failures, successes and skips per step.
     *
     * @param reports The {@link Report} list.
     */
    private void calculateStepResultCounts(final List<Report> reports) {
        if (reports == null) return;
        reports.forEach(report -> report.getElements().forEach(element -> {
            int scenarioIndex = element.getScenarioIndex();
            element.getSteps().forEach(step -> {
                ResultCount stepResultCount = stepResultCounts.getOrDefault(step, new ResultCount());
                updateResultCount(stepResultCount, step.getStatus());
                stepResultCounts.put(step, stepResultCount);
                addScenarioIndexByStatus(element.getStatus(), element.getScenarioIndex());
                Times stepTimes = this.stepTimes.getOrDefault(step, new Times());
                if (!step.isSkipped()) {
                    stepTimes.addTime(step.getResult().getDuration(), scenarioIndex);
                }
                this.stepTimes.put(step, stepTimes);
            });
        }));
    }
}
