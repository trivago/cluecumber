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

package com.trivago.cluecumber.rendering.pages.pojos;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportDetails {
    private String chartJson;
    private final String date;

    public ReportDetails() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        this.date = dateFormat.format(date);
    }

    @SuppressWarnings("unused")
    public String getChartJson() {
        return chartJson;
    }

    public void setChartJson(final String chartJson) {
        this.chartJson = chartJson;
    }

    public String getDate() {
        return date;
    }

    public String getGeneratorName() {
        return String.format("%s version %s",
                PluginSettings.NAME, RenderingUtils.getPluginVersion());
    }
}
