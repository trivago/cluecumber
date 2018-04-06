package com.trivago.rta.rendering.pages.pojos;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

public class ReportDetailsTest {
    ReportDetails reportDetails;

    @Before
    public void setup(){
        reportDetails = new ReportDetails();
    }

    @Test
    public void getGeneratorNameTest(){
        String generatorName = reportDetails.getGeneratorName();
        MatcherAssert.assertThat(generatorName, CoreMatchers.is("Cluecumber Report Plugin [unknown]"));
    }
}
