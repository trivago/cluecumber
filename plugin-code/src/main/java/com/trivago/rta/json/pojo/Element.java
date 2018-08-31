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

package com.trivago.rta.json.pojo;

import com.trivago.rta.constants.Status;
import com.trivago.rta.rendering.RenderingUtils;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private List<ResultMatch> before = new ArrayList<>();
    private int line;
    private String name = "";
    private String description = "";
    private String id = "";
    private List<ResultMatch> after = new ArrayList<>();
    private String type = "";
    private String keyword = "";
    private List<Step> steps = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    private transient int scenarioIndex = 0;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    public List<ResultMatch> getBefore() {
        return before;
    }

    public void setBefore(final List<ResultMatch> before) {
        this.before = before;
    }

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public String getName() {
        return !name.isEmpty() ? name : "[Unnamed]";
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public List<ResultMatch> getAfter() {
        return after;
    }

    public void setAfter(final List<ResultMatch> after) {
        this.after = after;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(final List<Step> steps) {
        this.steps = steps;
    }

    public boolean isScenario() {
        return type.equals("scenario");
    }

    public boolean isFailed() {
        return getStatus() == Status.FAILED;
    }

    public boolean isPassed() {
        return getStatus() == Status.PASSED;
    }

    public boolean isSkipped() {
        return getStatus() == Status.SKIPPED;
    }

    public Status getStatus() {
        int totalSteps = steps.size();

        if (totalSteps == 0) {
            return Status.SKIPPED;
        }

        // If any hooks fail, report the scenario as failed
        for (ResultMatch beforeHook : before) {
            if (beforeHook.isFailed()) {
                return Status.FAILED;
            }
        }
        for (ResultMatch afterHook : after) {
            if (afterHook.isFailed()){
                return Status.FAILED;
            }
        }

        // If all steps have the same status, return this as the scenario status.
        for (Status status : Status.values()) {
            long count = 0L;
            for (Step step : steps) {
                if (step.getStatus() == status) {
                    count++;
                }

                // If any step hooks fail, report scenario as failed.
                for (ResultMatch beforeStepHook : step.getBefore()) {
                    if (beforeStepHook.isFailed()){
                        return Status.FAILED;
                    }
                }
                for (ResultMatch afterStepHook : step.getAfter()) {
                    if (afterStepHook.isFailed()){
                        return Status.FAILED;
                    }
                }
            }
            int stepNumber = (int) count;
            if (totalSteps == stepNumber) {
                if (status != Status.UNDEFINED) {
                    return status;
                } else {
                    return Status.SKIPPED;
                }
            }
        }

        // If at least one step passed and the other steps are skipped, return passed.
        if (getTotalNumberOfPassedSteps() >= 0 &&
                (getTotalNumberOfSkippedSteps() + getTotalNumberOfPassedSteps()) == getTotalNumberOfSteps()) {
            return Status.PASSED;
        }

        // If all steps are skipped return skipped.
        if (getTotalNumberOfSkippedSteps() == totalSteps) {
            return Status.SKIPPED;
        }

        return Status.FAILED;
    }

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public void setScenarioIndex(final int scenarioIndex) {
        this.scenarioIndex = scenarioIndex;
    }

    public int getTotalNumberOfSteps() {
        return getSteps().size();
    }

    public int getTotalNumberOfPassedSteps() {
        return getNumberOfStepsWithStatus(Status.PASSED);
    }

    public int getTotalNumberOfFailedSteps() {
        return getNumberOfStepsWithStatus(Status.FAILED) +
                getNumberOfStepsWithStatus(Status.UNDEFINED) +
                getNumberOfStepsWithStatus(Status.AMBIGUOUS);
    }

    public int getTotalNumberOfSkippedSteps() {
        return getNumberOfStepsWithStatus(Status.SKIPPED) + getNumberOfStepsWithStatus(Status.PENDING);
    }

    private int getNumberOfStepsWithStatus(final Status status) {
        return (int) getSteps().stream().filter(step -> step.getStatus() == status).count();
    }

    public long getTotalDuration() {
        long totalDurationMicroseconds = 0;
        for (ResultMatch beforeStep : before) {
            totalDurationMicroseconds += beforeStep.getResult().getDuration();
        }
        for (Step step : steps) {
            totalDurationMicroseconds += step.getResult().getDuration();
        }
        for (ResultMatch afterStep : after) {
            totalDurationMicroseconds += afterStep.getResult().getDuration();
        }
        return totalDurationMicroseconds;
    }

    public String returnTotalDurationString() {
        return RenderingUtils.convertMicrosecondsToTimeString(getTotalDuration());
    }

    public List<ResultMatch> getAllResultMatches() {
        List<ResultMatch> resultMatches = new ArrayList<>(getBefore());
        resultMatches.addAll(getSteps());
        resultMatches.addAll(getAfter());
        return resultMatches;
    }
}
