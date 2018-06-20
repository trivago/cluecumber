package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.rendering.pages.pojos.Feature;
import com.trivago.rta.rendering.pages.pojos.ResultCount;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeatureSummaryPageCollectionTest {
    private FeatureSummaryPageCollection featureSummaryPageCollection;

    @Test
    public void getEmptyTagStatsTest() {
        List<Report> reports = new ArrayList<>();
        featureSummaryPageCollection = new FeatureSummaryPageCollection(reports);
        Map<Feature, ResultCount> featureStats = featureSummaryPageCollection.getFeatureResultCounts();
        assertThat(featureStats.size(), is(0));
    }
}
