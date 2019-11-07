package cluecumber.rendering.pages.pojos;

import com.trivago.cluecumberCore.rendering.pages.pojos.Feature;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeatureTest {

    private Feature feature;

    @Before
    public void setUp() throws Exception {
        feature = new Feature("myFeature", 1);
    }

    @Test
    public void equalsSameNameTest() {
        Feature featureToCompare = new Feature("myFeature", 2);
        assertThat(feature.equals(featureToCompare), is(false));
    }

    @Test
    public void equalsSameIndexTest() {
        Feature featureToCompare = new Feature("otherName", 1);
        assertThat(feature.equals(featureToCompare), is(false));
    }

    @Test
    public void equalsDifferentNameAndIndexTest() {
        Feature featureToCompare = new Feature("otherName", 2);
        assertThat(feature.equals(featureToCompare), is(false));
    }

    @Test
    public void equalsSameNameAndIndexTest() {
        Feature featureToCompare = new Feature("myFeature", 1);
        assertThat(feature.equals(featureToCompare), is(true));
    }
}