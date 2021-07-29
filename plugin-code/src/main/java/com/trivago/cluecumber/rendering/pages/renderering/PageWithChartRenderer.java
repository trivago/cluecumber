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

package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.pages.pojos.CustomParameter;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.PageCollection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageWithChartRenderer extends PageRenderer {

    private final ChartJsonConverter chartJsonConverter;
    private final PropertyManager propertyManager;

    @Inject
    public PageWithChartRenderer(final ChartJsonConverter chartJsonConverter, final PropertyManager propertyManager) {
        this.chartJsonConverter = chartJsonConverter;
        this.propertyManager = propertyManager;
    }

    String convertChartToJson(final Chart chart) {
        return chartJsonConverter.convertChartToJson(chart);
    }

    protected void addCustomParametersToReportDetails(final PageCollection pageCollection){
        PluginSettings.CustomParamDisplayMode displayMode = propertyManager.getCustomParametersDisplayMode();
        pageCollection.setDisplayMode(displayMode);
        pageCollection.setStartPage(propertyManager.getStartPage().getPageName());

        Map<String, String> customParameterMap = propertyManager.getCustomParameters();
        if (customParameterMap == null || customParameterMap.isEmpty())
        {
            return;
        }

        // <customParameters> in the pom configuration section
        List<CustomParameter> customParameters = new ArrayList<>();
        customParameterMap.forEach((key1, value) -> {
            if (value == null || value.trim().isEmpty())
            {
                return;
            }
            String key = key1.replace("_", " ");
            CustomParameter customParameter = new CustomParameter(key, value);
            customParameters.add(customParameter);
        });

        pageCollection.setCustomParameters(customParameters);
    }
}
