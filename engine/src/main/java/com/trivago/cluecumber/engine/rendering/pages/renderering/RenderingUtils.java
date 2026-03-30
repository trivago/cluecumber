/*
 * Copyright 2023 trivago N.V.
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
package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.CluecumberEngine;

import java.net.URL;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


/**
 * Generic utilities for page rendering.
 */
public class RenderingUtils {

    private static final int MICROSECOND_FACTOR = 1000000;
    private static final String[] ANSI_COLORS = {
            "black", "red", "green", "yellow", "blue", "magenta", "cyan", "white"
    };
    private static final String[] ANSI_BRIGHT_COLORS = {
            "bright-black", "bright-red", "bright-green", "bright-yellow", "bright-blue", "bright-magenta", "bright-cyan", "bright-white"
    };
    private static final Pattern URL_PATTERN = Pattern.compile(
            "\\b((ftp|https?|file)://\\S+)|\\b(file:\\\\\\\\S+)");

    /**
     * Default constructor.
     */
    private RenderingUtils() {
        // Default constructor
    }

    /**
     * Convert nanoseconds to a human-readable time string.
     *
     * @param nanoseconds The amount of nanoseconds.
     * @return The human-readable string representation.
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
        return CluecumberEngine.class.getPackage().getImplementationVersion();
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
     * Convert ANSI color codes into HTML spans with CSS classes.
     *
     * @param sourceString The source string.
     * @return The HTML string with ANSI codes rendered as spans.
     */
    public static String renderAnsiToHtml(final String sourceString) {
        if (sourceString == null || sourceString.isEmpty()) {
            return "";
        }

        StringBuilder output = new StringBuilder(Math.max(16, sourceString.length()));
        AnsiStyle currentStyle = new AnsiStyle();
        boolean spanOpen = false;

        int index = 0;
        while (index < sourceString.length()) {
            int escIndex = sourceString.indexOf('\u001B', index);
            if (escIndex == -1) {
                appendEscaped(output, sourceString.substring(index));
                break;
            }

            if (escIndex > index) {
                appendEscaped(output, sourceString.substring(index, escIndex));
            }

            if (escIndex + 1 < sourceString.length() && sourceString.charAt(escIndex + 1) == '[') {
                int mIndex = sourceString.indexOf('m', escIndex + 2);
                if (mIndex == -1) {
                    appendEscaped(output, String.valueOf(sourceString.charAt(escIndex)));
                    index = escIndex + 1;
                    continue;
                }

                String codes = sourceString.substring(escIndex + 2, mIndex);
                AnsiStyle updatedStyle = currentStyle.copy();
                applyAnsiCodes(updatedStyle, codes);

                if (!updatedStyle.equals(currentStyle)) {
                    if (spanOpen) {
                        output.append("</span>");
                    }
                    spanOpen = updatedStyle.hasStyle();
                    if (spanOpen) {
                        output.append(updatedStyle.toSpanOpen());
                    }
                    currentStyle = updatedStyle;
                }

                index = mIndex + 1;
            } else {
                appendEscaped(output, String.valueOf(sourceString.charAt(escIndex)));
                index = escIndex + 1;
            }
        }

        if (spanOpen) {
            output.append("</span>");
        }

        return output.toString();
    }

    private static void appendEscaped(final StringBuilder output, final String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        output.append(escapeHTML(value));
    }

    private static void applyAnsiCodes(final AnsiStyle style, final String codeString) {
        if (codeString == null || codeString.isEmpty()) {
            style.reset();
            return;
        }

        String[] rawCodes = codeString.split(";");
        for (String rawCode : rawCodes) {
            if (rawCode.isEmpty()) {
                style.reset();
                continue;
            }

            int code;
            try {
                code = Integer.parseInt(rawCode);
            } catch (NumberFormatException ignored) {
                continue;
            }

            if (code == 0) {
                style.reset();
                continue;
            }
            if (code == 1) {
                style.setBold(true);
                continue;
            }
            if (code == 22) {
                style.setBold(false);
                continue;
            }
            if (code == 4) {
                style.setUnderline(true);
                continue;
            }
            if (code == 24) {
                style.setUnderline(false);
                continue;
            }
            if (code == 39) {
                style.resetForeground();
                continue;
            }
            if (code == 49) {
                style.resetBackground();
                continue;
            }
            if (code >= 30 && code <= 37) {
                style.setForegroundClass("ansi-fg-" + ANSI_COLORS[code - 30]);
                continue;
            }
            if (code >= 40 && code <= 47) {
                style.setBackgroundClass("ansi-bg-" + ANSI_COLORS[code - 40]);
                continue;
            }
            if (code >= 90 && code <= 97) {
                style.setForegroundClass("ansi-fg-" + ANSI_BRIGHT_COLORS[code - 90]);
                continue;
            }
            if (code >= 100 && code <= 107) {
                style.setBackgroundClass("ansi-bg-" + ANSI_BRIGHT_COLORS[code - 100]);
            }
        }
    }

