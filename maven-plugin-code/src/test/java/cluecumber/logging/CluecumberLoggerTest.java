package cluecumber.logging;

import com.trivago.cluecumberCore.logging.CluecumberLogger;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CluecumberLoggerTest {

    private Log mockedLogger;
    private CluecumberLogger logger;

    @Before
    public void setup() {
        mockedLogger = mock(Log.class);
        logger = new CluecumberLogger();
        logger.initialize(mockedLogger, null);
    }

    @Test
    public void infoTest() {
        logger.info("Test");
        verify(mockedLogger, times(1))
                .info("Test");
    }

    @Test
    public void warnTest() {
        logger.warn("Test");
        verify(mockedLogger, times(1))
                .warn("Test");
    }

    @Test
    public void separatorTest() {
        logger.logInfoSeparator();
        verify(mockedLogger, times(1))
                .info("------------------------------------------------------------------------");
    }
}
