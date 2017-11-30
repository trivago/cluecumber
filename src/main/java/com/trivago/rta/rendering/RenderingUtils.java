/*
 * Copyright 2017 trivago N.V.
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

package com.trivago.rta.rendering;

import java.time.Duration;

public class RenderingUtils {
    private static final int MICROSECOND_FACTOR = 1000000;

    public static String convertMicrosecondsToTimeString(final long microseconds) {
        Duration durationMilliseconds = Duration.ofMillis(microseconds / MICROSECOND_FACTOR);
        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return String.format("%dm %02ds %03dms", minutes, seconds, milliseconds);
    }

    public static long convertMicrosecondsToMilliseconds(final long microseconds) {
        return Duration.ofMillis(microseconds / MICROSECOND_FACTOR).toMillis();
    }

    public static String escapeHTML(final String htmlString) {
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
