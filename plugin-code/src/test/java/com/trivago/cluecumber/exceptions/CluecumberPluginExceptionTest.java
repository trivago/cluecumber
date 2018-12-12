package com.trivago.cluecumber.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CluecumberPluginExceptionTest {
    @Test
    public void testErrorMessage() {
        CluecumberPluginException exception = new CluecumberPluginException("This is a test");
        assertThat(exception.getMessage(), is("This is a test"));
    }
}
