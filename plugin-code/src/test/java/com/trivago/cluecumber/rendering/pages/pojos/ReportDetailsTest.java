package com.trivago.cluecumber.rendering.pages.pojos;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportDetailsTest {
    private ReportDetails reportDetails;

    @Before
    public void setup() {
        reportDetails = new ReportDetails("Page Name");
    }

    @Test
    public void getGeneratorNameTest() {
        MatcherAssert.assertThat(reportDetails.getGeneratorName(), CoreMatchers.is("Cluecumber Report Plugin [unknown]"));
    }

    @Test
    public void getPageNameTest() {
        MatcherAssert.assertThat(reportDetails.getPageName(), CoreMatchers.is("Page Name"));
    }

    @Test
    public void reportDetailsDateTest() {
        ReportDetails reportDetails = new ReportDetails("");
        assertThat(reportDetails.getDate(), is(notNullValue()));
    }
}
