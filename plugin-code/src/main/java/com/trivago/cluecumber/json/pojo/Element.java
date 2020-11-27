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

package com.trivago.cluecumber.json.pojo;

import com.google.gson.annotations.SerializedName;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Element {
    private List<ResultMatch> before = new ArrayList<>();
    private int line;
    private String featureName = "";
    private String featureUri = "";
    private String name = "";
    private String description = "";
    private String id = "";
    private List<ResultMatch> after = new ArrayList<>();
    private String type = "";
    private String keyword = "";
    private List<Step> backgroundSteps = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    @SerializedName("start_timestamp")
    private String startTimestamp = "";

    private transient int featureIndex = 0;
    private transient int scenarioIndex = 0;
    private transient boolean failOnPendingOrUndefined = false;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(final String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public ZonedDateTime getStartDateTime() {
        return RenderingUtils.convertTimestampToZonedDateTime(startTimestamp);
    }

    public ZonedDateTime getEndDateTime() {
        ZonedDateTime startDateTime = getStartDateTime();
        if (startDateTime != null) {
            return getStartDateTime().plusNanos(getTotalDuration());
        } else {
            return null;
        }
    }

    public String getStartDateString() {
        return RenderingUtils.convertZonedDateTimeToDateString(getStartDateTime());
    }

    public String getStartTimeString() {
        return RenderingUtils.convertZonedDateTimeToTimeString(getStartDateTime());
    }

    public String getEndDateString() {
        return RenderingUtils.convertZonedDateTimeToDateString(getEndDateTime());
    }

    public String getEndTimeString() {
        return RenderingUtils.convertZonedDateTimeToTimeString(getEndDateTime());
    }

    public List<ResultMatch> getBefore() {
        return before;
    }

    public void setBefore(final List<ResultMatch> before) {
        this.before = before;
    }

    public boolean anyBeforeHookHasContent() {
        for (ResultMatch resultMatch : before) {
            if (resultMatch.hasContent()) {
                return true;
            }
        }
        return false;
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

    public boolean anyAfterHookHasContent() {
        for (ResultMatch resultMatch : after) {
            if (resultMatch.hasContent()) {
                return true;
            }
        }
        return false;
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

    public List<Step> getBackgroundSteps() {
        return backgroundSteps;
    }

    public void setBackgroundSteps(final List<Step> steps) {
        this.backgroundSteps = steps;
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
            if (afterHook.isFailed()) {
                return Status.FAILED;
            }
        }

        // If all steps have the same status, return this as the scenario status.
        for (Status status : Status.BASIC_STATES) {
            int stepsWithCertainStatusCount = 0;
            for (Step step : steps) {
                if (step.getConsolidatedStatus() == status) {
                    stepsWithCertainStatusCount++;
                }

                // If any step hooks fail, report scenario as failed.
                for (ResultMatch beforeStepHook : step.getBefore()) {
                    if (beforeStepHook.isFailed()) {
                        return Status.FAILED;
                    }
                }
                for (ResultMatch afterStepHook : step.getAfter()) {
                    if (afterStepHook.isFailed()) {
                        return Status.FAILED;
                    }
                }
            }

            if (totalSteps == stepsWithCertainStatusCount) {
                if (status == Status.SKIPPED) {
                    if (failOnPendingOrUndefined) {
                        return Status.FAILED;
                    }
                }
                return status;
            }
        }

        // If at least one step passed and the other steps are skipped, return passed (or failed if failOnPendingOrUndefined is true).
        if (getTotalNumberOfPassedSteps() >= 0 &&
                (getTotalNumberOfSkippedSteps() + getTotalNumberOfPassedSteps()) == getTotalNumberOfSteps()) {
            if (failOnPendingOrUndefined) {
                return Status.FAILED;
            }
            return Status.PASSED;
        }

        // If all steps are skipped return skipped (or failed if failOnPendingOrUndefined is true).
        if (getTotalNumberOfSkippedSteps() == totalSteps) {
            if (failOnPendingOrUndefined) {
                return Status.FAILED;
            }
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
        return getNumberOfStepsWithStatus(Status.FAILED);
    }

    public int getTotalNumberOfSkippedSteps() {
        return getNumberOfStepsWithStatus(Status.SKIPPED);
    }

    private int getNumberOfStepsWithStatus(final Status status) {
        return (int) getSteps().stream().filter(step -> step.getConsolidatedStatus() == status).count();
    }

    public long getTotalDuration() {
        long totalDurationNanoseconds = before.stream().mapToLong(beforeStep -> beforeStep.getResult().getDuration()).sum();
        totalDurationNanoseconds += backgroundSteps.stream().mapToLong(Step::getTotalDuration).sum();
        totalDurationNanoseconds += steps.stream().mapToLong(Step::getTotalDuration).sum();
        totalDurationNanoseconds += after.stream().mapToLong(afterStep -> afterStep.getResult().getDuration()).sum();
        return totalDurationNanoseconds;
    }

    public String returnTotalDurationString() {
        return RenderingUtils.convertNanosecondsToTimeString(getTotalDuration());
    }

    public boolean hasHooks() {
        return getBefore().size() > 0 || getAfter().size() > 0;
    }

    public boolean hasHooksWithContent() {
        return anyBeforeHookHasContent() || anyAfterHookHasContent();
    }

    public boolean hasDocStrings() {
        for (Step step : backgroundSteps) {
            if (step.getDocString() != null) {
                return true;
            }
        }
        for (Step step : steps) {
            if (step.getDocString() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean hasStepHooks() {
        for (Step step : backgroundSteps) {
            if (step.getBefore().size() > 0) {
                return true;
            }
            if (step.getAfter().size() > 0) {
                return true;
            }
        }
        for (Step step : steps) {
            if (step.getBefore().size() > 0) {
                return true;
            }
            if (step.getAfter().size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasStepHooksWithContent() {
        for (Step step : backgroundSteps) {
            if (step.hasHooksWithContent())
            {
                return true;
            }
        }
        for (Step step : steps) {
            if (step.hasHooksWithContent())
            {
                return true;
            }
        }
        return false;
    }

    public List<ResultMatch> getAllResultMatches() {
        List<ResultMatch> resultMatches = new ArrayList<>(getBefore());
        resultMatches.addAll(getBackgroundSteps());
        resultMatches.addAll(getSteps());
        resultMatches.addAll(getAfter());
        return resultMatches;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(final String featureName) {
        this.featureName = featureName;
    }

    public int getFeatureIndex() {
        return featureIndex;
    }

    public void setFeatureIndex(final int featureIndex) {
        this.featureIndex = featureIndex;
    }

    public void setFailOnPendingOrUndefined(final boolean failOnPendingOrUndefined) {
        this.failOnPendingOrUndefined = failOnPendingOrUndefined;
    }

    public String getFeatureUri() {
        return featureUri;
    }

    public void setFeatureUri(String featureUri) {
        this.featureUri = featureUri;
    }
}
