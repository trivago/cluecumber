/*
 * Copyright 2018 trivago N.V.
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

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.rendering.pages.pojos.Times;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllStepsPageCollection extends ScenarioSummaryPageCollection {
    private Map<Step, ResultCount> stepResultCounts = new HashMap<>();
    private Map<Step, Times> stepTimes = new HashMap<>();

    public AllStepsPageCollection(List<Report> reports) {
        super(PluginSettings.STEP_SUMMARY_PAGE_NAME);
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

    public String getMinimumTimeFromStep(final Step step){
        return stepTimes.get(step).getMinimumTime();
    }

    public String getMaximumTimeFromStep(final Step step){
        return stepTimes.get(step).getMaximumTime();
    }

    public String getAverageTimeFromStep(final Step step){
        return stepTimes.get(step).getAverageTime();
    }

    /**
     * Calculate the numbers of failures, successes and skips per step.
     *
     * @param reports The {@link Report} list.
     */
    private void calculateStepResultCounts(final List<Report> reports) {
        if (reports == null) return;
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                for (Step step : element.getSteps()) {
                    ResultCount stepResultCount = stepResultCounts.getOrDefault(step, new ResultCount());
                    updateResultCount(stepResultCount, step.getStatus());
                    stepResultCounts.put(step, stepResultCount);
                    Times stepTimes = this.stepTimes.getOrDefault(step, new Times());
                    stepTimes.addTime(step.getResult().getDuration());
                    this.stepTimes.put(step, stepTimes);
                    addScenarioIndexByStatus(element.getStatus(), element.getScenarioIndex());
                }
            }
        }
    }
}
