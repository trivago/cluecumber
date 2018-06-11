package com.trivago.rta.json.postprocessors;

import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Embedding;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ElementPostProcessorTest {
    private ElementPostProcessor elementPostProcessor;

    @Before
    public void setup() {
        PropertyManager propertyManager = mock(PropertyManager.class);
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

    @Test
    public void postDeserializeTest(){
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        List<Embedding> embeddings = new ArrayList<>();
        Embedding embedding = new Embedding();
        embedding.setMimeType("image/png");
        embedding.setData("123");
        embeddings.add(embedding);
        step.setEmbeddings(embeddings);
        steps.add(step);
        element.setSteps(steps);

        assertThat(embedding.getData(), is("123"));

        elementPostProcessor.postDeserialize(element, null, null);

        assertThat(embedding.getData(), is(""));
        assertThat(embedding.getFilename(), is("attachment000.png"));
    }

    @Test
    public void postSerializeTest(){
        elementPostProcessor.postSerialize(null, null, null);
    }
}
