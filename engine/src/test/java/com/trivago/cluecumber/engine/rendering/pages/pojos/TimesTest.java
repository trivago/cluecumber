package com.trivago.cluecumber.engine.rendering.pages.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimesTest {

    private Times times;

    @BeforeEach
    public void setup() {
        times = new Times();
        times.addTime(100000000, 1);
        times.addTime(500000000, 2);
        times.addTime(400000000, 3);
    }

    @Test
    public void getMinimumTimeStringTest() {
        assertEquals(times.getMinimumTimeString(), "0m 00s 100ms");
    }

    @Test
    public void getMinimumTimeScenarioIndexTest() {
        assertEquals(times.getMinimumTimeIndex(), 1);
    }

    @Test
    public void getMaximumTimeStringTest() {
        assertEquals(times.getMaximumTimeString(), "0m 00s 500ms");
    }

    @Test
    public void getMaximumTimeScenarioIndexTest() {
        assertEquals(times.getMaximumTimeIndex(), 2);
    }

    @Test
    public void getAverageTimeString() {
        assertEquals(times.getAverageTimeString(), "0m 00s 333ms");
    }
}