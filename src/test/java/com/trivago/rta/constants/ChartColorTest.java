package com.trivago.rta.constants;

import be.ceau.chart.color.Color;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChartColorTest {

    @Test
    public void getPassedChartColorByStatusTest(){
        Color passedColor = ChartColor.getChartColorByStatus(Status.PASSED);
        assertThat(passedColor.getR(), is(40));
        assertThat(passedColor.getG(), is(167));
        assertThat(passedColor.getB(), is(69));
    }

    @Test
    public void getFailedChartColorByStatusTest(){
        Color passedColor = ChartColor.getChartColorByStatus(Status.FAILED);
        assertThat(passedColor.getR(), is(220));
        assertThat(passedColor.getG(), is(53));
        assertThat(passedColor.getB(), is(69));
    }

    @Test
    public void getSkippedChartColorByStatusTest() {
        Color passedColor = ChartColor.getChartColorByStatus(Status.SKIPPED);
        assertThat(passedColor.getR(), is(255));
        assertThat(passedColor.getG(), is(193));
        assertThat(passedColor.getB(), is(7));
    }
}
