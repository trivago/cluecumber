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

package com.trivago.cluecumber.logging;

import org.apache.maven.plugin.logging.Log;

import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class CluecumberLogger {

    private Log mojoLogger;
    private CluecumberLogLevel currentLogLevel;

    /**
     * Set the mojo logger so it can be used in any class that injects a CluecumberLogger.
     *
     * @param mojoLogger      The current {@link Log}.
     * @param currentLogLevel the log level that the logger should react to.
     */
    public void initialize(final Log mojoLogger, final String currentLogLevel) {
        this.mojoLogger = mojoLogger;
        if (currentLogLevel == null) {
            this.currentLogLevel = CluecumberLogLevel.DEFAULT;
            return;
        }

        try {
            this.currentLogLevel = CluecumberLogLevel.valueOf(currentLogLevel.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.currentLogLevel = CluecumberLogLevel.DEFAULT;
            warn("Log level " + currentLogLevel + " is unknown. Cluecumber will use 'default' logging.");
        }
    }

    public void logInfoSeparator(final CluecumberLogLevel... cluecumberLogLevels) {
        info("------------------------------------------------------------------------", cluecumberLogLevels);
    }

    /**
     * Info logging based on the provided Cluecumber log levels.
     *
     * @param logString           The {@link String} to be logged.
     * @param cluecumberLogLevels The log levels ({@link CluecumberLogLevel} list) in which the message should be displayed.
     */
    public void info(final CharSequence logString, CluecumberLogLevel... cluecumberLogLevels) {
        log(LogLevel.INFO, logString, cluecumberLogLevels);
    }

    /**
     * Warn logging. This is always displayed unless logging is off.
     *
     * @param logString The {@link String} to be logged.
     */
    public void warn(final CharSequence logString) {
        CluecumberLogLevel[] logLevels =
                new CluecumberLogLevel[]{CluecumberLogLevel.DEFAULT, CluecumberLogLevel.COMPACT, CluecumberLogLevel.MINIMAL};
        log(LogLevel.WARN, logString, logLevels);
    }

    /**
     * Logs a message based on the provided log levels.
     *
     * @param logString           The {@link String} to be logged.
     * @param CluecumberLogLevels The log levels ({@link CluecumberLogger} list) in which the message should be displayed.
     */
    private void log(final LogLevel logLevel, final CharSequence logString, CluecumberLogLevel... CluecumberLogLevels) {
        if (currentLogLevel == CluecumberLogLevel.OFF) {
            return;
        }

        if (currentLogLevel != null
                && CluecumberLogLevels != null
                && CluecumberLogLevels.length > 0
                && Arrays.stream(CluecumberLogLevels)
                .noneMatch(CluecumberLogLevel -> CluecumberLogLevel == currentLogLevel)) {
            return;
        }
        switch (logLevel) {
            case INFO:
                mojoLogger.info(logString);
                break;
            case WARN:
                mojoLogger.warn(logString);
                break;
        }
    }

    private enum LogLevel {
        INFO, WARN
    }

    public enum CluecumberLogLevel {
        DEFAULT, COMPACT, MINIMAL, OFF
    }
}
