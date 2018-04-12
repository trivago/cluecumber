package com.trivago.rta.rendering;

import com.trivago.rta.exceptions.CluecumberPluginException;
import org.junit.Before;
import org.junit.Test;

public class TemplateConfigurationTest {

    private TemplateConfiguration templateConfiguration;

    @Before
    public void setup() {
        templateConfiguration = new TemplateConfiguration();
    }

    @Test
    public void validInitTest() {
        templateConfiguration.init(this.getClass(), "bla");
    }

    @Test(expected = CluecumberPluginException.class)
    public void getNonexistentTemplateTest() throws CluecumberPluginException {
        templateConfiguration.getTemplate("testTemplate");
    }
}
