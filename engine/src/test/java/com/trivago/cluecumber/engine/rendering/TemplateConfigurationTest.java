package com.trivago.cluecumber.engine.rendering;

import com.google.gson.Gson;
import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.processors.ElementIndexPreProcessor;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.ScenarioDetailsPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateConfiguration;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateContextFactory;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateConfigurationTest {

    private TemplateConfiguration templateConfiguration;

    @BeforeEach
    public void setup() {
        templateConfiguration = new TemplateConfiguration();
    }

    @Test
    public void validInitTest() {
        templateConfiguration.init(Settings.BASE_TEMPLATE_PATH);
    }

    @Test
    public void getNonexistentTemplateTest() {
        templateConfiguration.init(Settings.BASE_TEMPLATE_PATH);
        assertThrows(CluecumberException.class, () -> templateConfiguration.getTemplate("testTemplate"));
    }

    @Test
    public void getExistentTemplateTest() throws CluecumberException, java.io.IOException {
        templateConfiguration.init(Settings.BASE_TEMPLATE_PATH);
        PebbleTemplate template = templateConfiguration.getTemplate("custom-css");
        StringWriter writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("passedColor", "passed");
        context.put("failedColor", "failed");
        context.put("skippedColor", "skipped");
        template.evaluate(writer, context);
        assertFalse(writer.toString().isEmpty());
    }

    @Test
    public void getAllReportTemplatesTest() throws CluecumberException {
        templateConfiguration.init(Settings.BASE_TEMPLATE_PATH);
        for (final String templateName : new String[]{
                "scenario-summary",
                "scenario-detail",
                "scenario-sequence",
                "rerun-scenarios",
                "feature-summary",
                "tag-summary",
                "step-summary",
                "exception-summary",
                "tree-view",
                "index"
        }) {
            templateConfiguration.getTemplate(templateName);
        }
    }

    @Test
    public void fullySkippedScenarioDetailRendersTest() throws CluecumberException, java.io.IOException {
        templateConfiguration.init(Settings.BASE_TEMPLATE_PATH);
        Path jsonPath = Path.of("..", "examples", "json", "fully_skipped_scenario.json").toAbsolutePath().normalize();
        String json = Files.readString(jsonPath);
        Report[] reports = new Gson().fromJson(json, Report[].class);
        Report report = reports[0];
        Element element = report.getElements().get(0);
        element.setFeatureName(report.getName());
        element.setFeatureIndex(0);
        element.setFeatureUri(report.getUri());

        new ElementIndexPreProcessor().process(List.of(report));

        ScenarioDetailsPageCollection collection = new ScenarioDetailsPageCollection(element, "Test");
        Map<String, Object> context = TemplateContextFactory.create(collection);
        PebbleTemplate template = templateConfiguration.getTemplate("scenario-detail");
        StringWriter writer = new StringWriter();
        template.evaluate(writer, context);
        String html = writer.toString();

        assertTrue(html.contains("somelocation.beforeStep"));
        assertTrue(html.contains("somelocation.teardownInterception"));
        assertFalse(html.contains("TrupiSteps"));
        assertTrue(html.contains("firstExceptionContainer"));
        assertTrue(html.contains(">More</button>"));
        assertTrue(html.contains(">Less</button>"));
        assertTrue(html.contains("org.opentest4j.TestAbortedException: assumption was not met"));
        assertTrue(html.contains("exc_pre_"));
        assertTrue(html.contains("exc_before_"));
    }
}
