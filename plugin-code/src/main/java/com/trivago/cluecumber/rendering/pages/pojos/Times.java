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

import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Times {
    private final List<FeatureTime> times = new ArrayList<>();

    public void addTime(final long nanoseconds, final int featureIndex) {
        times.add(new FeatureTime(nanoseconds, featureIndex));
    }

    private FeatureTime getMinimumFeatureTime() {
        Optional<FeatureTime> featureTime = times.stream().min(Comparator.comparingLong(ft -> ft.time));
        return featureTime.orElseGet(() -> new FeatureTime(0, -1));
    }

    private FeatureTime getMaximumFeatureTime() {
        Optional<FeatureTime> featureTime = times.stream().max(Comparator.comparingLong(ft -> ft.time));
        return featureTime.orElseGet(() -> new FeatureTime(0, -1));
    }

    public String getMinimumTimeString() {
        return RenderingUtils.convertNanosecondsToTimeString(getMinimumFeatureTime().time);
    }

    public int getMinimumTimeScenarioIndex() {
        return getMinimumFeatureTime().scenarioIndex;
    }

    public String getMaximumTimeString() {
        return RenderingUtils.convertNanosecondsToTimeString(getMaximumFeatureTime().time);
    }

    public int getMaximumTimeScenarioIndex() {
        return getMaximumFeatureTime().scenarioIndex;
    }

    public String getAverageTimeString() {
        return RenderingUtils.convertNanosecondsToTimeString(
                (long) times.stream().mapToLong(v -> v.time).average().orElse(0)
        );
    }

    static class FeatureTime {
        private final long time;
        private final int scenarioIndex;

        FeatureTime(final long time, final int scenarioIndex) {
            this.time = time;
            this.scenarioIndex = scenarioIndex;
        }
    }
}

