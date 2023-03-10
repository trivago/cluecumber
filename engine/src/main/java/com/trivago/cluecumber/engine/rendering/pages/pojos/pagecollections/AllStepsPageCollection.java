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

import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Times;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Page collection for the step overview page.
 */
public class AllStepsPageCollection extends ScenarioSummaryPageCollection {
    private final Map<Step, ResultCount> stepResultCounts = new HashMap<>();
    private final Map<Step, Times> stepTimes = new HashMap<>();

    /**
     * Constructor.
     *
     * @param reports   The list of {@link Report} instances.
     * @param pageTitle The page title.
     */
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

    /**
     * Get all steps.
     *
     * @return The {@link Step} set.
     */
    public Set<Step> getSteps() {
        return stepResultCounts.keySet();
    }

    /**
     * Get the number of all steps.
     *
     * @return The count.
     */
    public int getTotalNumberOfSteps() {
        return stepResultCounts.size();
    }

    /**
     * Get the minimum step time.
     *
     * @param step The {@link Step}.
     * @return The minimum time as string.
     */
    public String getMinimumTimeFromStep(final Step step) {
        return stepTimes.get(step).getMinimumTimeString();
    }

    /**
     * Get the scenario index from the minimum step time.
     *
     * @param step The {@link Step}.
     * @return The scenario index.
     */
    public int getMinimumTimeScenarioIndexFromStep(final Step step) {
        return stepTimes.get(step).getMinimumTimeIndex();
    }

    /**
     * Get the maximum step time.
     *
     * @param step The {@link Step}.
     * @return The minimum time as string.
     */
    public String getMaximumTimeFromStep(final Step step) {
        return stepTimes.get(step).getMaximumTimeString();
    }

    /**
     * Get the scenario index from the maximum step time.
     *
     * @param step The {@link Step}.
     * @return The scenario index.
     */
    public int getMaximumTimeScenarioIndexFromStep(final Step step) {
        return stepTimes.get(step).getMaximumTimeIndex();
    }

    /**
     * Get the average time of all step executions of a specific step.
     *
     * @param step The {@link Step}.
     * @return The average time as string.
     */
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
