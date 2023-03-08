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
 * Chart axis.
 */
public class Axis {
    private Ticks ticks;
    private boolean stacked;
    private ScaleLabel scaleLabel;
    private float stepSize;

    /**
     * Get the ticks on this chart axis.
     * @return The ticks of the axis.
     */
    public Ticks getTicks() {
        return ticks;
    }

    /**
     * Set the ticks on this chart axis.
     * @param ticks The ticks of the axis.
     */
    public void setTicks(final Ticks ticks) {
        this.ticks = ticks;
    }

    /**
     * Determine if this axis is stacked.
     * @return true if this axis is stacked.
     */
    public boolean isStacked() {
        return stacked;
    }

    /**
     * Set if this axis is stacked.
     * @param stacked true means the axis is stacked.
     */
    public void setStacked(final boolean stacked) {
        this.stacked = stacked;
    }

    public ScaleLabel getScaleLabel() {
        return scaleLabel;
    }

    public void setScaleLabel(final ScaleLabel scaleLabel) {
        this.scaleLabel = scaleLabel;
    }

    public float getStepSize() {
        return stepSize;
    }

    public void setStepSize(final float stepSize) {
        this.stepSize = stepSize;
    }
}
