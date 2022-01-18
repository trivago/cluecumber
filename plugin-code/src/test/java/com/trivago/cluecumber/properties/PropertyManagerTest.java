package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.logging.CluecumberLogger;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PropertyManagerTest {
    private PropertyManager propertyManager;
    private CluecumberLogger logger;
    private FileIO fileIO;

    @Before
    public void setup() {
        logger = mock(CluecumberLogger.class);
        fileIO = mock(FileIO.class);
        PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader(fileIO);
        propertyManager = new PropertyManager(logger, fileIO, propertiesFileLoader);
    }

    @Test
    public void generatedHtmlReportDirectoryTest() throws WrongOrMissingPropertyException {
        propertyManager.setGeneratedHtmlReportDirectory("test");
        assertThat(propertyManager.getGeneratedHtmlReportDirectory(), is("test"));
    }

    @Test
    public void sourceJsonReportDirectoryTest() throws WrongOrMissingPropertyException {
        propertyManager.setSourceJsonReportDirectory("test");
        assertThat(propertyManager.getSourceJsonReportDirectory(), is("test"));
    }

    @Test(expected = WrongOrMissingPropertyException.class)
    public void missingJsonReportDirectoryTest() throws Exception {
        propertyManager.setSourceJsonReportDirectory("");
    }

    @Test(expected = WrongOrMissingPropertyException.class)
    public void missingHtmlReportDirectoryTest() throws Exception {
        propertyManager.setGeneratedHtmlReportDirectory("");
    }

    @Test
    public void logBasePropertiesTest() {
        propertyManager.logProperties();
        verify(logger, times(2)).info(anyString(),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT),
                eq(CluecumberLogger.CluecumberLogLevel.COMPACT));
        verify(logger, times(9)).info(anyString(),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
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
    public void customParametersDisplayModeTest() {
        propertyManager.setCustomParametersDisplayMode(PluginSettings.CustomParamDisplayMode.ALL_PAGES.name());
        assertThat(propertyManager.getCustomParametersDisplayMode(), is(PluginSettings.CustomParamDisplayMode.ALL_PAGES));
    }

    @Test
    public void customParametersDisplayModeUnknownTest() {
        propertyManager.setCustomParametersDisplayMode("SomethingElse");
        assertThat(propertyManager.getCustomParametersDisplayMode(), is(PluginSettings.CustomParamDisplayMode.SCENARIO_PAGES));
    }

    @Test(expected = MissingFileException.class)
    public void setCustomParametersFileNonExistingTest() throws CluecumberPluginException {
        String customParametersFile = "customParametersFile";
        when(fileIO.isExistingFile(customParametersFile)).thenReturn(false);
        propertyManager.setCustomParametersFile(customParametersFile);
    }

    @Test
    public void setCustomParametersFileTest() throws CluecumberPluginException {
        String customParametersFile = "src/test/resources/test.properties";
        when(fileIO.isExistingFile(customParametersFile)).thenCallRealMethod();
        when(fileIO.readContentFromFile(customParametersFile)).thenCallRealMethod();
        propertyManager.setCustomParametersFile(customParametersFile);
        assertThat(propertyManager.getCustomParametersFile(), is(customParametersFile));
        final Map<String, String> customParameters = propertyManager.getCustomParameters();
        assertThat(customParameters.size(), is(2));
        assertThat(customParameters.get("Test_Property"), is("some value"));
        assertThat(customParameters.get("Test_Property2"), is("another value"));
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
    public void customCssTest() throws MissingFileException {
        String customCss = "MyCss";
        when(fileIO.isExistingFile(customCss)).thenReturn(true);
        propertyManager.setCustomCssFile(customCss);
        assertThat(propertyManager.getCustomCssFile(), is(customCss));
    }

    @Test
    public void customCssNotSetTest() throws MissingFileException {
        propertyManager.setCustomCssFile(null);
        assertThat(propertyManager.getCustomCssFile(), is(nullValue()));
    }

    @Test(expected = MissingFileException.class)
    public void customCssMissingFileTest() throws MissingFileException {
        String customCss = "MyCss";
        when(fileIO.isExistingFile(customCss)).thenReturn(false);
        propertyManager.setCustomCssFile(customCss);
    }

    @Test
    public void customPageTitleTest() {
        String customPageTitle = "Custom Title";
        propertyManager.setCustomPageTitle(customPageTitle);
        assertThat(propertyManager.getCustomPageTitle(), is(customPageTitle));
    }

    @Test
    public void customPageTitleNotSetTest() {
        assertThat(propertyManager.getCustomPageTitle(), is("Cluecumber Report"));
    }

    @Test
    public void customColorsTest() throws WrongOrMissingPropertyException {
        assertThat(propertyManager.getCustomStatusColorPassed(), is("#28a745"));
        assertThat(propertyManager.getCustomStatusColorFailed(), is("#dc3545"));
        assertThat(propertyManager.getCustomStatusColorSkipped(), is("#ffc107"));

        propertyManager.setCustomStatusColorPassed("#aaaaaa");
        propertyManager.setCustomStatusColorFailed("#bbbbbb");
        propertyManager.setCustomStatusColorSkipped("#cccccc");

        assertThat(propertyManager.getCustomStatusColorPassed(), is("#aaaaaa"));
        assertThat(propertyManager.getCustomStatusColorFailed(), is("#bbbbbb"));
        assertThat(propertyManager.getCustomStatusColorSkipped(), is("#cccccc"));
    }

    @Test
    public void logFullPropertiesTest() throws MissingFileException {
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("key1", "value1");
        customParameters.put("key2", "value2");
        propertyManager.setCustomParameters(customParameters);

        when(fileIO.isExistingFile("test")).thenReturn(true);
        propertyManager.setCustomCssFile("test");

        propertyManager.logProperties();
        verify(logger, times(2)).info(anyString(),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT),
                eq(CluecumberLogger.CluecumberLogLevel.COMPACT));
        verify(logger, times(12)).info(anyString(),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }
}
