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

import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about the report generation date and Cluecumber version.
 */
public class ReportDetails {
    private final String date;
    private String chartJson;
    private Map<String, String> chartUrlLookup = new HashMap<>();

    /**
     * Constructor setting the generation date.
     */
    public ReportDetails() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        this.date = dateFormat.format(date);
    }

    /**
     * Return the chart JSON.
     *
     * @return The JSON string.
     */
    public String getChartJson() {
        return chartJson;
    }

    /**
     * Set the chart JSON to include.
     *
     * @param chartJson The chart JSON.
     */
    public void setChartJson(final String chartJson) {
        this.chartJson = chartJson;
    }

    /**
     * Get the generation date of this report.
     *
     * @return The date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Return the Cluecumber name and version.
     *
     * @return The Cluecumber name and version string.
     */
    public String getGeneratorName() {
        return String.format("%s version %s",
                Settings.NAME, RenderingUtils.getPluginVersion());
    }

    /**
     * Get the chart URL lookup for linking bars to their respective pages.
     *
     * @return The mapping of  names to urls.
     */
    public Map<String, String> getChartUrlLookup() {
        return chartUrlLookup;
    }

    /**
     * Set the chart URL lookup for linking bars to their respective pages.
     *
     * @param chartUrlLookup The mapping of names to urls.
     */
    public void setChartUrlLookup(Map<String, String> chartUrlLookup) {
        this.chartUrlLookup = chartUrlLookup;
    }
}
