package com.trivago.cluecumber.json.pojo;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
