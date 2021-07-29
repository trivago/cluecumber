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

package com.trivago.cluecumber.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.rendering.pages.pojos.CustomParameter;
import com.trivago.cluecumber.rendering.pages.pojos.ReportDetails;

import java.util.List;

@SuppressWarnings("unused")
public class PageCollection implements Cloneable {
    private final ReportDetails reportDetails;
    private final String pageTitle;
    private boolean expandBeforeAfterHooks;
    private boolean expandStepHooks;
    private boolean expandDocStrings;
    private List<CustomParameter> customParameters;
    private String startPage;
    private PluginSettings.CustomParamDisplayMode displayMode;

    PageCollection(final String pageTitle) {
        this.reportDetails = new ReportDetails();
        this.pageTitle = pageTitle;
    }

    public ReportDetails getReportDetails() {
        return reportDetails;
    }

    public boolean isExpandBeforeAfterHooks() {
        return expandBeforeAfterHooks;
    }

    public void setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
        this.expandBeforeAfterHooks = expandBeforeAfterHooks;
    }

    public boolean isExpandStepHooks() {
        return expandStepHooks;
    }

    public void setExpandStepHooks(final boolean expandStepHooks) {
        this.expandStepHooks = expandStepHooks;
    }

    public boolean isExpandDocStrings() {
        return expandDocStrings;
    }

    public void setExpandDocStrings(final boolean expandDocStrings) {
        this.expandDocStrings = expandDocStrings;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public List<CustomParameter> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(final List<CustomParameter> customParameters) {
        this.customParameters = customParameters;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public void setDisplayMode(PluginSettings.CustomParamDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public boolean hasCustomParameters(String currentPage) {
        boolean paramsNotEmpty = customParameters != null && !customParameters.isEmpty();

        if (displayMode != null && displayMode == PluginSettings.CustomParamDisplayMode.START_PAGE)
        {
            return currentPage.equals(startPage) && paramsNotEmpty;
        }

        return paramsNotEmpty;
    }

}
