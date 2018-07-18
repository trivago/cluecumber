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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step extends ResultMatch {
    private List<Before> before;
    private int line;
    private String name = "";
    private String keyword = "";
    private List<Row> rows = new ArrayList<>();
    private List<After> after;

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
        return !name.isEmpty() ? name : "[Unnamed]";
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String returnNameWithArguments() {
        String tmpName = name;
        List<Argument> arguments = getArguments();
        for (int i = arguments.size() - 1; i >= 0; i--) {
            String argument = arguments.get(i).getVal();
            tmpName = tmpName.replaceFirst(Pattern.quote(argument), Matcher.quoteReplacement("<strong>" + argument + "</strong>"));
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

    public List<After> getAfter() {
        return after;
    }

    public void setAfter(final List<After> after) {
        this.after = after;
    }

    @Override
    public String toString() {
        return "Step{" +
                "before=" + before +
                ", line=" + line +
                ", name='" + name + '\'' +
                ", keyword='" + keyword + '\'' +
                ", rows=" + rows +
                ", after=" + after +
                '}';
    }
}
