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

import com.trivago.cluecumber.constants.Status;

import java.util.ArrayList;
import java.util.List;

public class ResultMatch {
    private Result result;
    private Match match;

    private List<String> output = new ArrayList<>();
    private List<Embedding> embeddings = new ArrayList<>();

    public Result getResult() {
        return result != null ? result : new Result();
    }

    public void setResult(final Result result) {
        this.result = result;
    }

    public Match getMatch() {
        return match != null ? match : new Match();
    }

    public void setMatch(final Match match) {
        this.match = match;
    }

    public List<Embedding> getEmbeddings() {
        return embeddings;
    }

    public void setEmbeddings(final List<Embedding> embeddings) {
        this.embeddings = embeddings;
    }

    public boolean hasEmbeddings() {
        return embeddings.size() > 0;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(final List<String> output) {
        this.output = output;
    }

    public boolean hasOutputs() {
        return output.size() > 0;
    }

    public List<String> returnEscapedOutputs() {
        return output;
    }

    public String getGlueMethodName() {
        return getMatch().getLocation();
    }

    public List<Argument> getArguments() {
        return getMatch().getArguments();
    }

    public Status getStatus() {
        return Status.fromString(getResult().getStatus());
    }

    public String getStatusString() {
        return getStatus().getStatusString();
    }

    public boolean isFailed() {
        return getStatus() == Status.FAILED;
    }

    public boolean isPassed() {
        return getStatus() == Status.PASSED;
    }

    public boolean isSkipped() {
        return getConsolidatedStatus() == Status.SKIPPED;
    }

    public boolean hasContent() {
        return hasOutputs() || hasEmbeddings();
    }

    public Status getConsolidatedStatus() {
        switch (getStatus()) {
            case PASSED:
                return Status.PASSED;
            case SKIPPED:
            case PENDING:
            case AMBIGUOUS:
            case UNDEFINED:
                return Status.SKIPPED;
            case FAILED:
            default:
                return Status.FAILED;
        }
    }

    public String getConsolidatedStatusString() {
        return getConsolidatedStatus().getStatusString();
    }
}
