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

package com.trivago.cluecumber.rendering.pages.charts.pojos;

public class Ticks {
    private int min;
    private int stepSize;
    private boolean display = true;

    public int getMin() {
        return min;
    }

    public void setMin(final int min) {
        this.min = min;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(final boolean display) {
        this.display = display;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(final int stepSize) {
        this.stepSize = stepSize;
    }
}
