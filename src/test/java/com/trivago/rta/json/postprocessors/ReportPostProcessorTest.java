package com.trivago.rta.json.postprocessors;

import com.trivago.rta.logging.CluecumberLogger;
import org.junit.Before;

public class ReportPostProcessorTest {
    private ReportPostProcessor reportPostProcessor;

    @Before
    public void setup() {
        CluecumberLogger logger = new CluecumberLogger();
        reportPostProcessor = new ReportPostProcessor(logger);
    }


}
