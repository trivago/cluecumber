package com.trivago.cluecumber.rendering.pages.templates;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateEngineTest {

    private TemplateEngine templateEngine;
    private TemplateConfiguration templateConfiguration;

    @Before
    public void setup() {
        templateConfiguration = mock(TemplateConfiguration.class);
        templateEngine = new TemplateEngine(templateConfiguration);
    }

    @Test
    public void getTemplateTest() throws CluecumberPluginException {
        Template returnTemplate = mock(Template.class);
        when(returnTemplate.getName()).thenReturn("MyTemplate");
        when(templateConfiguration.getTemplate(TemplateEngine.Template.SCENARIO_DETAILS.getFileName())).thenReturn(returnTemplate);
        final Template template = templateEngine.getTemplate(TemplateEngine.Template.SCENARIO_DETAILS);
        assertThat(template.getName(), is("MyTemplate"));
    }
}
