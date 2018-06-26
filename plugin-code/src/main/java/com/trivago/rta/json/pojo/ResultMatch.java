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

import java.util.ArrayList;
import java.util.List;

class ResultMatch {
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

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(final List<String> output) {
        this.output = output;
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
        return getStatus() == Status.SKIPPED ||
                getStatus() == Status.PENDING ||
                getStatus() == Status.UNDEFINED ||
                getStatus() == Status.AMBIGUOUS;
    }

    @Override
    public String toString() {
        return "ResultMatch{" +
                "result=" + result +
                ", match=" + match +
                ", output=" + output +
                ", embeddings=" + embeddings +
                '}';
    }
}
