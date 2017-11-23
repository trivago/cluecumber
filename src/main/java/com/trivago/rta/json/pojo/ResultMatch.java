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

class ResultMatch {
    private Result result;
    private Match match;

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

    public Status getStatus() {
        return Status.fromString(result.getStatus());
    }

    public boolean isFailed() {
        return getStatus() == Status.FAILED;
    }

    public boolean isPassed() {
        return getStatus() == Status.PASSED;
    }

    public boolean isSkipped() {
        return getStatus() == Status.SKIPPED || getStatus() == Status.PENDING || getStatus() == Status.UNDEFINED;
    }

    @Override
    public String toString() {
        return "ResultMatch{" +
                "result=" + result +
                ", match=" + match +
                '}';
    }
}
