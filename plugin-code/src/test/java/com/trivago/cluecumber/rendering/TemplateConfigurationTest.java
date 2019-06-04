package com.trivago.cluecumber.rendering;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.rendering.pages.templates.TemplateConfiguration;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TemplateConfigurationTest {

    private TemplateConfiguration templateConfiguration;

    @Before
    public void setup() {
        templateConfiguration = new TemplateConfiguration();
    }

    @Test
    public void validInitTest() {
        templateConfiguration.init("bla");
    }

    @Test(expected = CluecumberPluginException.class)
    public void getNonexistentTemplateTest() throws CluecumberPluginException {
        templateConfiguration.getTemplate("testTemplate");
    }

    @Test
    public void getExistentTemplateTest() throws CluecumberPluginException {
        templateConfiguration.init("/");
        Template template = templateConfiguration.getTemplate("test");
        assertThat(template.toString(), is("${test}"));
    }
}
