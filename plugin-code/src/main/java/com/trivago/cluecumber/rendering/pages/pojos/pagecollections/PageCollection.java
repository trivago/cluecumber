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
import com.trivago.cluecumber.constants.PluginSettings.CustomParamDisplayMode;
import com.trivago.cluecumber.rendering.pages.pojos.CustomParameter;
import com.trivago.cluecumber.rendering.pages.pojos.ReportDetails;

import java.util.List;

@SuppressWarnings({"unused", "CloneableClassWithoutClone"})
public class PageCollection implements Cloneable {
    private final ReportDetails reportDetails;
    private final String pageTitle;
    private boolean expandBeforeAfterHooks;
    private boolean expandStepHooks;
    private boolean expandDocStrings;
    private boolean expandAttachments;
    private List<CustomParameter> customParameters;
    private CustomParamDisplayMode displayMode;

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

    public boolean isExpandAttachments() {
        return expandAttachments;
    }

    public void setExpandAttachments(final boolean expandAttachments) {
        this.expandAttachments = expandAttachments;
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

    public boolean hasCustomParameters() {
        return customParameters != null && !customParameters.isEmpty();
    }

    public CustomParamDisplayMode getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(CustomParamDisplayMode displayMode) {
        this.displayMode = displayMode;
    }
}
