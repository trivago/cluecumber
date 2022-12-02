package com.trivago.cluecumber.engine.json.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagTest {

    private Tag tag;

    @BeforeEach
    public void setup() {
        tag = new Tag();
    }

    @Test
    public void getUrlFriendlyNameSimpleNameTest() {
        tag.setName("someName");
        assertEquals(tag.getUrlFriendlyName(), "someName");
    }

    @Test
    public void getUrlFriendlyNameComplexNameTest() {
        tag.setName("@this_is-@quite%complex");
        assertEquals(tag.getUrlFriendlyName(), "this_is-quite25complex");
    }
}