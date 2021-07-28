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

public class Dataset {
    private List<Integer> data;
    private List<String> backgroundColor;
    private String label;
    private String stack;

    public List<Integer> getData() {
        return data;
    }

    public void setData(final List<Integer> data) {
        this.data = data;
    }

    public List<String> getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final List<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(final String stack) {
        this.stack = stack;
    }
}
