package com.trivago.cluecumber.rendering.pages.pojos;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimesTest {

    private Times times;

    @Before
    public void setup() {
        times = new Times();
        times.addTime(100000000, 1);
        times.addTime(500000000, 2);
        times.addTime(400000000, 3);
    }

    @Test
    public void getMinimumTimeStringTest() {
        assertThat(times.getMinimumTimeString(), is("0m 00s 100ms"));
    }

    @Test
    public void getMinimumTimeScenarioIndexTest() {
        assertThat(times.getMinimumTimeScenarioIndex(), is(1));
    }

    @Test
    public void getMaximumTimeStringTest() {
        assertThat(times.getMaximumTimeString(), is("0m 00s 500ms"));
    }

    @Test
    public void getMaximumTimeScenarioIndexTest() {
        assertThat(times.getMaximumTimeScenarioIndex(), is(2));
    }

    @Test
    public void getAverageTimeString() {
        assertThat(times.getAverageTimeString(), is("0m 00s 333ms"));
    }
}