package com.trivago.rta.rendering;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RenderingUtilsTest {
    @Test
    public void convertMicrosecondsToTimeStringTest(){
        String timeString = RenderingUtils.convertMicrosecondsToTimeString(1234567890000l);
        assertThat(timeString, is("20m 34s 567ms"));
    }

    @Test
    public void convertMicrosecondsToMillisecondsTest(){
        long milliseconds = RenderingUtils.convertMicrosecondsToMilliseconds(1234567890000l);
        assertThat(milliseconds, is(1234567L));
    }

    @Test
    public void getPluginVersionTest(){
        String version = RenderingUtils.getPluginVersion();
        assertThat(version, is("unknown"));
    }
}
