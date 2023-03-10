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
 * The class to hold chart data.
 */
public class Dataset {
    private List<Float> data;
    private List<String> backgroundColor;
    private String label;
    private String stack;

    /**
     * Get the values of this dataset.
     *
     * @return The value list.
     */
    public List<Float> getData() {
        return data;
    }

    /**
     * Set the values of this dataset.
     *
     * @param data The value list.
     */
    public void setData(final List<Float> data) {
        this.data = data;
    }

    /**
     * Get the background colors for each data point.
     *
     * @return The list of color strings.
     */
    public List<String> getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Set the background colors for each data point.
     *
     * @param backgroundColor The list of color strings.
     */
    public void setBackgroundColor(final List<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Get the label of this dataset.
     *
     * @return The label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label of this dataset.
     *
     * @param label The label.
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Get the current stack.
     *
     * @return The stack string.
     */
    public String getStack() {
        return stack;
    }

    /**
     * Set the current stack.
     *
     * @param stack The stack string.
     */
    public void setStack(final String stack) {
        this.stack = stack;
    }
}
