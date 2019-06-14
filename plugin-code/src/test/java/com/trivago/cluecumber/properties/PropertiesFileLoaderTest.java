package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PropertiesFileLoaderTest {

    private PropertiesFileLoader propertiesFileLoader;

    @Before
    public void setUp() {
        FileIO fileIO = new FileIO();
        propertiesFileLoader = new PropertiesFileLoader(fileIO);
    }

    @Test(expected = CluecumberPluginException.class)
    public void loadInvalidPropertiesMapTest() throws CluecumberPluginException {
        propertiesFileLoader.loadPropertiesMap("nonexistent");
    }

    @Test
    public void loadPropertiesMapTest() throws CluecumberPluginException {
        final LinkedHashMap<String, String> propertiesMap = propertiesFileLoader.loadPropertiesMap("src/test/resources/test.properties");
        assertThat(propertiesMap.size(), is(2));
    }
}