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

import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * This class holds feature, scenario or step indeces and associated times.
 */
public class Times {
    private final List<IndexTime> times = new ArrayList<>();

    /**
     * Add a time and an index to the list of times.
     *
     * @param nanoseconds The nanoseconds.
     * @param index       The feature, scenario or step index.
     */
    public void addTime(final long nanoseconds, final int index) {
        times.add(new IndexTime(nanoseconds, index));
    }

    /**
     * Get the minimum time of the list.
     *
     * @return The {@link IndexTime} instance with the minimum time.
     */
    private IndexTime getMinimumTime() {
        Optional<IndexTime> time = times.stream().min(Comparator.comparingLong(t -> t.time));
        return time.orElseGet(() -> new IndexTime(0, -1));
    }

    /**
     * Get the maximum time of the list.
     *
     * @return The {@link IndexTime} instance with the maximum time.
     */
    private IndexTime getMaximumTime() {
        Optional<IndexTime> time = times.stream().max(Comparator.comparingLong(t -> t.time));
        return time.orElseGet(() -> new IndexTime(0, -1));
    }

    /**
     * Get the human-readable time string for the minimum time.
     *
     * @return The time string.
     */
    public String getMinimumTimeString() {
        return RenderingUtils.convertNanosecondsToTimeString(getMinimumTime().time);
    }

    /**
     * Get the index for the minimum time.
     *
     * @return The feature, scenario or step index.
     */
    public int getMinimumTimeIndex() {
        return getMinimumTime().index;
    }

    /**
     * Get the human-readable time string for the maximum time.
     *
     * @return The time string.
     */
    public String getMaximumTimeString() {
        return RenderingUtils.convertNanosecondsToTimeString(getMaximumTime().time);
    }

    /**
     * Get the index for the maximum time.
     *
     * @return The feature, scenario or step index.
     */
    public int getMaximumTimeIndex() {
        return getMaximumTime().index;
    }

    /**
     * Get the average time of all recorded times.
     *
     * @return The time string.
     */
    public String getAverageTimeString() {
        return RenderingUtils.convertNanosecondsToTimeString(
                (long) times.stream().mapToLong(v -> v.time).average().orElse(0)
        );
    }

    static class IndexTime {
        private final long time;
        private final int index;

        IndexTime(final long time, final int index) {
            this.time = time;
            this.index = index;
        }
    }
}

