package com.trivago.cluecumber.properties;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LinkedPropertiesTest {

    private LinkedProperties linkedProperties;

    @Before
    public void setup() {
        linkedProperties = new LinkedProperties();
    }

    @Test
    public void putTest() {
        assertThat(linkedProperties.contains("value"), is(false));
        linkedProperties.put("key", "value");
    }

    @Test
    public void containsTest() {
        assertThat(linkedProperties.contains("value"), is(false));
        linkedProperties.put("key", "value");
        assertThat(linkedProperties.contains("value"), is(true));
    }

    @Test
    public void containsValueTest() {
        assertThat(linkedProperties.contains("value"), is(false));
        linkedProperties.put("key", "value");
        assertThat(linkedProperties.containsValue("value"), is(true));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void elementsTest() {
        linkedProperties.elements();
    }

    @Test
    public void entrySetTest() {
        linkedProperties.put("key", "value");
        linkedProperties.put("key2", "value2");
        assertThat(linkedProperties.entrySet().size(), is(2));
    }

    @Test
    public void clearTest() {
        linkedProperties.put("key", "value");
        linkedProperties.clear();
        assertThat(linkedProperties.entrySet().size(), is(0));
    }

    @Test
    public void containsKeyTest() {
        assertThat(linkedProperties.containsKey("key"), is(false));
        linkedProperties.put("key", "value");
        assertThat(linkedProperties.containsKey("key"), is(true));
    }
}
