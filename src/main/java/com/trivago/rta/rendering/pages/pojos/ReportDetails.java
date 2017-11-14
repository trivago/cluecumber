/*
 * Copyright 2017 trivago N.V.
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

package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.constants.PluginSettings;

public class ReportDetails {
    private String overviewChartJson;
    private String date;

    public void setChartJson(final String chartJson) {
        this.overviewChartJson = chartJson;
    }

    public String getChartJson() {
        return overviewChartJson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getGeneratorName() {
        return String.format("%s [%s]",
                PluginSettings.NAME, getClass().getPackage().getImplementationVersion());
    }
}
