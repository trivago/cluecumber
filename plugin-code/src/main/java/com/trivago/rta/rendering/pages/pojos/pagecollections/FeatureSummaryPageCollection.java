package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.ResultCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureSummaryPageCollection extends PageCollection {
    private Map<Feature, ResultCount> featureResultCounts;

    public FeatureSummaryPageCollection(final List<Report> reports) {
        super(PluginSettings.FEATURE_SUMMARY_PAGE_NAME);
        calculateFeatureResultCounts(reports);
    }

    /**
     * Get a map of {@link ResultCount} lists connected to tag names.
     *
     * @return a map of {@link ResultCount} lists with tag names as keys.
     */
    public Map<Feature, ResultCount> getFeatureResultCounts() {
        return featureResultCounts;
    }

    /**
     * Calculate the numbers of failures, successes and skips per feature.
     *
     * @param reports The {@link Report} list.
     */
    private void calculateFeatureResultCounts(final List<Report> reports) {
        if (reports == null) return;
        featureResultCounts = new HashMap<>();
        for (Report report : reports) {
            Feature feature = new Feature(report.getName(), report.getFeatureIndex());
            ResultCount featureResultCount = this.featureResultCounts.getOrDefault(feature, new ResultCount());
            for (Element element : report.getElements()) {
                updateResultCount(featureResultCount, element.getStatus());
            }
            this.featureResultCounts.put(feature, featureResultCount);
        }
    }
}
