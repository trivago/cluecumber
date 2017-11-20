package com.trivago.rta.json.postprocessors;

import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ElementPostProcessorTest {
    private PropertyManager propertyManager;
    private FileIO fileIO;
    private CluecumberLogger logger;
    private ElementPostProcessor elementPostProcessor;

    @Before
    public void setup() {
        propertyManager = mock(PropertyManager.class);
        FileIO fileIO = mock(FileIO.class);
        CluecumberLogger logger = new CluecumberLogger();
        elementPostProcessor = new ElementPostProcessor(propertyManager, fileIO, logger);
    }

    @Test
    public void postDesiralizeAddScenarioIndexTest(){
        Element element = new Element();
        assertThat(element.getScenarioIndex(), is(-1));
        elementPostProcessor.postDeserialize(element, null, null);
        assertThat(element.getScenarioIndex(), is(0));
    }
}
