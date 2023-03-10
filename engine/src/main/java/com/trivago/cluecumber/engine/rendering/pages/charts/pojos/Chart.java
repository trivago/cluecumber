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
package com.trivago.cluecumber.engine.rendering.pages.charts.pojos;

import com.trivago.cluecumber.engine.constants.ChartConfiguration;

/**
 * The base class for all charts.
 */
public class Chart {

    private Data data;
    private Options options;
    private ChartConfiguration.Type type;

    /**
     * Get the chart data.
     *
     * @return The {@link Data} instance.
     */
    public Data getData() {
        return data;
    }

    /**
     * Set the chart data.
     *
     * @param data The {@link Data} instance.
     */
    public void setData(final Data data) {
        this.data = data;
    }


    /**
     * Get the chart type.
     *
     * @return The chart type.
     */
    public ChartConfiguration.Type getType() {
        return type;
    }

    /**
     * Set the chart type.
     *
     * @param type The chart type.
     */
    public void setType(final ChartConfiguration.Type type) {
        this.type = type;
    }

    /**
     * Get the chart configuration options.
     *
     * @return The {@link Options} instance.
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Set the chart configuration options.
     *
     * @param options The {@link Options} instance.
     */
    public void setOptions(final Options options) {
        this.options = options;
    }
}
