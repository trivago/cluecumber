/*
 * Copyright 2017 trivago N.V.
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Element {
    private List<Before> before = new ArrayList<>();
    private int line;
    private String name;
    private String description;
    private String id;
    private List<After> after = new ArrayList<>();
    private String type;
    private String keyword;
    private List<Step> steps = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    private transient int scenarioIndex;
    private transient String durationChartJson = "";

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    public List<Before> getBefore() {
        return before;
    }

    public void setBefore(final List<Before> before) {
        this.before = before;
    }

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public String getName() {
        return name;
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

    public List<After> getAfter() {
        return after;
    }

    public void setAfter(final List<After> after) {
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

        for (Status status : Status.values()) {
            int stepNumber = (int) steps.stream().filter(step -> step.getStatus() == status).count();
            if (totalSteps == stepNumber) {
                if (status != Status.UNDEFINED) {
                    return status;
                } else {
                    return Status.SKIPPED;
                }
            }
        }
        return Status.FAILED;
    }

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public void setScenarioIndex(final int scenarioIndex) {
        this.scenarioIndex = scenarioIndex;
    }

    public long getTotalDuration() {
        long totalDurationMicroseconds = 0;
        for (Step step : steps) {
            totalDurationMicroseconds += step.getResult().getDuration();
        }
        return totalDurationMicroseconds;
    }

    public String getTotalDurationString() {
        final int microsecondFactor = 1000000;
        Duration durationMilliseconds = Duration.ofMillis(getTotalDuration() / microsecondFactor);

        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return String.format("%dm %02ds %03dms", minutes, seconds, milliseconds);
    }

    @Override
    public String toString() {
        return "Element{" +
                "before=" + before +
                ", line=" + line +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", after=" + after +
                ", type='" + type + '\'' +
                ", keyword='" + keyword + '\'' +
                ", steps=" + steps +
                ", tags=" + tags +
                ", scenarioIndex=" + scenarioIndex +
                '}';
    }
}
