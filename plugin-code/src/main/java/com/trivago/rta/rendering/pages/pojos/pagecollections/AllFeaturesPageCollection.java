/*
 * Copyright 2018 trivago N.V.
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

package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.constants.Status;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.ResultCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllFeaturesPageCollection extends SummaryPageCollection {
    private Map<Feature, ResultCount> resultCounts;

    public AllFeaturesPageCollection(final List<Report> reports) {
        super(PluginSettings.FEATURE_SUMMARY_PAGE_NAME);
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
        for (Report report : reports) {
            Feature feature = new Feature(report.getName(), report.getFeatureIndex());
            ResultCount featureResultCount = this.resultCounts.getOrDefault(feature, new ResultCount());
            for (Element element : report.getElements()) {
                updateResultCount(featureResultCount, element.getStatus());
            }
            this.resultCounts.put(feature, featureResultCount);
        }
    }
}
