package com.trivago.cluecumber.core;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CluecumberCoreTest {
    @Test
    void builder() throws CluecumberException {
        CluecumberCore core = new CluecumberCore.Builder().build();
        assertNotNull(core);
    }
}
