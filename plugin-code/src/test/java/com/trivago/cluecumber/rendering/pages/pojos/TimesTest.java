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
        times.addTime(100000000);
        times.addTime(500000000);
        times.addTime(400000000);
    }

    @Test
    public void getMinimumTime() {
        assertThat(times.getMinimumTime(), is("0m 00s 100ms"));
    }

    @Test
    public void getMaximumTime() {
        assertThat(times.getMaximumTime(), is("0m 00s 500ms"));
    }

    @Test
    public void getAverageTime() {
        assertThat(times.getAverageTime(), is("0m 00s 333ms"));
    }
}