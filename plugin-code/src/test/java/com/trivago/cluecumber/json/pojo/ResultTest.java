package com.trivago.cluecumber.json.pojo;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ResultTest {
    private Result result;

    @Before
    public void setup() {
        result = new Result();
    }

    @Test
    public void hasErrorMessageTest() {
        assertThat(result.hasErrorMessage(), is(false));
        result.setErrorMessage("My Error");
        assertThat(result.hasErrorMessage(), is(true));
    }

    @Test
    public void durationTest() {
        result.setDuration(1234567890);
        assertThat(result.getDurationInMilliseconds(), is(1234L));
        assertThat(result.returnDurationString(), is("0m 01s 234ms"));
    }

    @Test
    public void returnErrorMessageWithClickableLinksTest() throws CluecumberPluginException {
        result.setErrorMessage("Error in http://google.com found!");
        String error = result.returnErrorMessageWithClickableLinks();
        assertThat(error, is("Error in <a href='http://google.com' target='_blank'>http://google.com</a> found!"));
    }
}
