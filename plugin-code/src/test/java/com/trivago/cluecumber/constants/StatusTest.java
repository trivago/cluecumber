package com.trivago.cluecumber.constants;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StatusTest {

    @Test
    public void getPassedStatusFromStringTest() {
        Status skipped = Status.fromString("passed");
        assertThat(skipped, is(Status.PASSED));
    }

    @Test
    public void getFailedStatusFromStringTest() {
        Status skipped = Status.fromString("failed");
        assertThat(skipped, is(Status.FAILED));
    }

    @Test
    public void getSkippedStatusFromStringTest() {
        Status skipped = Status.fromString("skipped");
        assertThat(skipped, is(Status.SKIPPED));
    }
}
