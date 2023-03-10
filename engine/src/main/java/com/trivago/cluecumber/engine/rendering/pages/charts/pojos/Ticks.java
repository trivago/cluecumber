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

/**
 * Class to define the ticks on chart axes.
 */
public class Ticks {
    private int min;
    private float stepSize;
    private boolean display = true;

    /**
     * Get the minimum tick.
     *
     * @return The value.
     */
    public int getMin() {
        return min;
    }

    /**
     * Set the minimum tick.
     *
     * @param min The value.
     */
    public void setMin(final int min) {
        this.min = min;
    }

    /**
     * Check if ticks should be displayed.
     *
     * @return true if ticks should be displayed.
     */
    public boolean isDisplay() {
        return display;
    }

    /**
     * Set if ticks should be displayed.
     *
     * @param display true if ticks should be displayed.
     */
    public void setDisplay(final boolean display) {
        this.display = display;
    }

    /**
     * Get the tick step size.
     *
     * @return The step size.
     */
    public float getStepSize() {
        return stepSize;
    }

    /**
     * Set the tick step size.
     *
     * @param stepSize The step size.
     */
    public void setStepSize(final float stepSize) {
        this.stepSize = stepSize;
    }
}
