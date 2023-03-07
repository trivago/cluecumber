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

import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Page collection for the feature overview page.
 */
public class AllFeaturesPageCollection extends SummaryPageCollection {
    /**
     * Total results per feature.
     */
    private Map<Feature, ResultCount> resultCounts;
    private int totalNumberOfScenarios;

    /**
     * Constructor.
     *
     * @param reports   The list of {@link Report} instances.
     * @param pageTitle The page title.
     */
    public AllFeaturesPageCollection(final List<Report> reports, final String pageTitle) {
        super(pageTitle);
        calculateFeatureResultCounts(reports);
    }

    /**
     * Get a map of {@link ResultCount} lists connected to features.
     *
     * @return a map of {@link ResultCount} lists with features as keys.
     */
    public Map<Feature, ResultCount> getFeatureResultCounts() {
        return resultCounts;
    }


    /**
     * Returns all features.
     *
     * @return All Features.
     */
    public Set<Feature> getFeatures() {
        return resultCounts.keySet();
    }

    /**
     * Get the feature count.
     * @return The number of features.
     */
    public int getTotalNumberOfFeatures() {
        return resultCounts.size();
    }

    /**
     * Get the passed feature count.
     * @return The number of passed features.
     */
    public int getTotalNumberOfPassedFeatures() {
        return getNumberOfResultsWithStatus(resultCounts.values(), Status.PASSED);
    }

    /**
     * Get the failed feature count.
     * @return The number of failed features.
     */
    public int getTotalNumberOfFailedFeatures() {
        return getNumberOfResultsWithStatus(resultCounts.values(), Status.FAILED);
    }

    /**
     * Get the skipped feature count.
     * @return The number of skipped features.
     */
    public int getTotalNumberOfSkippedFeatures() {
        return getNumberOfResultsWithStatus(resultCounts.values(), Status.SKIPPED);
    }

    /**
     * Calculate the numbers of failures, successes and skips per feature.
     *
     * @param reports The {@link Report} list.
     */
    private void calculateFeatureResultCounts(final List<Report> reports) {
        if (reports == null) return;
        resultCounts = new HashMap<>();
        totalNumberOfScenarios = 0;
        reports.forEach(report -> {
            Feature feature = new Feature(report.getName(), report.getDescription(), report.getUri(), report.getFeatureIndex());
            ResultCount featureResultCount = this.resultCounts.getOrDefault(feature, new ResultCount());
            totalNumberOfScenarios += report.getElements().size();
            report.getElements().forEach(element -> updateResultCount(featureResultCount, element.getStatus()));
            this.resultCounts.put(feature, featureResultCount);
        });
    }


    /**
     * Get the scenario count.
     * @return The number of scenarios.
     */
    @SuppressWarnings("unused")
    public int getTotalNumberOfScenarios() {
        return totalNumberOfScenarios;
    }
}
