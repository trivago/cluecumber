package com.trivago.cluecumber.engine.rendering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateConfiguration;
import freemarker.template.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TemplateConfigurationTest {

    private TemplateConfiguration templateConfiguration;

    @BeforeEach
    public void setup() {
        templateConfiguration = new TemplateConfiguration();
    }

    @Test
    public void validInitTest() {
        templateConfiguration.init("bla");
    }

    @Test
    public void getNonexistentTemplateTest() {
        assertThrows(CluecumberException.class, () -> templateConfiguration.getTemplate("testTemplate"));
    }

    @Test
    public void getExistentTemplateTest() throws CluecumberException {
        templateConfiguration.init("/");
        Template template = templateConfiguration.getTemplate("test");
        assertEquals(template.toString(), "${test}");
    }
}
