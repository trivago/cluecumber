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
package com.trivago.cluecumber.engine.rendering.pages.pojos;

/**
 * The aggregated results.
 */
public class ResultCount {
    private int passed;
    private int failed;
    private int skipped;

    /**
     * Default constructor resetting all counts.
     */
    public ResultCount() {
        this.passed = 0;
        this.failed = 0;
        this.skipped = 0;
    }

    /**
     * Add an amount of passed entries to the overall results.
     *
     * @param passedCount The number of passed entries to add.
     */
    public void addPassed(int passedCount) {
        this.passed += passedCount;
    }

    /**
     * Add an amount of failed entries to the overall results.
     *
     * @param failedCount The number of failed entries to add.
     */
    public void addFailed(int failedCount) {
        this.failed += failedCount;
    }

    /**
     * Add an amount of skipped entries to the overall results.
     *
     * @param skippedCount The number of skipped entries to add.
     */
    public void addSkipped(int skippedCount) {
        this.skipped += skippedCount;
    }

    /**
     * Get the total amount of passed entries.
     *
     * @return The count.
     */
    public int getPassed() {
        return passed;
    }

    /**
     * Get the total amount of passed entries.
     *
     * @return The count.
     */
    public int getFailed() {
        return failed;
    }

    /**
     * Get the total amount of passed entries.
     *
     * @return The count.
     */
    public int getSkipped() {
        return skipped;
    }

    /**
     * Get the total amount of all entries.
     *
     * @return The count.
     */
    public int getTotal() {
        return passed + failed + skipped;
    }
}
