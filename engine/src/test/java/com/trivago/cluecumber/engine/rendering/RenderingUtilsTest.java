package com.trivago.cluecumber.engine.rendering;

import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RenderingUtilsTest {
    @Test
    public void convertNanosecondsToTimeStringTest() {
        String timeString = RenderingUtils.convertNanosecondsToTimeString(1234567890000L);
        assertEquals(timeString, "20m 34s 567ms");
    }

    @Test
    public void convertNanosecondsToMillisecondsTest() {
        long milliseconds = RenderingUtils.convertNanosecondsToMilliseconds(1234567890000L);
        assertEquals(milliseconds, 1234567L);
    }

    @Test
    public void escapeHTMLTest() {
        String escapedHTML = RenderingUtils.escapeHTML("<body>This is a test öäüÖÄÜß</body>");
        assertEquals(escapedHTML, "&#60;body&#62;This is a test &#246;&#228;&#252;&#214;&#196;&#220;&#223;&#60;/body&#62;");
    }

    @Test
    public void turnUrlsIntoLinksTest() {
        String htmlWithLinks = RenderingUtils.turnUrlsIntoLinks("This is a test on ftp://some.ftp.url.com and http://www.trivago.de");
        assertEquals(htmlWithLinks, "This is a test on <a href='ftp://some.ftp.url.com' target='_blank'>ftp://some.ftp.url.com</a> and <a href='http://www.trivago.de' target='_blank'>http://www.trivago.de</a>");
    }

    @Test
    public void convertTimestampToZonedDateTimeTest() {
        String timestamp = "2018-09-14T13:02:15.123Z";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);
        assertNotNull(zonedDateTime);
        assertEquals(zonedDateTime.getYear(), 2018);
        assertEquals(zonedDateTime.getMonth(), Month.SEPTEMBER);
        assertEquals(zonedDateTime.getDayOfMonth(), 14);
        assertEquals(zonedDateTime.getHour(), 13);
        assertEquals(zonedDateTime.getMinute(), 2);
        assertEquals(zonedDateTime.getSecond(), 15);
    }

    @Test
    public void convertTimestampToZonedDateTimeInvalidTest() {
        String timestamp = "invalidTimestamp";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);
        assertNull(zonedDateTime);
    }

    @Test
    public void convertZonedDateTimeToDateStringTest() {
        String timestamp = "2018-09-14T13:02:15.123Z";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);

        final String dateLocal = Objects.requireNonNull(zonedDateTime).withZoneSameInstant(ZoneId.systemDefault()).
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String dateString = RenderingUtils.convertZonedDateTimeToDateString(zonedDateTime);
        assertEquals(dateString, dateLocal);
    }

    @Test
    public void convertZonedDateTimeToDateStringInvalidTest() {
        String dateString = RenderingUtils.convertZonedDateTimeToDateString(null);
        assertEquals(dateString, "");
    }

    @Test
    public void convertZonedDateTimeToTimeStringTest() {
        String timestamp = "2018-09-14T13:02:15.123Z";
        ZonedDateTime zonedDateTime = RenderingUtils.convertTimestampToZonedDateTime(timestamp);

        final String timeLocal = Objects.requireNonNull(zonedDateTime).withZoneSameInstant(ZoneId.systemDefault()).
                format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String timeString = RenderingUtils.convertZonedDateTimeToTimeString(zonedDateTime);
        assertEquals(timeString, timeLocal);
    }

    @Test
    public void convertZonedDateTimeToTimeStringInvalidTest() {
        String timeString = RenderingUtils.convertZonedDateTimeToTimeString(null);
        assertEquals(timeString, "");
    }
}
