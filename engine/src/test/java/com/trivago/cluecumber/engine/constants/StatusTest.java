package com.trivago.cluecumber.engine.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTest {

    @Test
    public void getPassedStatusFromStringTest() {
        Status skipped = Status.fromString("passed");
        assertEquals(skipped, Status.PASSED);
    }

    @Test
    public void getFailedStatusFromStringTest() {
        Status skipped = Status.fromString("failed");
        assertEquals(skipped, Status.FAILED);
    }

    @Test
    public void getSkippedStatusFromStringTest() {
        Status skipped = Status.fromString("skipped");
        assertEquals(skipped, Status.SKIPPED);
    }
}
