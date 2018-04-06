package com.trivago.rta.logging;

import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CluecumberLoggerTest {

    private Log mockedLogger;
    private CluecumberLogger logger;

    @Before
    public void setup() {
        mockedLogger = mock(Log.class);
        logger = new CluecumberLogger();
        logger.setMojoLogger(mockedLogger);
    }

    @Test
    public void infoTest() {
        logger.info("Test");
        verify(mockedLogger, times(1))
                .info("Test");
    }

    @Test
    public void errorTest() {
        logger.error("Test");
        verify(mockedLogger, times(1))
                .error("Test");
    }
}
