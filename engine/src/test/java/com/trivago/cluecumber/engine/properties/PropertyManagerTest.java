package com.trivago.cluecumber.engine.properties;

import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.engine.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @BeforeEach
    public void setup() {
        logger = mock(CluecumberLogger.class);
        fileIO = mock(FileIO.class);
        PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader(fileIO);
        propertyManager = new PropertyManager(logger, fileIO, propertiesFileLoader);
    }

    @Test
    public void generatedHtmlReportDirectoryTest() throws WrongOrMissingPropertyException {
        propertyManager.setGeneratedHtmlReportDirectory("test");
        assertEquals(propertyManager.getGeneratedHtmlReportDirectory(), "test");
    }

    @Test
    public void sourceJsonReportDirectoryTest() throws WrongOrMissingPropertyException {
        propertyManager.setSourceJsonReportDirectory("test");
        assertEquals(propertyManager.getSourceJsonReportDirectory(), "test");
    }

    @Test
    public void missingJsonReportDirectoryTest() {
        assertThrows(WrongOrMissingPropertyException.class, () -> propertyManager.setSourceJsonReportDirectory(""));
    }

    @Test
    public void missingHtmlReportDirectoryTest() {
        assertThrows(WrongOrMissingPropertyException.class, () -> propertyManager.setGeneratedHtmlReportDirectory(""));
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
        assertEquals(propertyManager.getCustomParameters().size(), 1);
        assertEquals(propertyManager.getCustomParameters().get("key"), "value");
    }

    @Test
    public void customParametersDisplayModeTest() {
        propertyManager.setCustomParametersDisplayMode(PluginSettings.CustomParamDisplayMode.ALL_PAGES.name());
        assertEquals(propertyManager.getCustomParametersDisplayMode(), PluginSettings.CustomParamDisplayMode.ALL_PAGES);
    }

    @Test
    public void customParametersDisplayModeUnknownTest() {
        propertyManager.setCustomParametersDisplayMode("SomethingElse");
        assertEquals(propertyManager.getCustomParametersDisplayMode(), PluginSettings.CustomParamDisplayMode.SCENARIO_PAGES);
    }

    @Test
    public void setCustomParametersFileNonExistingTest() {
        String customParametersFile = "customParametersFile";
        when(fileIO.isExistingFile(customParametersFile)).thenReturn(false);
        assertThrows(MissingFileException.class, () -> propertyManager.setCustomParametersFile(customParametersFile));
    }

    @Test
    public void setCustomParametersFileTest() throws CluecumberException {
        String customParametersFile = "src/test/resources/test.properties";
        when(fileIO.isExistingFile(customParametersFile)).thenCallRealMethod();
        when(fileIO.readContentFromFile(customParametersFile)).thenCallRealMethod();
        propertyManager.setCustomParametersFile(customParametersFile);
        assertEquals(propertyManager.getCustomParametersFile(), customParametersFile);
        final Map<String, String> customParameters = propertyManager.getCustomParameters();
        assertEquals(customParameters.size(), 2);
        assertEquals(customParameters.get("Test_Property"), "some value");
        assertEquals(customParameters.get("Test_Property2"), "another value");
    }

    @Test
    public void failOnPendingOrSkippedStepsTest() {
        assertFalse(propertyManager.isFailScenariosOnPendingOrUndefinedSteps());
        propertyManager.setFailScenariosOnPendingOrUndefinedSteps(true);
        assertTrue(propertyManager.isFailScenariosOnPendingOrUndefinedSteps());
    }

    @Test
    public void expandBeforeAfterHooksTest() {
        assertFalse(propertyManager.isExpandBeforeAfterHooks());
        assertFalse(propertyManager.isExpandStepHooks());
        assertFalse(propertyManager.isExpandDocStrings());
        propertyManager.setExpandBeforeAfterHooks(true);
        assertTrue(propertyManager.isExpandBeforeAfterHooks());
        assertFalse(propertyManager.isExpandStepHooks());
        assertFalse(propertyManager.isExpandDocStrings());
    }

    @Test
    public void expandStepHooksTest() {
        assertFalse(propertyManager.isExpandBeforeAfterHooks());
        assertFalse(propertyManager.isExpandStepHooks());
        assertFalse(propertyManager.isExpandDocStrings());
        propertyManager.setExpandStepHooks(true);
        assertFalse(propertyManager.isExpandBeforeAfterHooks());
        assertTrue(propertyManager.isExpandStepHooks());
        assertFalse(propertyManager.isExpandDocStrings());
    }

    @Test
    public void expandDocStringsTest() {
        assertFalse(propertyManager.isExpandBeforeAfterHooks());
        assertFalse(propertyManager.isExpandStepHooks());
        assertFalse(propertyManager.isExpandDocStrings());
        propertyManager.setExpandDocStrings(true);
        assertFalse(propertyManager.isExpandBeforeAfterHooks());
        assertFalse(propertyManager.isExpandStepHooks());
        assertTrue(propertyManager.isExpandDocStrings());
    }

    @Test
    public void customCssTest() throws MissingFileException {
        String customCss = "MyCss";
        when(fileIO.isExistingFile(customCss)).thenReturn(true);
        propertyManager.setCustomCssFile(customCss);
        assertEquals(propertyManager.getCustomCssFile(), customCss);
    }

    @Test
    public void customCssNotSetTest() throws MissingFileException {
        propertyManager.setCustomCssFile(null);
        assertNull(propertyManager.getCustomCssFile());
    }

    @Test
    public void customCssMissingFileTest() {
        String customCss = "MyCss";
        when(fileIO.isExistingFile(customCss)).thenReturn(false);
        assertThrows(MissingFileException.class, () -> propertyManager.setCustomCssFile(customCss));
    }

    @Test
    public void customPageTitleTest() {
        String customPageTitle = "Custom Title";
        propertyManager.setCustomPageTitle(customPageTitle);
        assertEquals(propertyManager.getCustomPageTitle(), customPageTitle);
    }

    @Test
    public void customPageTitleNotSetTest() {
        assertEquals(propertyManager.getCustomPageTitle(), "Cluecumber Report");
    }

    @Test
    public void customColorsTest() throws WrongOrMissingPropertyException {
        assertEquals(propertyManager.getCustomStatusColorPassed(), "#60cc79");
        assertEquals(propertyManager.getCustomStatusColorFailed(), "#fc7180");
        assertEquals(propertyManager.getCustomStatusColorSkipped(), "#f7c42b");

        propertyManager.setCustomStatusColorPassed("#aaaaaa");
        propertyManager.setCustomStatusColorFailed("#bbbbbb");
        propertyManager.setCustomStatusColorSkipped("#cccccc");

        assertEquals(propertyManager.getCustomStatusColorPassed(), "#aaaaaa");
        assertEquals(propertyManager.getCustomStatusColorFailed(), "#bbbbbb");
        assertEquals(propertyManager.getCustomStatusColorSkipped(), "#cccccc");
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
