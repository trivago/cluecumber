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

package com.trivago.cluecumber.rendering.pages.pojos;

public class ResultCount {
    private int passed;
    private int failed;
    private int skipped;

    public ResultCount() {
        this.passed = 0;
        this.failed = 0;
        this.skipped = 0;
    }

    public void addPassed(int passedCount) {
        this.passed += passedCount;
    }

    public void addFailed(int failedCount) {
        this.failed += failedCount;
    }

    public void addSkipped(int skippedCount) {
        this.skipped += skippedCount;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getTotal() {
        return passed + failed + skipped;
    }

    @Override
    public String toString() {
        return "ResultCount{" +
                "passed=" + passed +
                ", failed=" + failed +
                ", skipped=" + skipped +
                '}';
    }
}
