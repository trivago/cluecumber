package com.trivago.cluecumber.engine.rendering.pages.pojos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CustomParameterTest {

    @Test
    public void isValidUrlTest() {
        CustomParameter customParameter = new CustomParameter("test", "https://www.someurl.de");
        assertTrue(customParameter.isUrl());
    }

    @Test
    public void isInvalidUrlTest() {
        CustomParameter customParameter = new CustomParameter("test", "test");
        assertFalse(customParameter.isUrl());
    }

    @Test
    public void getKeyValueTest() {
        CustomParameter customParameter = new CustomParameter("key", "value");
        assertEquals(customParameter.getKey(), "key");
        assertEquals(customParameter.getValue(), "value");
    }
}
