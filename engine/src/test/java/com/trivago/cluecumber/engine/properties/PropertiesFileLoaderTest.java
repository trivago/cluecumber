package com.trivago.cluecumber.engine.properties;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertiesFileLoaderTest {

    private PropertiesFileLoader propertiesFileLoader;

    @BeforeEach
    public void setUp() {
        FileIO fileIO = new FileIO();
        propertiesFileLoader = new PropertiesFileLoader(fileIO);
    }

    @Test
    public void loadInvalidPropertiesMapTest() {
        assertThrows(CluecumberException.class, () -> propertiesFileLoader.loadPropertiesMap("nonexistent"));
    }

    @Test
    public void loadPropertiesMapTest() throws CluecumberException {
        final LinkedHashMap<String, String> propertiesMap = propertiesFileLoader.loadPropertiesMap("src/test/resources/test.properties");
        assertEquals(propertiesMap.size(), 2);
    }
}