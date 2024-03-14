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
package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.constants.Settings.CustomParamDisplayMode;
import com.trivago.cluecumber.engine.rendering.pages.pojos.CustomParameter;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ReportDetails;

import java.util.List;

/**
 * The base page collection.
 */
public class PageCollection implements Cloneable {
    private final ReportDetails reportDetails;
    private final String pageTitle;
    private boolean expandBeforeAfterHooks;
    private boolean expandStepHooks;
    private boolean expandDocStrings;
    private boolean expandAttachments;
    private boolean expandOutputs;
    private boolean expandSubSections;
    private boolean groupPreviousScenarioRuns;
    private boolean expandPreviousScenarioRuns;
    private List<CustomParameter> customParameters;
    private CustomParamDisplayMode displayMode;
    private List<Link> links;

    /**
     * Constructor.
     *
     * @param pageTitle The page title.
     */
    PageCollection(final String pageTitle) {
        this.reportDetails = new ReportDetails();
        this.pageTitle = pageTitle;
    }

    /**
     * Get the report details.
     *
     * @return The {@link ReportDetails} instance.
     */
    public ReportDetails getReportDetails() {
        return reportDetails;
    }

    /**
     * Check if before and after hooks should be expanded.
     *
     * @return true if they should be expanded.
     */
    public boolean isExpandBeforeAfterHooks() {
        return expandBeforeAfterHooks;
    }

    /**
     * Set if before and after hooks should be expanded.
     *
     * @param expandBeforeAfterHooks true if they should be expanded.
     */
    public void setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
        this.expandBeforeAfterHooks = expandBeforeAfterHooks;
    }

    /**
     * Check if step hooks should be expanded.
     *
     * @return true if they should be expanded.
     */
    public boolean isExpandStepHooks() {
        return expandStepHooks;
    }

    /**
     * Set if step hooks should be expanded.
     *
     * @param expandStepHooks true if they should be expanded.
     */
    public void setExpandStepHooks(final boolean expandStepHooks) {
        this.expandStepHooks = expandStepHooks;
    }

    /**
     * Check if doc strings should be expanded.
     *
     * @return true if they should be expanded.
     */
    public boolean isExpandDocStrings() {
        return expandDocStrings;
    }

    /**
     * Sheck if doc strings should be expanded.
     *
     * @param expandDocStrings true if they should be expanded.
     */
    public void setExpandDocStrings(final boolean expandDocStrings) {
        this.expandDocStrings = expandDocStrings;
    }

    /**
     * Check if attachments should be expanded.
     *
     * @return true if they should be expanded.
     */
    public boolean isExpandAttachments() {
        return expandAttachments;
    }

    /**
     * Check if step outputs should be expanded.
     *
     * @return true if they should be expanded.
     */
    public boolean isExpandOutputs() {
        return expandOutputs;
    }

    /**
     * Set if attachments should be expanded.
     *
     * @param expandAttachments true if they should be expanded.
     */
    public void setExpandAttachments(final boolean expandAttachments) {
        this.expandAttachments = expandAttachments;
    }

    /**
     * Set if step outputs should be expanded.
     *
     * @param expandOutputs true if they should be expanded.
     */
    public void setExpandOutputs(final boolean expandOutputs) {
        this.expandOutputs = expandOutputs;
    }

    /**
     * Set if sub sections should be expanded.
     *
     * @param expandSubSections true if they should be expanded.
     */
    public void setExpandSubSections(final boolean expandSubSections) {
        this.expandSubSections = expandSubSections;
    }

    /**
     * Check if sub sections should be expanded.
     *
     * @return true if they should be expanded.
     */
    public boolean isExpandSubSections() {
        return expandSubSections;
    }

    /**
     * This determines whether the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     *
     * @return true means scenarios should be grouped and toggle should be shown.
     */
    public boolean isGroupPreviousScenarioRuns() {
        return groupPreviousScenarioRuns;
    }

    /**
     * Set whether the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     *
     * @param groupPreviousScenarioRuns true means scenarios should be grouped and toggle should be shown.
     */
    public void setGroupPreviousScenarioRuns(final boolean groupPreviousScenarioRuns) {
        this.groupPreviousScenarioRuns = groupPreviousScenarioRuns;
    }

    /**
     * This determines whether the not last run elements should be expanded and shown.
     *
     * @return true means it should be expanded.
     */
    public boolean isExpandPreviousScenarioRuns() {
        return expandPreviousScenarioRuns;
    }

    /**
     * Set whether the not last run elements should be expanded and shown.
     *
     * @param expandPreviousScenarioRuns true means elements should be expanded.
     */
    public void setExpandPreviousScenarioRuns(final boolean expandPreviousScenarioRuns) {
        this.expandPreviousScenarioRuns = expandPreviousScenarioRuns;
    }

    /**
     * Get the page title.
     *
     * @return The page title.
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * Get the custom parameters.
     *
     * @return The {@link CustomParameter} list.
     */
    public List<CustomParameter> getCustomParameters() {
        return customParameters;
    }

    /**
     * Set custom parameters.
     *
     * @param customParameters The {@link CustomParameter} list.
     */
    public void setCustomParameters(final List<CustomParameter> customParameters) {
        this.customParameters = customParameters;
    }

    /**
     * Check if there are custom parameters.
     *
     * @return true if there are custom parameters.
     */
    public boolean hasCustomParameters() {
        return customParameters != null && !customParameters.isEmpty();
    }

    /**
     * Get the display mode for custom parameters.
     *
     * @return The {@link CustomParamDisplayMode} enum.
     */
    public CustomParamDisplayMode getDisplayMode() {
        return displayMode;
    }

    /**
     * Set the display mode for custom parameters.
     *
     * @param displayMode The {@link CustomParamDisplayMode} enum.
     */
    public void setDisplayMode(CustomParamDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    /**
     * Get custom navigation links.
     *
     * @return The {@link Link} list.
     */
    public List<Link> getNavigationLinks() {
        return this.links;
    }

    /**
     * Set custom navigation links.
     *
     * @param links The {@link Link} list.
     */
    public void setNavigationLinks(List<Link> links) {
        this.links = links;
    }

    /**
     * Clone this page collection for later filtering.
     *
     * @return The cloned collection.
     * @throws CloneNotSupportedException Thrown if cloning failed.
     */
    @Override
    public PageCollection clone() throws CloneNotSupportedException {
        try {
            return (PageCollection) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
