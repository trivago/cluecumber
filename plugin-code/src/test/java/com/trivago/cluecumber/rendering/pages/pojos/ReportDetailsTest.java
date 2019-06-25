package com.trivago.cluecumber.rendering.pages.pojos;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReportDetailsTest {
    private ReportDetails reportDetails;

    @Before
    public void setup() {
        reportDetails = new ReportDetails();
    }

    @Test
    public void getGeneratorNameTest() {
        MatcherAssert.assertThat(reportDetails.getGeneratorName(), is("Cluecumber Report Plugin version unknown"));
    }

    @Test
    public void reportDetailsDateTest() {
        ReportDetails reportDetails = new ReportDetails();
        assertThat(reportDetails.getDate(), is(notNullValue()));
    }
}
