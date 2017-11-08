package com.trivago.rta.logging;

import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CucableLoggerTest {

    private Log mockedLogger;
    private TrupiLogger logger;

    @Before
    public void setup() {
        mockedLogger = mock(Log.class);
        logger = new TrupiLogger();
        logger.setMojoLogger(mockedLogger);
    }

    @Test
    public void infoTest() {
        logger.info("Test");
        verify(mockedLogger, times(1))
                .info("Test");
    }
}
