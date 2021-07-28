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
import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step extends ResultMatch {
    private List<ResultMatch> before = new ArrayList<>();
    private int line;
    private String name = "";
    private String keyword = "";
    private List<Row> rows = new ArrayList<>();
    private List<ResultMatch> after = new ArrayList<>();
    @SerializedName("doc_string")
    private DocString docString;

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

    public String returnNameWithArgumentPlaceholders() {
        String tmpName = getName();
        List<Argument> arguments = getArguments();
        for (int i = arguments.size() - 1; i >= 0; i--) {
            String argument = arguments.get(i).getVal();
            if (argument != null) {
                tmpName = tmpName.replaceFirst(Pattern.quote(argument), Matcher.quoteReplacement("{}"));
            }
        }
        return tmpName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(final List<Row> rows) {
        this.rows = rows;
    }

    public List<ResultMatch> getAfter() {
        return after;
    }

    public void setAfter(final List<ResultMatch> after) {
        this.after = after;
    }

    public DocString getDocString() {
        return docString;
    }

    public void setDocString(final DocString docString) {
        this.docString = docString;
    }

    public long getTotalDuration() {
        long totalDurationNanoseconds =
                before.stream().mapToLong(beforeStep -> beforeStep.getResult().getDuration()).sum();
        totalDurationNanoseconds += getResult().getDuration();
        totalDurationNanoseconds += after.stream().mapToLong(afterStep -> afterStep.getResult().getDuration()).sum();
        return totalDurationNanoseconds;
    }

    public String returnTotalDurationString() {
        return RenderingUtils.convertNanosecondsToTimeString(getTotalDuration());
    }

    public String getUrlFriendlyName() {
        return Integer.toString(hashCode()).replace("-", "0");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Step step = (Step) o;
        return Objects.equals(getGlueMethodName(), step.getGlueMethodName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGlueMethodName());
    }
}
