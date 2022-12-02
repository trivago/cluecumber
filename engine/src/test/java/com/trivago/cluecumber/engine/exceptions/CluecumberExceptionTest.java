package com.trivago.cluecumber.engine.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CluecumberExceptionTest {
    @Test
    public void testErrorMessage() {
        CluecumberException exception = new CluecumberException("This is a test");
        assertEquals(exception.getMessage(), "This is a test");
    }
}
