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

import java.util.List;

public class Data {
    private List<String> labels;
    private List<Dataset> datasets;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(final List<Dataset> datasets) {
        this.datasets = datasets;
    }

    @Override
    public String toString() {
        return "Data{" +
                "labels=" + labels +
                ", datasets=" + datasets +
                '}';
    }
}
