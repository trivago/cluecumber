/*
 * Copyright 2018 trivago N.V.
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

package com.trivago.cluecumber.rendering;

import org.jsoup.Jsoup;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenderingUtils {
    private static final int MICROSECOND_FACTOR = 1000000;
    private static final Pattern URL_PATTERN = Pattern.compile("(ftp|http|https)://(\\w+:?\\w*@)?(\\S+)(:[0-9]+)?(/|/([\\w#!:.?+=&%@\\-/]))?");

    /**
     * Convert microseconds to a human readable time string.
     *
     * @param microseconds The amount of microseconds.
     * @return The human readable string representation.
     */
    public static String convertMicrosecondsToTimeString(final long microseconds) {
        Duration durationMilliseconds = Duration.ofMillis(microseconds / MICROSECOND_FACTOR);
        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return String.format("%dm %02ds %03dms", minutes, seconds, milliseconds);
    }

    /**
     * Convert microseconds to milliseconds.
     *
     * @param microseconds The amount of microseconds.
     * @return The millisecond representation.
     */
    public static long convertMicrosecondsToMilliseconds(final long microseconds) {
        return microseconds / MICROSECOND_FACTOR;
    }

    /**
     * Returns prettified HTML
     *
     * @param html The source html.
     * @return The prettified HTML.
     */
    static String prettifyHtml(String html) {
        return Jsoup.parse(html).toString().trim();
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
        for (int i = 0; i < sourceString.length(); i++) {
            char character = sourceString.charAt(i);
            if (character > 127 || character == '"' || character == '<' || character == '>' || character == '&') {
                stringBuilder.append("&#");
                stringBuilder.append((int) character);
                stringBuilder.append(';');
            } else {
                stringBuilder.append(character);
            }
        }
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
            targetString = targetString.replaceFirst(Pattern.quote(found), "<a href='" + found + "' target='_blank'>" + found + "</a>");
        }
        return targetString;
    }
}
