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
 * The axes of the chart.
 */
public class Scales {
    private List<Axis> xAxes;
    private List<Axis> yAxes;

    /**
     * Get the x axes.
     *
     * @return The {@link Axis} list.
     */
    public List<Axis> getxAxes() {
        return xAxes;
    }

    /**
     * Set the x axes.
     *
     * @param xAxes The {@link Axis} list.
     */
    public void setxAxes(final List<Axis> xAxes) {
        this.xAxes = xAxes;
    }

    /**
     * Get the y axes.
     *
     * @return The {@link Axis} list.
     */
    public List<Axis> getyAxes() {
        return yAxes;
    }

    /**
     * Set the x axes.
     *
     * @param yAxes The {@link Axis} list.
     */
    public void setyAxes(final List<Axis> yAxes) {
        this.yAxes = yAxes;
    }
}
