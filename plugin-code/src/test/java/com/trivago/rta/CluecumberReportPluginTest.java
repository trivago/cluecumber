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

import static org.mockito.Mockito.mock;

public class CluecumberReportPluginTest {

    private CluecumberReportPlugin cluecumberReportPlugin;

    @Before
    public void setup() throws CluecumberPluginException {
        CluecumberLogger cluecumberLogger = mock(CluecumberLogger.class);
        PropertyManager propertyManager = mock(PropertyManager.class);
        FileSystemManager fileSystemManager = mock(FileSystemManager.class);
        FileIO fileIO = mock(FileIO.class);
        JsonPojoConverter jsonPojoConverter = mock(JsonPojoConverter.class);
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
}
