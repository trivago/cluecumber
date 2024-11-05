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
 * The chart data to be displayed.
 */
@SuppressWarnings("unused")
public class Data {
    private List<String> labels;
    private List<Dataset> datasets;

    /**
     * Default constructor.
     */
    public Data() {
        // Default constructor
    }

    /**
     * Get the labels for the data sets.
     *
     * @return The list of labels.
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * Set the labels for the data sets.
     *
     * @param labels The list of labels.
     */
    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    /**
     * Get the data sets.
     *
     * @return The list of {@link Dataset} instances.
     */
    public List<Dataset> getDatasets() {
        return datasets;
    }

    /**
     * Set the data sets.
     *
     * @param datasets The list of {@link Dataset} instances.
     */
    public void setDatasets(final List<Dataset> datasets) {
        this.datasets = datasets;
    }
}