    private static final class AnsiStyle {
        private boolean bold;
        private boolean underline;
        private String foregroundClass;
        private String backgroundClass;

        private AnsiStyle copy() {
            AnsiStyle copy = new AnsiStyle();
            copy.bold = bold;
            copy.underline = underline;
            copy.foregroundClass = foregroundClass;
            copy.backgroundClass = backgroundClass;
            return copy;
        }

        private void reset() {
            bold = false;
            underline = false;
            foregroundClass = null;
            backgroundClass = null;
        }

        private void resetForeground() {
            foregroundClass = null;
        }

        private void resetBackground() {
            backgroundClass = null;
        }

        private void setBold(final boolean bold) {
            this.bold = bold;
        }

        private void setUnderline(final boolean underline) {
            this.underline = underline;
        }

        private void setForegroundClass(final String foregroundClass) {
            this.foregroundClass = foregroundClass;
        }

        private void setBackgroundClass(final String backgroundClass) {
            this.backgroundClass = backgroundClass;
        }

        private boolean hasStyle() {
            return bold || underline || foregroundClass != null || backgroundClass != null;
        }

        private String toSpanOpen() {
            StringBuilder classes = new StringBuilder();
            if (bold) {
                classes.append("ansi-bold");
            }
            if (underline) {
                appendClass(classes, "ansi-underline");
            }
            if (foregroundClass != null) {
                appendClass(classes, foregroundClass);
            }
            if (backgroundClass != null) {
                appendClass(classes, backgroundClass);
            }

            StringBuilder span = new StringBuilder("<span");
            if (!classes.isEmpty()) {
                span.append(" class=\"").append(classes).append("\"");
            }
            span.append('>');
            return span.toString();
        }

        private void appendClass(final StringBuilder classes, final String value) {
            if (!classes.isEmpty()) {
                classes.append(' ');
            }
            classes.append(value);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AnsiStyle)) {
                return false;
            }
            AnsiStyle that = (AnsiStyle) other;
            return bold == that.bold
                    && underline == that.underline
                    && equalsValue(foregroundClass, that.foregroundClass)
                    && equalsValue(backgroundClass, that.backgroundClass);
        }

        private boolean equalsValue(final String left, final String right) {
            if (left == null) {
                return right == null;
            }
            return left.equals(right);
        }

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

    /**
     * Convert a time zone date time to a human-readable year, month and day string.
     *
     * @param startDateTime The {@link ZonedDateTime} instance.
     * @return The string representation.
     */
    public static String convertZonedDateTimeToDateString(final ZonedDateTime startDateTime) {
        try {
            return startDateTime.withZoneSameInstant(ZoneId.systemDefault()).
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * Convert a time zone date time to a human-readable hour, minute and second string.
     *
     * @param startDateTime The {@link ZonedDateTime} instance.
     * @return The string representation.
     */
    public static String convertZonedDateTimeToTimeString(final ZonedDateTime startDateTime) {
        try {
            return startDateTime.withZoneSameInstant(ZoneId.systemDefault()).
                    format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * Check if a string is a URL.
     *
     * @param value The string to check.
     * @return true if it is a URL.
     */
    public static boolean isUrl(String value) {
        try {
            new URL(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if a string is a relative URL.
     *
     * @param value The string to check.
     * @return true if it is a relative URL.
     */
    public static boolean isRelativeUrl(final String value) {
        return value.startsWith("./") || value.startsWith("../") || value.startsWith("#");
    }
}
