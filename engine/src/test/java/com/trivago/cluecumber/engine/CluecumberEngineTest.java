package com.trivago.cluecumber.engine;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.filesystem.FileSystemManager;
import com.trivago.cluecumber.engine.json.JsonPojoConverter;
import com.trivago.cluecumber.engine.json.processors.ElementIndexPreProcessor;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CluecumberEngineTest {

    private CluecumberEngine cluecumberEngine;
    private FileIO fileIO;
    private JsonPojoConverter jsonPojoConverter;

    @BeforeEach
    public void setup() {
        CluecumberLogger cluecumberLogger = new CluecumberLogger();
        PropertyManager propertyManager = Mockito.mock(PropertyManager.class);

        FileSystemManager fileSystemManager = Mockito.mock(FileSystemManager.class);
        List<Path> fileList = new ArrayList<>();
        Path path = Mockito.mock(Path.class);
        fileList.add(path);
        Mockito.when(fileSystemManager.getJsonFilePaths(ArgumentMatchers.anyString())).thenReturn(fileList);

        fileIO = Mockito.mock(FileIO.class);
        jsonPojoConverter = Mockito.mock(JsonPojoConverter.class);
        ElementIndexPreProcessor elementIndexPostProcessor = Mockito.mock(ElementIndexPreProcessor.class);
        ReportGenerator reportGenerator = Mockito.mock(ReportGenerator.class);

        cluecumberEngine = new CluecumberEngine(
                cluecumberLogger,
                propertyManager,
                fileSystemManager,
                fileIO,
                jsonPojoConverter,
                elementIndexPostProcessor,
                reportGenerator
        );
    }

    @Test
    void invocation() throws CluecumberException {
        cluecumberEngine.build("", "");
    }

    @Test
    public void noErrorOnUnparsableJsonTest() throws CluecumberException {
        Mockito.when(fileIO.readContentFromFile(ArgumentMatchers.any())).thenReturn("json");
        Mockito.when(jsonPojoConverter.convertJsonToReportPojos("json")).thenThrow(new CluecumberException("failure"));
        cluecumberEngine.build("", "");
    }
}
