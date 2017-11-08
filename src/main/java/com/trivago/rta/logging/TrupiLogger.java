package com.trivago.rta.logging;

import org.apache.maven.plugin.logging.Log;

import javax.inject.Singleton;

@Singleton
public class TrupiLogger {

    private Log mojoLogger;

    public void setMojoLogger(final Log mojoLogger) {
        this.mojoLogger = mojoLogger;
    }

    public void debug(final CharSequence charSequence) {
        mojoLogger.debug(charSequence);
    }

    public void info(final CharSequence charSequence) {
        mojoLogger.info(charSequence);
    }
}
