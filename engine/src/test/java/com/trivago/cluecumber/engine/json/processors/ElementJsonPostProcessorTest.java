package com.trivago.cluecumber.engine.json.processors;

import com.trivago.cluecumber.engine.constants.MimeType;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Embedding;
import com.trivago.cluecumber.engine.json.pojo.ResultMatch;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ElementJsonPostProcessorTest {
    private ElementJsonPostProcessor elementJsonPostProcessor;

    @BeforeEach
    public void setup() {
        PropertyManager propertyManager = mock(PropertyManager.class);
        FileIO fileIO = mock(FileIO.class);
        CluecumberLogger logger = mock(CluecumberLogger.class);
        elementJsonPostProcessor = new ElementJsonPostProcessor(propertyManager, fileIO, logger);
    }

    @Test
    public void postDesiralizeAddScenarioIndexBackgroundScenarioTest() {
        Element element = new Element();
        assertEquals(element.getScenarioIndex(), 0);
        elementJsonPostProcessor.postDeserialize(element, null, null);
        assertEquals(element.getScenarioIndex(), 0);
    }

    @Test
    public void postDeserializeTest() {
        Element element = new Element();
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        List<Embedding> embeddings = new ArrayList<>();
        Embedding embedding = new Embedding();
        embedding.setMimeType(MimeType.PNG);
        embedding.setData("123");
        embeddings.add(embedding);
        step.setEmbeddings(embeddings);

        List<ResultMatch> before = new ArrayList<>();
        ResultMatch resultMatchBefore = new ResultMatch();
        before.add(resultMatchBefore);
        step.setBefore(before);

        List<ResultMatch> after = new ArrayList<>();
        ResultMatch resultMatchAfter = new ResultMatch();
        after.add(resultMatchAfter);
        step.setAfter(after);

        steps.add(step);
        element.setSteps(steps);

        List<ResultMatch> elementAfter = new ArrayList<>();
        elementAfter.add(resultMatchAfter);
        element.setAfter(elementAfter);

        assertEquals(embedding.getData(), "123");

        elementJsonPostProcessor.postDeserialize(element, null, null);
        assertEquals(embedding.getFilename(), "attachment001.png");
    }

    @Test
    public void postSerializeTest() {
        elementJsonPostProcessor.postSerialize(null, null, null);
    }
}
