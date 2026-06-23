package com.trivago.cluecumber.engine.properties;

import com.trivago.cluecumber.engine.constants.Settings;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InOrder;

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
        verify(logger, times(14)).info(anyString(),
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
        propertyManager.setCustomParametersDisplayMode(Settings.CustomParamDisplayMode.ALL_PAGES.name());
        assertEquals(propertyManager.getCustomParametersDisplayMode(), Settings.CustomParamDisplayMode.ALL_PAGES);
    }

    @Test
    public void customParametersDisplayModeUnknownTest() {
        propertyManager.setCustomParametersDisplayMode("SomethingElse");
        assertEquals(propertyManager.getCustomParametersDisplayMode(), Settings.CustomParamDisplayMode.SCENARIO_PAGES);
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
    public void expandPreviousScenarioRunsTest() {
        propertyManager.setExpandPreviousScenarioRuns(true);
        assertTrue(propertyManager.isExpandPreviousScenarioRuns());
    }

    @Test
    public void groupPreviousScenarioRunsTest() {
        assertFalse(propertyManager.isGroupPreviousScenarioRuns());
        propertyManager.setGroupPreviousScenarioRuns(true);
        assertTrue(propertyManager.isGroupPreviousScenarioRuns());
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
        verify(logger, times(17)).info(anyString(),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    // === Tests added to improve mutation coverage ===

    @Test
    public void setCustomParametersDisplayModeWithInvalidValueLogsWarning() {
        propertyManager.setCustomParametersDisplayMode("invalid_mode");
        verify(logger).warn(contains("Unknown setting for custom parameter page"));
    }

    @Test
    public void isExpandPreviousScenarioRunsDefaultsToFalse() {
        assertFalse(propertyManager.isExpandPreviousScenarioRuns());
    }

    @Test
    public void setCustomStatusColorPassedWithInvalidHexThrows() {
        assertThrows(WrongOrMissingPropertyException.class,
                () -> propertyManager.setCustomStatusColorPassed("notahex"));
    }

    @Test
    public void setCustomStatusColorFailedWithInvalidHexThrows() {
        assertThrows(WrongOrMissingPropertyException.class,
                () -> propertyManager.setCustomStatusColorFailed("notahex"));
    }

    @Test
    public void setCustomStatusColorSkippedWithInvalidHexThrows() {
        assertThrows(WrongOrMissingPropertyException.class,
                () -> propertyManager.setCustomStatusColorSkipped("notahex"));
    }

    @Test
    public void logPropertiesWithCustomParametersFileDoesNotLogExtraSeparator() throws CluecumberException {
        String customParametersFile = "src/test/resources/test.properties";
        when(fileIO.isExistingFile(customParametersFile)).thenCallRealMethod();
        when(fileIO.readContentFromFile(customParametersFile)).thenCallRealMethod();
        propertyManager.setCustomParametersFile(customParametersFile);

        propertyManager.logProperties();
        verify(logger, atLeastOnce()).info(contains("Test_Property"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    @Test
    public void logPropertiesWithCustomParametersVerifiesContent() {
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("myKey", "myValue");
        propertyManager.setCustomParameters(customParameters);

        propertyManager.logProperties();
        verify(logger, atLeastOnce()).info(contains("myKey -> myValue"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    @Test
    public void logPropertiesVerifiesSeparatorOrder() {
        propertyManager.logProperties();
        InOrder inOrder = inOrder(logger);
        inOrder.verify(logger).info(contains("source JSON report directory"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT),
                eq(CluecumberLogger.CluecumberLogLevel.COMPACT));
        inOrder.verify(logger).logInfoSeparator(eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    @Test
    public void logPropertiesWithCustomNavigationLinksLogsEntries() {
        Map<String, String> navLinks = new HashMap<>();
        navLinks.put("Test_Link", "https://test.com");
        propertyManager.setCustomNavigationLinks(navLinks);

        propertyManager.logProperties();
        verify(logger, atLeastOnce()).info(contains("Test_Link -> https://test.com"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    @Test
    public void logPropertiesVerifiesFinalSeparator() {
        propertyManager.logProperties();
        InOrder inOrder = inOrder(logger);
        inOrder.verify(logger).info(contains("colors (passed, failed, skipped)"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
        inOrder.verify(logger).logInfoSeparator(eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    @Test
    public void logPropertiesWithCustomParametersFileLogsSeparatorAndPath() throws CluecumberException {
        String customParametersFile = "src/test/resources/test.properties";
        when(fileIO.isExistingFile(customParametersFile)).thenCallRealMethod();
        when(fileIO.readContentFromFile(customParametersFile)).thenCallRealMethod();
        propertyManager.setCustomParametersFile(customParametersFile);

        propertyManager.logProperties();
        // Kills: removed call to logInfoSeparator(DEFAULT) at line 591
        // With customParametersFile set: logInfoSeparator(DEFAULT) called 3 times
        // (line 591 + line 605 + line 640)
        verify(logger, times(3)).logInfoSeparator(eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
        // Kills: removed call to info("custom parameters file") at line 592
        verify(logger).info(contains("custom parameters file"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    @Test
    public void logPropertiesWithCustomParametersNoFileLogsNoArgSeparator() {
        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("myKey", "myValue");
        propertyManager.setCustomParameters(customParameters);

        propertyManager.logProperties();
        // Kills: removed call to logInfoSeparator() (no-args) at line 597
        // When no customParametersFile is set but customParameters exist,
        // logInfoSeparator() with no args is called
        verify(logger).logInfoSeparator();
    }

    @Test
    public void logPropertiesWithCustomParametersAndFileNoNoArgSeparator() throws CluecumberException {
        String customParametersFile = "src/test/resources/test.properties";
        when(fileIO.isExistingFile(customParametersFile)).thenCallRealMethod();
        when(fileIO.readContentFromFile(customParametersFile)).thenCallRealMethod();
        propertyManager.setCustomParametersFile(customParametersFile);
        // customParameters are loaded from the file, so they're non-empty

        propertyManager.logProperties();
        // Kills: negated conditional at line 596
        // When customParametersFile IS set and customParameters are non-empty,
        // logInfoSeparator() (no-args) should NOT be called
        verify(logger, times(0)).logInfoSeparator();
    }

    @Test
    public void logPropertiesAlwaysLogsSeparatorAfterCustomParams() {
        propertyManager.logProperties();
        // Kills: removed call to logInfoSeparator(DEFAULT) at line 605
        // Without customParametersFile: logInfoSeparator(DEFAULT) called 2 times
        // (line 605 + line 640)
        verify(logger, times(2)).logInfoSeparator(eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    // === Additional tests to cover NO_COVERAGE mutants ===

    @Test
    public void getCustomFaviconFileDefaultsToNull() {
        assertNull(propertyManager.getCustomFaviconFile());
    }

    @Test
    public void getCustomFaviconFileReturnsSetValue() throws MissingFileException {
        when(fileIO.isExistingFile("favicon.ico")).thenReturn(true);
        propertyManager.setCustomFaviconFile("favicon.ico");
        assertEquals("favicon.ico", propertyManager.getCustomFaviconFile());
    }

    @Test
    public void setCustomFaviconFileWithNullDoesNotCheckFile() throws MissingFileException {
        propertyManager.setCustomFaviconFile(null);
        assertNull(propertyManager.getCustomFaviconFile());
    }

    @Test
    public void setCustomFaviconFileWithEmptyStringDoesNotCheckFile() throws MissingFileException {
        propertyManager.setCustomFaviconFile("");
        assertEquals("", propertyManager.getCustomFaviconFile());
    }

    @Test
    public void setCustomFaviconFileWithMissingFileThrows() {
        when(fileIO.isExistingFile("missing.ico")).thenReturn(false);
        assertThrows(MissingFileException.class, () -> propertyManager.setCustomFaviconFile("missing.ico"));
    }

    @Test
    public void getCustomLogoFileDefaultsToNull() {
        assertNull(propertyManager.getCustomLogoFile());
    }

    @Test
    public void getCustomLogoFileReturnsSetValue() throws MissingFileException {
        when(fileIO.isExistingFile("logo.png")).thenReturn(true);
        propertyManager.setCustomLogoFile("logo.png");
        assertEquals("logo.png", propertyManager.getCustomLogoFile());
    }

    @Test
    public void setCustomLogoFileWithNullDoesNotCheckFile() throws MissingFileException {
        propertyManager.setCustomLogoFile(null);
        assertNull(propertyManager.getCustomLogoFile());
    }

    @Test
    public void setCustomLogoFileWithEmptyStringDoesNotCheckFile() throws MissingFileException {
        propertyManager.setCustomLogoFile("");
        assertEquals("", propertyManager.getCustomLogoFile());
    }

    @Test
    public void setCustomLogoFileWithMissingFileThrows() {
        when(fileIO.isExistingFile("missing.png")).thenReturn(false);
        assertThrows(MissingFileException.class, () -> propertyManager.setCustomLogoFile("missing.png"));
    }

    @Test
    public void getNavigationLinksReturnsInternalLinks() {
        var links = propertyManager.getNavigationLinks();
        assertNotNull(links);
        assertTrue(links.size() > 0);
    }

    @Test
    public void getNavigationLinksRemovesRerunScenariosWhenGroupingDisabled() {
        assertFalse(propertyManager.isGroupPreviousScenarioRuns());
        var links = propertyManager.getNavigationLinks();
        assertTrue(links.stream().noneMatch(link -> link.getName().equals("rerun_scenarios")));
    }

    @Test
    public void getNavigationLinksKeepsRerunScenariosWhenGroupingEnabled() {
        propertyManager.setGroupPreviousScenarioRuns(true);
        var links = propertyManager.getNavigationLinks();
        assertTrue(links.stream().anyMatch(link -> link.getName().equals("rerun_scenarios")));
    }

    @Test
    public void getNavigationLinksIncludesCustomLinks() {
        Map<String, String> navLinks = new HashMap<>();
        navLinks.put("Custom_Link", "https://example.com");
        propertyManager.setCustomNavigationLinks(navLinks);
        var links = propertyManager.getNavigationLinks();
        assertTrue(links.stream().anyMatch(link -> link.getName().equals("Custom Link")));
    }

    @Test
    public void isExpandSubSectionsDefaultsToFalse() {
        assertFalse(propertyManager.isExpandSubSections());
    }

    @Test
    public void isExpandSubSectionsCanBeSetToTrue() {
        propertyManager.setExpandSubSections(true);
        assertTrue(propertyManager.isExpandSubSections());
    }

    @Test
    public void isExpandOutputsDefaultsToFalse() {
        assertFalse(propertyManager.isExpandOutputs());
    }

    @Test
    public void isExpandOutputsCanBeSetToTrue() {
        propertyManager.setExpandOutputs(true);
        assertTrue(propertyManager.isExpandOutputs());
    }

    @Test
    public void isExpandAttachmentsDefaultsToTrue() {
        assertTrue(propertyManager.isExpandAttachments());
    }

    @Test
    public void isExpandAttachmentsCanBeSetToFalse() {
        propertyManager.setExpandAttachments(false);
        assertFalse(propertyManager.isExpandAttachments());
    }

    @Test
    public void isExpandErrorMessagesDefaultsToFalse() {
        assertFalse(propertyManager.isExpandErrorMessages());
    }

    @Test
    public void isExpandErrorMessagesCanBeSetToTrue() {
        propertyManager.setExpandErrorMessages(true);
        assertTrue(propertyManager.isExpandErrorMessages());
    }

    @Test
    public void getStartPageDefaultsToAllScenarios() {
        assertEquals(Settings.StartPage.ALL_SCENARIOS, propertyManager.getStartPage());
    }

    @Test
    public void setStartPageWithInvalidValueLogsWarning() {
        propertyManager.setStartPage("invalid_page");
        verify(logger).warn(contains("Unknown start page 'invalid_page'"));
        assertEquals(Settings.StartPage.ALL_SCENARIOS, propertyManager.getStartPage());
    }

    @Test
    public void logPropertiesWithCustomFaviconFileLogsFavicon() throws MissingFileException {
        when(fileIO.isExistingFile("favicon.ico")).thenReturn(true);
        propertyManager.setCustomFaviconFile("favicon.ico");
        propertyManager.logProperties();
        verify(logger).info(contains("custom favicon file"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }

    @Test
    public void logPropertiesWithCustomLogoFileLogsLogo() throws MissingFileException {
        when(fileIO.isExistingFile("logo.png")).thenReturn(true);
        propertyManager.setCustomLogoFile("logo.png");
        propertyManager.logProperties();
        verify(logger).info(contains("custom logo file"),
                eq(CluecumberLogger.CluecumberLogLevel.DEFAULT));
    }
}
