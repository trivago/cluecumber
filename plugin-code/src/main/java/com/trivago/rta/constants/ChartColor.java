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

package com.trivago.rta.constants;

/**
 * This enum manages the colors for the overview and detail charts that correspond to the passed in {@link Status} value.
 */
public enum ChartColor {
    PASSED(40, 167, 69), FAILED(220, 53, 69), SKIPPED(255, 193, 7);

    private static final String COLOR_FORMAT = "rgba(%d, %d, %d, 1.000)";

    private final int r;
    private final int g;
    private final int b;

    ChartColor(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Get the corresponding chart color string for the passed {@link Status}.
     *
     * @param status the {@link Status}.
     * @return the matching color string.
     */
    public static String getChartColorStringByStatus(Status status) {
        switch (status) {
            case FAILED:
                return String.format(COLOR_FORMAT, FAILED.r, FAILED.g, FAILED.b);
            case SKIPPED:
                return String.format(COLOR_FORMAT, SKIPPED.r, SKIPPED.g, SKIPPED.b);
            case PASSED:
            default:
                return String.format(COLOR_FORMAT, PASSED.r, PASSED.g, PASSED.b);
        }
    }
}
