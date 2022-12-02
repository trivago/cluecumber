package com.trivago.cluecumber.engine.json.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultTest {
    private Result result;

    @BeforeEach
    public void setup() {
        result = new Result();
    }

    @Test
    public void hasErrorMessageTest() {
        assertFalse(result.hasErrorMessage());
        result.setErrorMessage("My Error");
        assertTrue(result.hasErrorMessage());
    }

    @Test
    public void durationTest() {
        result.setDuration(1234567890);
        assertEquals(result.getDurationInMilliseconds(), 1234L);
        assertEquals(result.returnDurationString(), "0m 01s 234ms");
    }

    @Test
    public void returnErrorMessageWithClickableLinksTest() {
        result.setErrorMessage("Error in https://google.com found!");
        String error = result.returnErrorMessageWithClickableLinks();
        assertEquals(error, "Error in <a href='https://google.com' target='_blank'>https://google.com</a> found!");
    }
}
