package com.trivago.rta;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.json.JsonPojoConverter;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.ReportGenerator;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
        when(fileSystemManager.getJsonFilePaths()).thenReturn(fileList);

        fileIO = mock(FileIO.class);
        jsonPojoConverter = mock(JsonPojoConverter.class);
        ReportGenerator reportGenerator = mock(ReportGenerator.class);
        cluecumberReportPlugin = new CluecumberReportPlugin(
                cluecumberLogger,
                propertyManager,
                fileSystemManager,
                fileIO,
                jsonPojoConverter,
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
