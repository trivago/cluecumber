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

package com.trivago.cluecumber.rendering.pages.pojos;

import com.trivago.cluecumber.rendering.RenderingUtils;

import java.util.ArrayList;
import java.util.List;

public class Times {
    private List<Long> times = new ArrayList<>();

    public void addTime(final long nanoseconds) {
        times.add(nanoseconds);
    }

    public String getMinimumTime() {
        return RenderingUtils.convertNanosecondsToTimeString(times.stream().mapToLong(v -> v).min().orElse(0));
    }

    public String getMaximumTime() {
        return RenderingUtils.convertNanosecondsToTimeString(times.stream().mapToLong(v -> v).max().orElse(0));
    }

    public String getAverageTime() {
        return RenderingUtils.convertNanosecondsToTimeString(
                (long) times.stream().mapToLong(v -> v).average().orElse(0)
        );
    }
}
