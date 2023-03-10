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

import java.util.List;


/**
 * Chart options.
 */
public class Options {
    private Scales scales;
    private Legend legend;
    private List<String> events;

    /**
     * Get the scales.
     *
     * @return The {@link Scales} instance.
     */
    public Scales getScales() {
        return scales;
    }

    /**
     * Set the scales.
     *
     * @param scales The {@link Scales} instance.
     */
    public void setScales(final Scales scales) {
        this.scales = scales;
    }

    /**
     * Get the legend.
     *
     * @return The {@link Legend} instance.
     */
    public Legend getLegend() {
        return legend;
    }

    /**
     * Set the legend.
     *
     * @param legend The {@link Legend} instance.
     */
    public void setLegend(final Legend legend) {
        this.legend = legend;
    }

    /**
     * Get the supported javascript events.
     *
     * @return The list of strings representing events.
     */
    public List<String> getEvents() {
        return events;
    }

    /**
     * Set the supported javascript events.
     *
     * @param events The list of strings representing events.
     */
    public void setEvents(final List<String> events) {
        this.events = events;
    }
}
