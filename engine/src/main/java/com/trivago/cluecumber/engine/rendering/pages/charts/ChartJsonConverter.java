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
package com.trivago.cluecumber.engine.rendering.pages.charts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Helper class to convert charts to JSON data.
 */
@Singleton
public class ChartJsonConverter {

    private final Gson gson;

    /**
     * Default constructor.
     */
    @Inject
    public ChartJsonConverter() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Creates a JSON representation of the {@link Chart} instance.
     *
     * @param chart The {@link Chart} instance.
     * @return The JSON string.
     */
    public String convertChartToJson(final Chart chart) {
        return gson.toJson(chart);
    }
}