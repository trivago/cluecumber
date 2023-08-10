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

import com.trivago.cluecumber.engine.constants.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * The result collection that is used for steps, hooks, scenarios and features.
 */
public class ResultMatch {
    private Result result;
    private Match match;

    private List<String> output = new ArrayList<>();
    private List<Embedding> embeddings = new ArrayList<>();

    /**
     * Get the result.
     *
     * @return The {@link Result} instance.
     */
    public Result getResult() {
        return result != null ? result : new Result();
    }

    /**
     * Set the result.
     *
     * @param result The {@link Result} instance.
     */
    public void setResult(final Result result) {
        this.result = result;
    }

    /**
     * Get the match.
     *
     * @return The {@link Match} instance.
     */
    public Match getMatch() {
        return match != null ? match : new Match();
    }

    /**
     * Set the match.
     *
     * @param match The {@link Match} instance.
     */
    public void setMatch(final Match match) {
        this.match = match;
    }


    /**
     * Get the attachments.
     *
     * @return The {@link Embedding} list.
     */
    public List<Embedding> getEmbeddings() {
        return embeddings;
    }

    /**
     * Set the attachments.
     *
     * @param embeddings The {@link Embedding} list.
     */
    public void setEmbeddings(final List<Embedding> embeddings) {
        this.embeddings = embeddings;
    }


    /**
     * Check if there are attachments.
     *
     * @return true if attachments exist.
     */
    public boolean hasEmbeddings() {
        return embeddings.size() > 0;
    }

    /**
     * Get the output strings.
     *
     * @return The list of attached strings.
     */
    public List<String> getOutput() {
        return output;
    }

    /**
     * Set the output strings.
     *
     * @param output The list of attached strings.
     */
    public void setOutput(final List<String> output) {
        this.output = output;
    }

    /**
     * Check if outputs exist.
     *
     * @return true if outputs exist.
     */
    public boolean hasOutputs() {
        return output.size() > 0;
    }

    /**
     * Get the name of the glue method.
     *
     * @return The method name.
     */
    public String getGlueMethodName() {
        return getMatch().getLocation();
    }

    /**
     * Get the arguments of the glue method.
     *
     * @return The {@link Argument} list.
     */
    public List<Argument> getArguments() {
        return getMatch().getArguments();
    }


    /**
     * Get the status.
     *
     * @return The {@link Status} enum.
     */
    public Status getStatus() {
        return Status.fromString(getResult().getStatus());
    }

    /**
     * Get the string value of the status.
     *
     * @return The status string.
     */
    public String getStatusString() {
        return getStatus().getStatusString();
    }

    /**
     * Check if the status is failed.
     *
     * @return true if it is failed.
     */
    public boolean isFailed() {
        return getStatus() == Status.FAILED;
    }

    /**
     * Check if the status is passed.
     *
     * @return true if it is passed.
     */
    public boolean isPassed() {
        return getStatus() == Status.PASSED;
    }

    /**
     * Check if the status is skipped.
     *
     * @return true if it is skipped.
     */
    public boolean isSkipped() {
        return getConsolidatedStatus() == Status.SKIPPED;
    }

    /**
     * Check if there are outputs or attachments.
     *
     * @return true if there is content.
     */
    public boolean hasContent() {
        return hasOutputs() || hasEmbeddings();
    }

    /**
     * Get the basic status (passed, failed, skipped).
     *
     * @return The basic {@link Status} enum.
     */
    public Status getConsolidatedStatus() {
        return getStatus().basicStatus();
   }

    /**
     * Get the string of the basic status.
     *
     * @return The basic status string representation.
     */
    public String getConsolidatedStatusString() {
        return getConsolidatedStatus().getStatusString();
    }
}
