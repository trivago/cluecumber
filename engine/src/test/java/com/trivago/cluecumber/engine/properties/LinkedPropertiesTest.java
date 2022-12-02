package com.trivago.cluecumber.engine.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedPropertiesTest {

    private LinkedProperties linkedProperties;

    @BeforeEach
    public void setup() {
        linkedProperties = new LinkedProperties();
    }

    @Test
    public void putTest() {
        assertFalse(linkedProperties.contains("value"));
        linkedProperties.put("key", "value");
    }

    @Test
    public void containsTest() {
        assertFalse(linkedProperties.contains("value"));
        linkedProperties.put("key", "value");
        assertTrue(linkedProperties.contains("value"));
    }

    @Test
    public void containsValueTest() {
        assertFalse(linkedProperties.contains("value"));
        linkedProperties.put("key", "value");
        assertTrue(linkedProperties.containsValue("value"));
    }

    @Test
    public void elementsTest() {
        assertThrows(UnsupportedOperationException.class, () -> linkedProperties.elements());
    }

    @Test
    public void entrySetTest() {
        linkedProperties.put("key", "value");
        linkedProperties.put("key2", "value2");
        assertEquals(linkedProperties.entrySet().size(), 2);
    }

    @Test
    public void containsKeyTest() {
        assertFalse(linkedProperties.containsKey("key"));
        linkedProperties.put("key", "value");
        assertTrue(linkedProperties.containsKey("key"));
    }
}
