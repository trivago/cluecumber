package com.trivago.cluecumber.rendering.pages.pojos;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FeatureTest {

    private Feature feature;

    @Before
    public void setUp() {
        feature = new Feature("myFeature", "description", "uri", 1);
    }

    @Test
    public void equalsSameNameTest() {
        final Feature featureToCompare = new Feature("myFeature", "description", "uri", 2);
        assertThat(feature.equals(featureToCompare), is(false));
    }

    @Test
    public void equalsSameIndexTest() {
        final Feature featureToCompare = new Feature("otherName", "description", "uri", 1);
        assertThat(feature.equals(featureToCompare), is(false));
    }

    @Test
    public void equalsDifferentNameAndIndexTest() {
        final Feature featureToCompare = new Feature("otherName", "", "", 2);
        assertThat(feature.equals(featureToCompare), is(false));
    }

    @Test
    public void equalsSameNameAndIndexTest() {
        final Feature featureToCompare = new Feature("myFeature", "description", "uri", 1);
        assertThat(feature.equals(featureToCompare), is(true));
    }
}