package com.trivago.cluecumber;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.filesystem.FileSystemManager;
import com.trivago.cluecumber.json.JsonPojoConverter;
import com.trivago.cluecumber.json.processors.ElementIndexPreProcessor;
import com.trivago.cluecumber.logging.CluecumberLogger;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.ReportGenerator;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CluecumberReportPluginTest {

    private CluecumberReportPlugin cluecumberReportPlugin;
    private JsonPojoConverter jsonPojoConverter;
    private FileIO fileIO;

    @Before
    public void setup() throws CluecumberPluginException {
        CluecumberLogger cluecumberLogger = mock(CluecumberLogger.class);
        PropertyManager propertyManager = mock(PropertyManager.class);

        FileSystemManager fileSystemManager = mock(FileSystemManager.class);
        List<Path> fileList = new ArrayList<>();
        Path path = mock(Path.class);
        fileList.add(path);
        when(fileSystemManager.getJsonFilePaths(anyString())).thenReturn(fileList);

        fileIO = mock(FileIO.class);
        jsonPojoConverter = mock(JsonPojoConverter.class);
        ElementIndexPreProcessor elementIndexPostProcessor = mock(ElementIndexPreProcessor.class);
        ReportGenerator reportGenerator = mock(ReportGenerator.class);

        cluecumberReportPlugin = new CluecumberReportPlugin(
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
    public void executeTest() throws CluecumberPluginException {
        cluecumberReportPlugin.execute();
    }

    @Test
    public void noErrorOnUnparsableJsonTest() throws CluecumberPluginException {
        when(fileIO.readContentFromFile(any())).thenReturn("json");
        when(jsonPojoConverter.convertJsonToReportPojos("json")).thenThrow(new CluecumberPluginException("failure"));
        cluecumberReportPlugin.execute();
    }
}
