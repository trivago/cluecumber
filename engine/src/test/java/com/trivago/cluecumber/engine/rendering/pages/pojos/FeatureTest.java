package com.trivago.cluecumber.engine.rendering.pages.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FeatureTest {

    private Feature feature;

    @BeforeEach
    public void setUp() {
        feature = new Feature("myFeature", "description", "uri", 1);
    }

    @Test
    public void equalsSameNameTest() {
        final Feature featureToCompare = new Feature("myFeature", "description", "uri", 2);
        assertNotEquals(feature, featureToCompare);
    }

    @Test
    public void equalsSameIndexTest() {
        final Feature featureToCompare = new Feature("otherName", "description", "uri", 1);
        assertNotEquals(feature, featureToCompare);
    }

    @Test
    public void equalsDifferentNameAndIndexTest() {
        final Feature featureToCompare = new Feature("otherName", "", "uri", 2);
        assertNotEquals(feature, featureToCompare);
    }

    @Test
    public void equalsSameNameAndIndexTest() {
        final Feature featureToCompare = new Feature("myFeature", "description", "uri", 1);
        assertEquals(feature, featureToCompare);
    }
}