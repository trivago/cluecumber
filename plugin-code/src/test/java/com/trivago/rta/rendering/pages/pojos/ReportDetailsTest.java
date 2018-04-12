package com.trivago.rta.rendering.pages.pojos;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

public class ReportDetailsTest {
    ReportDetails reportDetails;

    @Before
    public void setup(){
        reportDetails = new ReportDetails("Page Name");
    }

    @Test
    public void getGeneratorNameTest(){
        MatcherAssert.assertThat(reportDetails.getGeneratorName(), CoreMatchers.is("Cluecumber Report Plugin [unknown]"));
    }

    @Test
    public void getPageNameTest(){
        MatcherAssert.assertThat(reportDetails.getPageName(), CoreMatchers.is("Page Name"));
    }
}
