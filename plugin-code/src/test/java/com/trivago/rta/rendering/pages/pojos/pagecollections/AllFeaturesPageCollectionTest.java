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

public class AllFeaturesPageCollectionTest {
    private AllFeaturesPageCollection allFeaturesPageCollection;

    @Test
    public void getEmptyTagStatsTest() {
        List<Report> reports = new ArrayList<>();
        allFeaturesPageCollection = new AllFeaturesPageCollection(reports);
        Map<Feature, ResultCount> featureStats = allFeaturesPageCollection.getFeatureResultCounts();
        assertThat(featureStats.size(), is(0));
    }
}
