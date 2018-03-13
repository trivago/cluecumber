package com.trivago.rta.rendering.pages;

import com.trivago.rta.rendering.pages.pojos.ReportDetails;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PageRendererTest {

    private PageRenderer pageRenderer;

    @Before
    public void setup() {
        pageRenderer = new PageRenderer();
    }

    @Test
    public void reportDetailsDateTest() {
        ReportDetails reportDetails = new ReportDetails();
        assertThat(reportDetails.getDate(), is(notNullValue()));
    }
}
