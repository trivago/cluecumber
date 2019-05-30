package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.logging.CluecumberLogger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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
    public void generatedHtmlReportDirectoryTest() {
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
    
    @Test(expected = WrongOrMissingPropertyException.class)
    public void invalidCustomParametersFileTest() throws Exception {
        propertyManager.setSourceJsonReportDirectory("test");
        propertyManager.setGeneratedHtmlReportDirectory("test");
        propertyManager.setCustomParametersFile("test");
        propertyManager.validateSettings();
    }

    @Test
    public void logBasePropertiesTest() {
        propertyManager.logProperties();
        verify(logger, times(6)).info(anyString());
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
    public void customParametersFileTest() {
        String customParamFilePath = "CustomParamTest.props";
        propertyManager.setCustomParametersFile(customParamFilePath);
        assertNotNull(propertyManager.getCustomParametersFile(), is("CustomParamTest.props"));
    }
    
    @Test
    public void initCustomParametersFromFileTest() {
        String customParamFilePath = new File(getClass().getClassLoader().getResource("CustomParamTest.props").getFile()).getAbsolutePath();
        propertyManager.setCustomParametersFile(customParamFilePath);
        propertyManager.initCustomParamatersFromFile();
        
        assertNotNull(propertyManager.getCustomParameters());
        assertThat(propertyManager.getCustomParameters().size(), is(2));
    }

    @Test
    public void initCustomParametersFromFileWithExistingParamsTest() {
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("key", "value");
        propertyManager.setCustomParameters(customParameters);
        
        String customParamFilePath = new File(getClass().getClassLoader().getResource("CustomParamTest.props").getFile()).getAbsolutePath();
        propertyManager.setCustomParametersFile(customParamFilePath);
        propertyManager.initCustomParamatersFromFile();
        
        assertNotNull(propertyManager.getCustomParameters());
        assertThat(propertyManager.getCustomParameters().size(), is(3));
    }
    
    @Test
    public void errorOnInvalidCustomParameterFileTest() {
        propertyManager.setCustomParametersFile("NoFile.txt");
        propertyManager.initCustomParamatersFromFile();
        
        assertNull(propertyManager.getCustomParameters());
        verify(logger, times(1)).error(anyString());
    }
    
    @Test
    public void failOnPendingOrSkippedStepsTest() {
        assertThat(propertyManager.isFailScenariosOnPendingOrUndefinedSteps(), is(false));
        propertyManager.setFailScenariosOnPendingOrUndefinedSteps(true);
        assertThat(propertyManager.isFailScenariosOnPendingOrUndefinedSteps(), is(true));
    }

    @Test
    public void expandBeforeAfterHooksTest() {
        assertThat(propertyManager.isExpandBeforeAfterHooks(), is(false));
        assertThat(propertyManager.isExpandStepHooks(), is(false));
        assertThat(propertyManager.isExpandDocStrings(), is(false));
        propertyManager.setExpandBeforeAfterHooks(true);
        assertThat(propertyManager.isExpandBeforeAfterHooks(), is(true));
        assertThat(propertyManager.isExpandStepHooks(), is(false));
        assertThat(propertyManager.isExpandDocStrings(), is(false));
    }

    @Test
    public void expandStepHooksTest() {
        assertThat(propertyManager.isExpandBeforeAfterHooks(), is(false));
        assertThat(propertyManager.isExpandStepHooks(), is(false));
        assertThat(propertyManager.isExpandDocStrings(), is(false));
        propertyManager.setExpandStepHooks(true);
        assertThat(propertyManager.isExpandBeforeAfterHooks(), is(false));
        assertThat(propertyManager.isExpandStepHooks(), is(true));
        assertThat(propertyManager.isExpandDocStrings(), is(false));
    }

    @Test
    public void expandDocStringsTest() {
        assertThat(propertyManager.isExpandBeforeAfterHooks(), is(false));
        assertThat(propertyManager.isExpandStepHooks(), is(false));
        assertThat(propertyManager.isExpandDocStrings(), is(false));
        propertyManager.setExpandDocStrings(true);
        assertThat(propertyManager.isExpandBeforeAfterHooks(), is(false));
        assertThat(propertyManager.isExpandStepHooks(), is(false));
        assertThat(propertyManager.isExpandDocStrings(), is(true));
    }

    @Test
    public void customCssTest() {
        String customCss = "MyCss";
        propertyManager.setCustomCss(customCss);
        assertThat(propertyManager.getCustomCss(), is("MyCss"));
    }

    @Test
    public void logFullPropertiesTest() {
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("key1", "value1");
        customParameters.put("key2", "value2");
        propertyManager.setCustomParameters(customParameters);

        propertyManager.setCustomCss("customCss");

        propertyManager.logProperties();
        verify(logger, times(9)).info(anyString());
    }
}
