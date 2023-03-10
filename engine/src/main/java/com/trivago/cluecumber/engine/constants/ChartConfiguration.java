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

package com.trivago.cluecumber.engine.constants;


import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This stores the colors and type of a report chart.
 */
@Singleton
public class ChartConfiguration {

    private final PropertyManager propertyManager;
    private String passedColorRgbaString;
    private String failedColorRgbaString;
    private String skippedColorRgbaString;

    /**
     * Constructor for dependency injection.
     *
     * @param propertyManager The {@link PropertyManager} instance.
     */
    @Inject
    public ChartConfiguration(final PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    /**
     * Get the RGBA color string by status.
     *
     * @param status The status to get the color for.
     * @return The RGBA string.
     */
    public String getColorRgbaStringByStatus(final Status status) {
        switch (status) {
            case FAILED:
                return getFailedColorRgbaString();
            case SKIPPED:
                return getSkippedColorRgbaString();
            default:
                return getPassedColorRgbaString();
        }
    }

    /**
     * Get the RGBA color string for passed.
     *
     * @return The RGBA color.
     */
    public String getPassedColorRgbaString() {
        if (passedColorRgbaString == null) {
            passedColorRgbaString = getRgbaColorStringFromHex(propertyManager.getCustomStatusColorPassed());
        }
        return passedColorRgbaString;
    }

    /**
     * Get the RGBA color string for failed.
     *
     * @return The RGBA color.
     */
    public String getFailedColorRgbaString() {
        if (failedColorRgbaString == null) {
            failedColorRgbaString = getRgbaColorStringFromHex(propertyManager.getCustomStatusColorFailed());
        }
        return failedColorRgbaString;
    }

    /**
     * Get the RGBA color string for skipped.
     *
     * @return The RGBA color.
     */
    public String getSkippedColorRgbaString() {
        if (skippedColorRgbaString == null) {
            skippedColorRgbaString = getRgbaColorStringFromHex(propertyManager.getCustomStatusColorSkipped());
        }
        return skippedColorRgbaString;
    }

    private String getRgbaColorStringFromHex(final String hexColorString) {
        if (hexColorString == null || hexColorString.isEmpty()) {
            return "";
        }
        String hex = hexColorString;
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }
        final int[] rgbEntries = new int[3];
        rgbEntries[0] = Integer.parseInt(hex.substring(0, 2), 16);
        rgbEntries[1] = Integer.parseInt(hex.substring(2, 4), 16);
        rgbEntries[2] = Integer.parseInt(hex.substring(4, 6), 16);
        return String.format("rgba(%d, %d, %d, 1.000)", rgbEntries[0], rgbEntries[1], rgbEntries[2]);
    }

    /**
     * The type of chart.
     */
    public enum Type {
        /**
         * Bar chart
         */
        bar,

        /**
         * Pie chart.
         */
        pie
    }
}
