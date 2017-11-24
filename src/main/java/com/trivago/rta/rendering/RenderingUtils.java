package com.trivago.rta.rendering;

import java.time.Duration;

public class RenderingUtils {
    private static final int MICROSECOND_FACTOR = 1000000;

    public static String convertMicrosecondsToTimeString(long microseconds) {
        Duration durationMilliseconds = Duration.ofMillis(microseconds / MICROSECOND_FACTOR);
        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return String.format("%dm %02ds %03dms", minutes, seconds, milliseconds);
    }

    public static long convertMicrosecondsToMilliseconds(long microseconds) {
        return Duration.ofMillis(microseconds / MICROSECOND_FACTOR).toMillis();
    }

    public static String escapeHTML(String htmlString) {
        StringBuilder out = new StringBuilder(Math.max(16, htmlString.length()));
        for (int i = 0; i < htmlString.length(); i++) {
            char c = htmlString.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
