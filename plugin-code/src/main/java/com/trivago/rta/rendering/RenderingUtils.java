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

package com.trivago.rta.rendering;

import org.jsoup.Jsoup;

import java.time.Duration;

public class RenderingUtils {
    private static final int MICROSECOND_FACTOR = 1000000;

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
        return Duration.ofMillis(microseconds / MICROSECOND_FACTOR).toMillis();
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
}
