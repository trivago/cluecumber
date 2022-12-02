package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllFeaturesPageCollectionTest {

    @Test
    public void getEmptyTagStatsTest() {
        List<Report> reports = new ArrayList<>();
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(reports, "");
        Map<Feature, ResultCount> featureStats = allFeaturesPageCollection.getFeatureResultCounts();
        assertEquals(featureStats.size(), 0);
    }

    @Test
    public void testGetNumberOfPassedFeatures() {
        List<Report> reports = new ArrayList<>();
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(reports, "");
        assertEquals(allFeaturesPageCollection.getTotalNumberOfPassedFeatures(), 0);
        assertEquals(allFeaturesPageCollection.getTotalNumberOfSkippedFeatures(), 0);
        assertEquals(allFeaturesPageCollection.getTotalNumberOfFailedFeatures(), 0);
    }
}
