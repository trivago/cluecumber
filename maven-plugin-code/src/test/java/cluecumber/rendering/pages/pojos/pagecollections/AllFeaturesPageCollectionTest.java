package cluecumber.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumberCore.json.pojo.Report;
import com.trivago.cluecumberCore.rendering.pages.pojos.Feature;
import com.trivago.cluecumberCore.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AllFeaturesPageCollectionTest {

    @Test
    public void getEmptyTagStatsTest() {
        List<Report> reports = new ArrayList<>();
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(reports, "");
        Map<Feature, ResultCount> featureStats = allFeaturesPageCollection.getFeatureResultCounts();
        assertThat(featureStats.size(), is(0));
    }

    @Test
    public void testGetNumberOfPassedFeatures() {
        List<Report> reports = new ArrayList<>();
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(reports, "");
        assertThat(allFeaturesPageCollection.getTotalNumberOfPassedFeatures(), is(0));
        assertThat(allFeaturesPageCollection.getTotalNumberOfSkippedFeatures(), is(0));
        assertThat(allFeaturesPageCollection.getTotalNumberOfFailedFeatures(), is(0));
    }
}
