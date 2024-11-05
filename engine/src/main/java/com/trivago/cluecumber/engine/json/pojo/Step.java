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
import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Cucumber step.
 */
public class Step extends ResultMatch {

    private List<ResultMatch> before = new ArrayList<>();
    private int line;
    private String name = "";
    private String keyword = "";
    private List<Row> rows = new ArrayList<>();
    private List<ResultMatch> after = new ArrayList<>();
    @SerializedName("doc_string")
    private DocString docString;
    private int collapseLevel = 0;
    private int index = 0;
    private boolean hasSubSections = false;

    private static final Map<String, String> stepMatchToNameWithArgumentPlaceholders = new HashMap<>();

    /**
     * Default constructor.
     */
    public Step() {
        // Default constructor
    }

    /**
     * Check if there are before or after step hooks with content.
     *
     * @return true if step hooks with content exist.
     */
    public boolean hasHooksWithContent() {
        for (ResultMatch resultMatch : before) {
            if (resultMatch.hasContent()) {
                return true;
            }
        }
        for (ResultMatch resultMatch : after) {
            if (resultMatch.hasContent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the step has hooks.
     *
     * @return true if the step has hooks.
     */
    public boolean hasHooks() {
        return !before.isEmpty() || !after.isEmpty();
    }

    /**
     * Get the before hooks.
     *
     * @return The list of {@link ResultMatch} instances.
     */
    public List<ResultMatch> getBefore() {
        return before;
    }

    /**
     * Set the before hooks.
     *
     * @param before The list of {@link ResultMatch} instances.
     */
    public void setBefore(final List<ResultMatch> before) {
        this.before = before;
    }

    /**
     * Get the line number where this step is located.
     *
     * @return The line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Set the line number where this step is located.
     *
     * @param line The line number.
     */
    public void setLine(final int line) {
        this.line = line;
    }

    /**
     * Get the step name.
     *
     * @return The name.
     */
    public String getName() {
        return !name.isEmpty() ? name : "[Unnamed]";
    }

    /**
     * Set the step name.
     *
     * @param name The name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get an HTML representation of the step name with highlighted argument values.
     *
     * @return The step name with highlighted arguments.
     */
    public String returnNameWithArguments() {
        String tmpName = getName();
        List<Argument> arguments = getArguments();
        for (int i = arguments.size() - 1; i >= 0; i--) {
            String argument = arguments.get(i).getVal();
            if (argument != null) {
                tmpName = tmpName.replaceFirst(
                        Pattern.quote(argument),
                        Matcher.quoteReplacement("<span class=\"parameter\">" + argument + "</span>")
                );
            }
        }
        return tmpName;
    }

    /**
     * Get the step name with curly braces instead of concrete arguments.
     * This is used for the step overview and scenarios by step page.
     *
     * @return The scenario name with empty arguments.
     */
    public String returnNameWithArgumentPlaceholders() {
        return stepMatchToNameWithArgumentPlaceholders.computeIfAbsent(getMatch().getLocation(), matchName -> {
            String tmpName = getName();
            List<Argument> arguments = getArguments();
            for (int i = arguments.size() - 1; i >= 0; i--) {
                String argument = arguments.get(i).getVal();
                if (argument != null) {
                    tmpName = tmpName.replaceFirst(Pattern.quote(argument), Matcher.quoteReplacement("{}"));
                }
            }
            return tmpName;
        });
    }

    /**
     * Get the step keyword (Given, When, Then etc.).
     *
     * @return The keyword.
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Set the step keyword (Given, When, Then etc.).
     *
     * @param keyword The keyword.
     */
    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    /**
     * Get the data table rows.
     *
     * @return The list of {@link Row} instances.
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     * Set the data table rows.
     *
     * @param rows The list of {@link Row} instances.
     */
    public void setRows(final List<Row> rows) {
        this.rows = rows;
    }

    /**
     * Get the after hooks.
     *
     * @return The list of {@link ResultMatch} instances.
     */
    public List<ResultMatch> getAfter() {
        return after;
    }

    /**
     * Set the after hooks.
     *
     * @param after The list of {@link ResultMatch} instances.
     */
    public void setAfter(final List<ResultMatch> after) {
        this.after = after;
    }

    /**
     * Get the step's doc string.
     *
     * @return The {@link DocString} instance.
     */
    public DocString getDocString() {
        return docString;
    }

    /**
     * Set the step's doc string.
     *
     * @param docString The {@link DocString} instance.
     */
    public void setDocString(final DocString docString) {
        this.docString = docString;
    }

    /**
     * Get the step's total duration.
     *
     * @return The duration in nanoseconds.
     */
    public long getTotalDuration() {
        long totalDurationNanoseconds =
                before.stream().mapToLong(beforeStep -> beforeStep.getResult().getDuration()).sum();
        totalDurationNanoseconds += getResult().getDuration();
        totalDurationNanoseconds += after.stream().mapToLong(afterStep -> afterStep.getResult().getDuration()).sum();
        return totalDurationNanoseconds;
    }

    /**
     * Get the step's total duration as a human-readable string.
     *
     * @return The duration string.
     */
    public String returnTotalDurationString() {
        return RenderingUtils.convertNanosecondsToTimeString(getTotalDuration());
    }

    /**
     * Get the hash of this step in a format that can be used within a URL.
     *
     * @return The URL friendly step hash.
     */
    public String getUrlFriendlyName() {
        return Integer.toString(hashCode()).replace("-", "0");
    }

    /**
     * Comparison function for steps.
     *
     * @param o The step to compare.
     * @return true if the method names match.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Step step = (Step) o;

        return Objects.equals(getUniqueName(), step.getUniqueName());
    }

    /**
     * Return the step hash.
     *
     * @return The hash code based on the step method name.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUniqueName());
    }

    /**
     * Get the collapse level of the step.
     *
     * @return The collapse level of the step.
     */
    public int getCollapseLevel() {
        return collapseLevel;
    }

    /**
     * Set the collapse level of the step.
     *
     * @param collapseLevel The collapse level of the step.
     */
    public void setCollapseLevel(int collapseLevel) {
        this.collapseLevel = collapseLevel;
    }

    /**
     * Get the internal index of the step.
     *
     * @return The index of the step.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the internal index of the step.
     *
     * @param index The index of the step.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Check if the step has sub sections.
     *
     * @return true if the step has sub sections.
     */
    public boolean hasSubSections() {
        return hasSubSections;
    }

    /**
     * Set if the step has subsections.
     *
     * @param hasSubSections true if the step has sub sections.
     */
    public void setHasSubSections(boolean hasSubSections) {
        this.hasSubSections = hasSubSections;
    }

    /**
     * Get the unique name of the step.
     *
     * @return The unique name.
     */
    public String getUniqueName() {
        return getGlueMethodName() + "_" + returnNameWithArgumentPlaceholders();
    }
}
