package com.trivago.cluecumber.rendering;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RenderingUtilsTest {
    @Test
    public void convertNanosecondsToTimeStringTest() {
        String timeString = RenderingUtils.convertNanosecondsToTimeString(1234567890000L);
        assertThat(timeString, is("20m 34s 567ms"));
    }

    @Test
    public void convertNanosecondsToMillisecondsTest() {
        long milliseconds = RenderingUtils.convertNanosecondsToMilliseconds(1234567890000L);
        assertThat(milliseconds, is(1234567L));
    }

    @Test
    public void getPluginVersionTest() {
        String version = RenderingUtils.getPluginVersion();
        assertThat(version, is("unknown"));
    }

    @Test
    public void escapeHTMLTest() {
        String escapedHTML = RenderingUtils.escapeHTML("<body>This is a test öäüÖÄÜß</body>");
        assertThat(escapedHTML, is("&#60;body&#62;This is a test &#246;&#228;&#252;&#214;&#196;&#220;&#223;&#60;/body&#62;"));
    }

    @Test
    public void turnUrlsIntoLinksTest() {
        String htmlWithLinks = RenderingUtils.turnUrlsIntoLinks("This is a test on ftp://some.ftp.url.com and http://www.trivago.de");
        assertThat(htmlWithLinks, is("This is a test on <a href='ftp://some.ftp.url.com' target='_blank'>ftp://some.ftp.url.com</a> and <a href='http://www.trivago.de' target='_blank'>http://www.trivago.de</a>"));
    }
}
