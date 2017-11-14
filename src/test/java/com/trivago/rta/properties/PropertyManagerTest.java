package com.trivago.rta.properties;

import com.trivago.rta.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.rta.logging.CluecumberLogger;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PropertyManagerTest {
    private PropertyManager propertyManager;

    @Before
    public void setup() {
        CluecumberLogger logger = mock(CluecumberLogger.class);
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
}
