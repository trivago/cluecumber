package com.trivago.cluecumber.engine.rendering.pages.templates;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import freemarker.template.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateEngineTest {

    private TemplateEngine templateEngine;
    private TemplateConfiguration templateConfiguration;

    @BeforeEach
    public void setup() {
        templateConfiguration = mock(TemplateConfiguration.class);
        templateEngine = new TemplateEngine(templateConfiguration);
    }

    @Test
    public void getTemplateTest() throws CluecumberException {
        Template returnTemplate = mock(Template.class);
        when(returnTemplate.getName()).thenReturn("MyTemplate");
        when(templateConfiguration.getTemplate(TemplateEngine.Template.SCENARIO_DETAILS.getFileName())).thenReturn(returnTemplate);
        final Template template = templateEngine.getTemplate(TemplateEngine.Template.SCENARIO_DETAILS);
        assertEquals(template.getName(), "MyTemplate");
    }
}
