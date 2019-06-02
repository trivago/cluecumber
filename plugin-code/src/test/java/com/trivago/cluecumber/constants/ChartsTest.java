package com.trivago.cluecumber.constants;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChartsTest {

    @Test
    public void getPassedChartColorByStatusTest() {
        String color = Charts.Color.getChartColorStringByStatus(Status.PASSED);
        assertThat(color, is("rgba(40, 167, 69, 1.000)"));
    }

    @Test
    public void getFailedChartColorByStatusTest() {
        String color = Charts.Color.getChartColorStringByStatus(Status.PASSED);
        assertThat(color, is("rgba(40, 167, 69, 1.000)"));
    }

    @Test
    public void getSkippedChartColorByStatusTest() {
        String color = Charts.Color.getChartColorStringByStatus(Status.PASSED);
        assertThat(color, is("rgba(40, 167, 69, 1.000)"));
    }
}
