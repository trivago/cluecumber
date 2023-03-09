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
package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;

import javax.inject.Inject;


/**
 * The renderer for pages with charts.
 */
public class PageWithChartRenderer extends PageRenderer {

    private final ChartJsonConverter chartJsonConverter;

    /**
     * Constructor for dependency injection.
     *
     * @param chartJsonConverter The {@link ChartJsonConverter} instance.
     */
    @Inject
    public PageWithChartRenderer(final ChartJsonConverter chartJsonConverter) {
        this.chartJsonConverter = chartJsonConverter;
    }

    /**
     * Convert a chart object to a JSON string representation.
     *
     * @param chart The {@link Chart} instance.
     * @return The JSON string.
     */
    String convertChartToJson(final Chart chart) {
        return chartJsonConverter.convertChartToJson(chart);
    }
}
