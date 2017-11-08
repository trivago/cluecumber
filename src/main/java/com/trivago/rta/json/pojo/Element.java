package com.trivago.rta.json.pojo;

import com.trivago.rta.constants.ScenarioStatus;

import java.time.Duration;
import java.util.List;

public class Element {
    private List<Before> before;
    private int line;
    private String name;
    private String description;
    private String id;
    private List<After> after;
    private String type;
    private String keyword;
    private List<Step> steps;
    private List<Tag> tags;

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

    public ScenarioStatus getStatus() {
        int totalSteps = steps.size();
        int skippedSteps = (int) steps.stream().filter(step -> step.getResult().getStatus().equals(ScenarioStatus.SKIPPED.getStatus())).count();
        int passedSteps = (int) steps.stream().filter(step -> step.getResult().getStatus().equals(ScenarioStatus.PASSED.getStatus())).count();

        if (skippedSteps == totalSteps) {
            return ScenarioStatus.SKIPPED;
        } else if (passedSteps == totalSteps) {
            return ScenarioStatus.PASSED;
        } else {
            return ScenarioStatus.FAILED;
        }
    }

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public void setScenarioIndex(final int scenarioIndex) {
        this.scenarioIndex = scenarioIndex;
    }

    public String getTotalDurationString() {
        long totalDurationMillis = 0;
        for (Step step : steps) {
            totalDurationMillis += step.getResult().getDuration();
        }

        final int microsecondFactor = 1000000;
        Duration durationMilliseconds = Duration.ofMillis(totalDurationMillis / microsecondFactor);

        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return String.format("%dm %02ds %03d", minutes, seconds, milliseconds);
    }

    public String getDurationChartJson() {
        return durationChartJson;
    }

    public void setDurationChartJson(final String durationChartJson) {
        this.durationChartJson = durationChartJson;
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
