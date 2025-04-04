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
package com.trivago.cluecumber.engine.json.pojo;

import com.google.gson.annotations.SerializedName;
import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This represents a scenarios.
 */
@SuppressWarnings("unused")
public class Element {
    private List<ResultMatch> before = new ArrayList<>();
    private int line;
    private boolean isMultiRunParent = false;
    private boolean isMultiRunChild = false;
    private String featureName = "";
    private String featureUri = "";
    private String name = "";
    private String description = "";
    private String id = "";
    private List<ResultMatch> after = new ArrayList<>();
    private String type = "";
    private final String keyword = "";
    private List<Step> backgroundSteps = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    @SerializedName("start_timestamp")
    private String startTimestamp = "";
    private List<Element> multiRunChildren = new ArrayList<>();
    private transient int featureIndex = 0;
    private transient int scenarioIndex = 0;
    private transient boolean failOnPendingOrUndefined = false;
    private Status status;

    /**
     * Default constructor.
     */
    public Element() {
        // Default constructor
    }

    /**
     * Get the list of tags of this scenario.
     *
     * @return The tag list.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Set the scenario tags.
     *
     * @param tags The list of tags.
     */
    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Get the start timestamp.
     *
     * @return The start timestamp.
     */
    public String getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * Set the scenario's start timestamp.
     *
     * @param startTimestamp The start timestamp.
     */
    public void setStartTimestamp(final String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    /**
     * Get the start time including a timezone.
     *
     * @return The start time.
     */
    public ZonedDateTime getStartDateTime() {
        return RenderingUtils.convertTimestampToZonedDateTime(startTimestamp);
    }

    /**
     * Get the end time including a timezone.
     *
     * @return The start time.
     */
    public ZonedDateTime getEndDateTime() {
        ZonedDateTime startDateTime = getStartDateTime();
        if (startDateTime != null) {
            return getStartDateTime().plusNanos(getTotalDuration());
        } else {
            return null;
        }
    }

    /**
     * Get the start date of this scenario run.
     *
     * @return The date.
     */
    public String getStartDateString() {
        return RenderingUtils.convertZonedDateTimeToDateString(getStartDateTime());
    }

    /**
     * Get the start time of this scenario run.
     *
     * @return The date.
     */
    public String getStartTimeString() {
        return RenderingUtils.convertZonedDateTimeToTimeString(getStartDateTime());
    }

    /**
     * Get the end date of this scenario run.
     *
     * @return The date.
     */
    public String getEndDateString() {
        return RenderingUtils.convertZonedDateTimeToDateString(getEndDateTime());
    }

    /**
     * Get the end time of this scenario run.
     *
     * @return The time.
     */
    public String getEndTimeString() {
        return RenderingUtils.convertZonedDateTimeToTimeString(getEndDateTime());
    }

    /**
     * Get the before hooks.
     *
     * @return The before hooks.
     */
    public List<ResultMatch> getBefore() {
        return before;
    }

    /**
     * Add before hooks.
     *
     * @param before The list of before hooks.
     */
    public void setBefore(final List<ResultMatch> before) {
        this.before = before;
    }

    /**
     * Determine if any before hooks contain data or an exception.
     *
     * @return true if any before hooks contain data.
     */
    public boolean anyBeforeHookHasContent() {
        for (ResultMatch resultMatch : before) {
            if (resultMatch.hasContent() || resultMatch.isFailed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the starting line number of the scenario in the feature file.
     *
     * @return The scenario line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Add the starting line number of this scenario within the feature file.
     *
     * @param line the line number.
     */
    public void setLine(final int line) {
        this.line = line;
    }

    /**
     * Get the scenario name.
     *
     * @return The scenario name.
     */
    public String getName() {
        return !name.isEmpty() ? name : "[Unnamed]";
    }

    /**
     * Add the scenario name.
     *
     * @param name The scenario name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the scenario description.
     *
     * @return The scenario description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the scenario description.
     *
     * @param description The description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get the scenario's after hooks.
     *
     * @return The list of hooks.
     */
    public List<ResultMatch> getAfter() {
        return after;
    }

    /**
     * Set the scenario after hooks
     *
     * @param after The list of hooks.
     */
    public void setAfter(final List<ResultMatch> after) {
        this.after = after;
    }


    /**
     * Check if after hooks has content or an exception.
     *
     * @return true if the after hook has content.
     */
    public boolean anyAfterHookHasContent() {
        for (ResultMatch resultMatch : after) {
            if (resultMatch.hasContent() || resultMatch.isFailed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the type of this element.
     *
     * @return The string "scenario" or "background".
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of this scenario element.
     *
     * @param type The string "scenario" or "background".
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Get the scenario's steps.
     *
     * @return The steps.
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Set the scenario steps.
     *
     * @param steps The list of steps.
     */
    public void setSteps(final List<Step> steps) {
        this.steps = steps;
    }

    /**
     * Get the scenario's background steps.
     *
     * @return The background steps.
     */
    public List<Step> getBackgroundSteps() {
        return backgroundSteps;
    }

    /**
     * Set the background scenario steps.
     *
     * @param steps The list of steps.
     */
    public void setBackgroundSteps(final List<Step> steps) {
        this.backgroundSteps = steps;
    }

    /**
     * Determine if this is of type scenario.
     *
     * @return true if this is a scenario.
     */
    public boolean isScenario() {
        return type.equals("scenario");
    }

    /**
     * Check if this scenario failed.
     *
     * @return true if the status is failed.
     */
    public boolean isFailed() {
        return getStatus() == Status.FAILED;
    }

    /**
     * Check if this scenario passed.
     *
     * @return true if the status is passed.
     */
    public boolean isPassed() {
        return getStatus() == Status.PASSED;
    }

    /**
     * Check if this scenario skipped.
     *
     * @return true if the status is skipped.
     */
    public boolean isSkipped() {
        return getStatus() == Status.SKIPPED;
    }

    /**
     * Get the overall scenario status based on its step results.
     *
     * @return The overall status.
     */
    public Status getStatus() {
        if (status != null) {
            return status;
        }

        Set<Status> allStates = before.stream().map(ResultMatch::getStatus).collect(Collectors.toSet());
        backgroundSteps.stream().map(Step::getStatus).forEach(allStates::add);
        steps.stream().map(Step::getStatus).forEach(allStates::add);
        after.stream().map(ResultMatch::getStatus).forEach(allStates::add);

        if (allStates.isEmpty()) {
            status = Status.SKIPPED;
        } else if (failOnPendingOrUndefined && (allStates.contains(Status.PENDING) ||
                allStates.contains(Status.UNDEFINED))) {
            status = Status.FAILED;
        } else {
            status = Status.getHighestBasicState(allStates);
        }

        return status;
    }

    /**
     * Get the first exception class in the stack trace to display
     *
     * @return The exception class string.
     */
    public String getFirstExceptionClass() {
        return getFirstExceptionSummary().split(":")[0].split(" ")[0].trim();
    }

    /**
     * Determine the first exception summary in the stack trace to display on the scenario overview page.
     *
     * @return The exception class string.
     */
    public String getFirstExceptionSummary() {
        String firstException = getFirstException();
        String exceptionClass = firstException.split("\n")[0].trim();

        if (exceptionClass.isEmpty()) {
            exceptionClass = "";
        }
        return exceptionClass;
    }

    /**
     * Determine the first exception message in the stack trace to display on the scenario overview page.
     *
     * @return The exception message string.
     */
    public String getFirstException() {
        String exception = getResultListException(before);
        if (exception != null && !exception.isEmpty()) {
            return exception;
        }

        exception = getStepException(backgroundSteps);
        if (exception != null && !exception.isEmpty()) {
            return exception;
        }

        exception = getStepException(steps);
        if (exception != null && !exception.isEmpty()) {
            return RenderingUtils.escapeHTML(exception);
        }

        exception = getResultListException(after);
        if (exception != null && !exception.isEmpty()) {
            return exception;
        }
        return "";
    }

    /**
     * Determine the first exception message from the steps.
     *
     * @return The exception message string.
     */
    private String getStepException(final List<Step> steps) {
        for (Step step : steps) {
            String exception = getResultListException(step.getBefore());
            if (exception != null && !exception.isEmpty()) {
                return exception;
            }

            exception = step.getResult().getErrorMessage();
            if (exception != null && !exception.isEmpty()) {
                return exception;
            }

            exception = getResultListException(step.getAfter());
            if (exception != null && !exception.isEmpty()) {
                return exception;
            }
        }
        return null;
    }

    /**
     * Determine the first exception message within the {@link ResultMatch} instances.
     *
     * @return The exception message string.
     */
    private String getResultListException(final List<ResultMatch> resultMatches) {
        for (ResultMatch match : resultMatches) {
            if (match.isFailed()) {
                return RenderingUtils.escapeHTML(match.getResult().getErrorMessage());
            }
        }
        return null;
    }

    /**
     * Get the internal scenario index.
     *
     * @return The scenario index.
     */
    public int getScenarioIndex() {
        return scenarioIndex;
    }

    /**
     * Set the internal scenario index that is used for report links to scenario details.
     *
     * @param scenarioIndex The scenario index.
     */
    public void setScenarioIndex(final int scenarioIndex) {
        this.scenarioIndex = scenarioIndex;
    }

    /**
     * Get the total number of steps in this scenario.
     *
     * @return The number of steps.
     */
    public int getTotalNumberOfSteps() {
        return getAllStepsIncludingBackgroundSteps().size();
    }

    /**
     * Get the total number of passed steps in this scenario.
     *
     * @return The number of passed steps.
     */
    public int getTotalNumberOfPassedSteps() {
        return getNumberOfStepsWithStatus(Status.PASSED);
    }

    /**
     * Get the total number of failed steps in this scenario.
     *
     * @return The number of failed steps.
     */
    public int getTotalNumberOfFailedSteps() {
        return getNumberOfStepsWithStatus(Status.FAILED);
    }

    /**
     * Get the total number of skipped steps in this scenario.
     *
     * @return The number of skipped steps.
     */
    public int getTotalNumberOfSkippedSteps() {
        return getNumberOfStepsWithStatus(Status.SKIPPED);
    }

    /**
     * Get the total number of steps that match a given status.
     *
     * @param status The status to filter the steps by.
     * @return The number of step.
     */
    private int getNumberOfStepsWithStatus(final Status status) {
        return (int) getAllStepsIncludingBackgroundSteps().stream().filter(step -> step.getConsolidatedStatus() == status).count();
    }

    /**
     * Get the total duration of this scenario.
     *
     * @return the duration in nanoseconds.
     */
    public long getTotalDuration() {
        return before.stream().mapToLong(beforeStep -> beforeStep.getResult().getDuration()).sum() +
                backgroundSteps.stream().mapToLong(Step::getTotalDuration).sum() +
                steps.stream().mapToLong(Step::getTotalDuration).sum() +
                after.stream().mapToLong(afterStep -> afterStep.getResult().getDuration()).sum();
    }

    /**
     * Get the human-readable time string.
     *
     * @return the time string.
     */
    public String returnTotalDurationString() {
        return RenderingUtils.convertNanosecondsToTimeString(getTotalDuration());
    }

    /**
     * Check if this scenario has before or after hooks.
     *
     * @return true if there are before or after hooks.
     */
    public boolean hasHooks() {
        return !getBefore().isEmpty() || !getAfter().isEmpty();
    }

    /**
     * Check if this scenario contains any hooks with content.
     *
     * @return true if there are hooks with content.
     */
    public boolean hasHooksWithContent() {
        return anyBeforeHookHasContent() || anyAfterHookHasContent();
    }

    /**
     * Check if this scenario contains docstrings.
     *
     * @return true if there are docstrings.
     */
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

    /**
     * Check if this scenario contains outputs.
     *
     * @return true if there are outputs.
     */
    public boolean hasOutputs() {
        for (Step step : backgroundSteps) {
            if (step.hasOutputs()) {
                return true;
            }
        }
        for (Step step : steps) {
            if (step.hasOutputs()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this scenario contains attachments.
     *
     * @return true if there are attachments.
     */
    public boolean hasAttachments() {
        for (ResultMatch resultMatch : before) {
            if (!resultMatch.getEmbeddings().isEmpty()) {
                return true;
            }
        }
        for (Step step : backgroundSteps) {
            if (step.hasEmbeddings()) {
                return true;
            }
        }
        for (Step step : steps) {
            if (step.hasEmbeddings()) {
                return true;
            }
        }
        for (ResultMatch resultMatch : after) {
            if (!resultMatch.getEmbeddings().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this scenario contains hooks.
     *
     * @return true if there are hooks.
     */
    public boolean hasStepHooks() {
        for (Step step : backgroundSteps) {
            if (!step.getBefore().isEmpty()) {
                return true;
            }
            if (!step.getAfter().isEmpty()) {
                return true;
            }
        }
        for (Step step : steps) {
            if (!step.getBefore().isEmpty()) {
                return true;
            }
            if (!step.getAfter().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this scenario contains subsections.
     *
     * @return true if there are subsections.
     */
    public boolean hasSubSections() {
        for (Step step : backgroundSteps) {
            if (step.hasSubSections()) {
                return true;
            }
        }
        for (Step step : steps) {
            if (step.hasSubSections()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if any step hooks have content.
     *
     * @return true means that there are step hooks with content.
     */
    public boolean hasStepHooksWithContent() {
        for (Step step : backgroundSteps) {
            if (step.hasHooksWithContent()) {
                return true;
            }
        }
        for (Step step : steps) {
            if (step.hasHooksWithContent()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get all results from all background steps, steps and after hooks.
     *
     * @return The results.
     */
    public List<ResultMatch> getAllResultMatches() {
        List<ResultMatch> resultMatches = new ArrayList<>(getBefore());
        resultMatches.addAll(getAllStepsIncludingBackgroundSteps());
        resultMatches.addAll(getAfter());
        return resultMatches;
    }

    /**
     * Get the scenario's parent feature name.
     *
     * @return The feature name.
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Set the scenario's parent feature name.
     *
     * @param featureName The feature name.
     */
    public void setFeatureName(final String featureName) {
        this.featureName = featureName;
    }

    /**
     * Get the internal feature index.
     *
     * @return The feature index.
     */
    public int getFeatureIndex() {
        return featureIndex;
    }

    /**
     * Set the internal feature index.
     *
     * @param featureIndex The feature index.
     */
    public void setFeatureIndex(final int featureIndex) {
        this.featureIndex = featureIndex;
    }

    /**
     * Choose if a scenario should be failed on pending or undefined steps.
     *
     * @param failOnPendingOrUndefined If true, the scenario is failed on pending or undefined steps.
     */
    public void setFailOnPendingOrUndefined(final boolean failOnPendingOrUndefined) {
        this.failOnPendingOrUndefined = failOnPendingOrUndefined;
    }

    /**
     * Get the URI of the parent feature file.
     *
     * @return The feature file URI.
     */
    public String getFeatureUri() {
        return featureUri;
    }

    /**
     * Get the scenario's id.
     *
     * @return The string id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the scenario's id.
     *
     * @param id The string id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Check if this scenario is the last of multiple runs.
     *
     * @return true if this scenario is the last of multiple runs.
     */
    public boolean isMultiRunParent() {
        return isMultiRunParent;
    }

    /**
     * Set to true if this scenario is the last of multiple runs.
     *
     * @param isMultiRunParent true if this scenario is the last of multiple runs.
     */
    public void setMultiRunParent(final boolean isMultiRunParent) {
        this.isMultiRunParent = isMultiRunParent;
    }

    /**
     * Check if this scenario was run multiple times and it's not the last run.
     *
     * @return true if this scenario was run multiple times and it's not the last run.
     */
    public boolean isMultiRunChild() {
        return isMultiRunChild;
    }

    /**
     * Set to true if this scenario was run multiple times, but it's not the last run.
     *
     * @param isMultiRunChild true if this scenario was run multiple times and it's not the last run.
     */
    public void isMultiRunChild(final boolean isMultiRunChild) {
        this.isMultiRunChild = isMultiRunChild;
    }

    /**
     * Get the children elements of this scenario.
     *
     * @return The children elements.
     */
    public List<Element> getMultiRunChildren() {
        return multiRunChildren;
    }

    /**
     * Set the children elements of this scenario.
     *
     * @param multiRunChildren The children elements.
     */
    public void setMultiRunChildren(final List<Element> multiRunChildren) {
        this.multiRunChildren = multiRunChildren;
    }

    /**
     * Check if this scenario is part of a multi-run.
     *
     * @return true if this scenario is part of a multi-run.
     */
    public boolean isPartOfMultiRun() {
        return isMultiRunParent || isMultiRunChild;
    }

    /**
     * Set the URI of the parent feature file.
     *
     * @param featureUri The feature file URI.
     */
    public void setFeatureUri(String featureUri) {
        this.featureUri = featureUri;
    }

    /**
     * Get all steps including background steps.
     *
     * @return The list of steps.
     */
    public List<Step> getAllStepsIncludingBackgroundSteps() {
        List<Step> combinedSteps = new ArrayList<>(backgroundSteps);
        combinedSteps.addAll(steps);
        return combinedSteps;
    }
}
