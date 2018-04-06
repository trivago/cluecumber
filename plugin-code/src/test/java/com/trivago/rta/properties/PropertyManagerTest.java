package com.trivago.rta.properties;

import com.trivago.rta.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.rta.logging.CluecumberLogger;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PropertyManagerTest {
    private PropertyManager propertyManager;
    private CluecumberLogger logger;

    @Before
    public void setup() {
        logger = mock(CluecumberLogger.class);
        propertyManager = new PropertyManager(logger);
    }

    @Test
    public void generatedHtmlReportDirectoryTest() throws Exception {
        propertyManager.setGeneratedHtmlReportDirectory("test");
        assertThat(propertyManager.getGeneratedHtmlReportDirectory(), is("test"));
    }

    @Test
    public void sourceJsonReportDirectoryTest() {
        propertyManager.setSourceJsonReportDirectory("test");
        assertThat(propertyManager.getSourceJsonReportDirectory(), is("test"));
    }

    @Test(expected = WrongOrMissingPropertyException.class)
    public void missingJsonReportDirectoryTest() throws Exception {
        propertyManager.setGeneratedHtmlReportDirectory("test");
        propertyManager.validateSettings();
    }

    @Test(expected = WrongOrMissingPropertyException.class)
    public void missingHtmlReportDirectoryTest() throws Exception {
        propertyManager.setSourceJsonReportDirectory("test");
        propertyManager.validateSettings();
    }

    @Test
    public void logBasePropertiesTest() {
        propertyManager.logProperties();
        verify(logger, times(2)).info(anyString());
    }

    @Test
    public void customParametersTest() {
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("key", "value");
        propertyManager.setCustomParameters(customParameters);
        assertThat(propertyManager.getCustomParameters().size(), is(1));
        assertThat(propertyManager.getCustomParameters().get("key"), is("value"));
    }

    @Test
    public void logFullPropertiesTest() {
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("key1", "value1");
        customParameters.put("key2", "value2");
        propertyManager.setCustomParameters(customParameters);
        propertyManager.logProperties();
        verify(logger, times(4)).info(anyString());
    }
}
