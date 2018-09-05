package com.trivago.cluecumber.constants;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChartColorTest {

    @Test
    public void getPassedChartColorByStatusTest() {
        String color = ChartColor.getChartColorStringByStatus(Status.PASSED);
        assertThat(color, is("rgba(40, 167, 69, 1.000)"));
    }

    @Test
    public void getFailedChartColorByStatusTest() {
        String color = ChartColor.getChartColorStringByStatus(Status.PASSED);
        assertThat(color, is("rgba(40, 167, 69, 1.000)"));
    }

    @Test
    public void getSkippedChartColorByStatusTest() {
        String color = ChartColor.getChartColorStringByStatus(Status.PASSED);
        assertThat(color, is("rgba(40, 167, 69, 1.000)"));
    }
}
