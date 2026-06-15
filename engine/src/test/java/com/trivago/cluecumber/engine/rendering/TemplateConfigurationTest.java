package com.trivago.cluecumber.engine.rendering;

import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateConfiguration;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
