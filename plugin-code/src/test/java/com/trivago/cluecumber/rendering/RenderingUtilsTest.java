package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;
import org.junit.Test;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

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

    @Test
    public void convertTimestampToZonedDateTimeTest() {
        String timestamp = "2018-09-14T13:02:15.123Z";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);
        assertThat(zonedDateTime, is(notNullValue()));
        assertThat(zonedDateTime.getYear(), is(2018));
        assertThat(zonedDateTime.getMonth(), is(Month.SEPTEMBER));
        assertThat(zonedDateTime.getDayOfMonth(), is(14));
        assertThat(zonedDateTime.getHour(), is(13));
        assertThat(zonedDateTime.getMinute(), is(2));
        assertThat(zonedDateTime.getSecond(), is(15));
    }

    @Test
    public void convertTimestampToZonedDateTimeInvalidTest() {
        String timestamp = "invalidTimestamp";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);
        assertThat(zonedDateTime, is(nullValue()));
    }

    @Test
    public void convertZonedDateTimeToDateStringTest() {
        String timestamp = "2018-09-14T13:02:15.123Z";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);

        final String dateLocal = Objects.requireNonNull(zonedDateTime).withZoneSameInstant(ZoneId.systemDefault()).
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String dateString = RenderingUtils.convertZonedDateTimeToDateString(zonedDateTime);
        assertThat(dateString, is(dateLocal));
    }

    @Test
    public void convertZonedDateTimeToDateStringInvalidTest() {
        String dateString = RenderingUtils.convertZonedDateTimeToDateString(null);
        assertThat(dateString, is(""));
    }

    @Test
    public void convertZonedDateTimeToTimeStringTest() {
        String timestamp = "2018-09-14T13:02:15.123Z";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);

        final String timeLocal = Objects.requireNonNull(zonedDateTime).withZoneSameInstant(ZoneId.systemDefault()).
                format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String timeString = RenderingUtils.convertZonedDateTimeToTimeString(zonedDateTime);
        assertThat(timeString, is(timeLocal));
    }

    @Test
    public void convertZonedDateTimeToTimeStringInvalidTest() {
        String timeString = RenderingUtils.convertZonedDateTimeToTimeString(null);
        assertThat(timeString, is(""));
    }
}
