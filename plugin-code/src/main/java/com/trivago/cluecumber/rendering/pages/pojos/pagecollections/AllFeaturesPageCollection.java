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

import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllFeaturesPageCollection extends SummaryPageCollection {
    private Map<Feature, ResultCount> resultCounts;
    private int totalNumberOfScenarios;

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

    public Set<Feature> getFeatures() {
        return resultCounts.keySet();
    }

    public int getTotalNumberOfFeatures() {
        return resultCounts.size();
    }

    public int getTotalNumberOfPassedFeatures() {
        return getNumberOfResultsWithStatus(resultCounts.values(), Status.PASSED);
    }

    public int getTotalNumberOfFailedFeatures() {
        return getNumberOfResultsWithStatus(resultCounts.values(), Status.FAILED);
    }

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
            Feature feature = new Feature(
                    report.getName(), report.getDescription(), report.getUri(), report.getFeatureIndex()
            );
            ResultCount featureResultCount = this.resultCounts.getOrDefault(feature, new ResultCount());
            totalNumberOfScenarios += report.getElements().size();
            report.getElements().forEach(element -> updateResultCount(featureResultCount, element.getStatus()));
            this.resultCounts.put(feature, featureResultCount);
        });
    }

    @SuppressWarnings("unused")
    public int getTotalNumberOfScenarios() {
        return totalNumberOfScenarios;
    }
}
