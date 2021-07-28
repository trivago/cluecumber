/*
 * Copyright 2019 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.cluecumber.rendering.pages.renderering;

import java.net.URL;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class RenderingUtils {
    private static final int MICROSECOND_FACTOR = 1000000;
    private static final Pattern URL_PATTERN = Pattern.compile("(file.*)|((ftp|http|https)://(\\w+:?\\w*@)?(\\S+)(:[0-9]+)?(/|/([\\w#!:.?+=&%@\\-/]))?)");

    /**
     * Convert nanoseconds to a human readable time string.
     *
     * @param nanoseconds The amount of nanoseconds.
     * @return The human readable string representation.
     */
    public static String convertNanosecondsToTimeString(final long nanoseconds) {
        Duration durationMilliseconds = Duration.ofMillis(nanoseconds / MICROSECOND_FACTOR);
        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return String.format("%dm %02ds %03dms", minutes, seconds, milliseconds);
    }

    /**
     * Convert nanoseconds to milliseconds.
     *
     * @param nanoseconds The amount of nanoseconds.
     * @return The millisecond representation.
     */
    public static long convertNanosecondsToMilliseconds(final long nanoseconds) {
        return nanoseconds / MICROSECOND_FACTOR;
    }

    /**
     * Return the current Cluecumber version.
     *
     * @return The version string.
     */
    public static String getPluginVersion() {
        String version = RenderingUtils.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "unknown";
        }
        return version;
    }

    /**
     * Escape HTML tags in a string.
     *
     * @param sourceString The source string.
     * @return The escaped string.
     */
    public static String escapeHTML(final String sourceString) {
        StringBuilder stringBuilder = new StringBuilder(Math.max(16, sourceString.length()));
        IntStream.range(0, sourceString.length()).forEachOrdered(i -> {
            char character = sourceString.charAt(i);
            if (character > 127 || character == '"' || character == '<' || character == '>' || character == '&') {
                stringBuilder.append("&#");
                stringBuilder.append((int) character);
                stringBuilder.append(';');
            } else {
                stringBuilder.append(character);
            }
        });
        return stringBuilder.toString();
    }

    /**
     * Return the source html string with added tags so URLs are clickable.
     *
     * @param sourceString The source string.
     * @return The string with additional anchor tags.
     */
    public static String turnUrlsIntoLinks(final String sourceString) {
        Matcher matcher = URL_PATTERN.matcher(sourceString);
        String targetString = sourceString;
        while (matcher.find()) {
            String found = matcher.group();
            targetString = targetString.replaceFirst(Pattern.quote(found),
                    Matcher.quoteReplacement("<a href='" + found + "' target='_blank'>" + found + "</a>"));
        }
        return targetString;
    }

    /**
     * Return a {@link ZonedDateTime} from a timestamp string.
     *
     * @param timestampString the timestamp string.
     * @return the converted {@link ZonedDateTime}.
     */
    public static ZonedDateTime convertTimestampToZonedDateTime(final String timestampString) {
        try {
            return ZonedDateTime.parse(timestampString);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static String convertZonedDateTimeToDateString(final ZonedDateTime startDateTime) {
        try {
            return startDateTime.withZoneSameInstant(ZoneId.systemDefault()).
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ignored) {
        }
        return "";
    }

    public static String convertZonedDateTimeToTimeString(final ZonedDateTime startDateTime) {
        try {
            return startDateTime.withZoneSameInstant(ZoneId.systemDefault()).
                    format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception ignored) {
        }
        return "";
    }

    public static boolean isUrl(String value) {
        try {
            new URL(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
