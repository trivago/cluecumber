package com.trivago.cluecumber.files;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.exceptions.filesystem.PathCreationException;
import com.trivago.cluecumber.filesystem.FileSystemManager;
import com.trivago.cluecumber.logging.CluecumberLogger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class FileSystemManagerTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private FileSystemManager fileSystemManager;

    @Before
    public void setup() {
        CluecumberLogger logger = mock(CluecumberLogger.class);
        fileSystemManager = new FileSystemManager(logger);
    }

    @Test
    public void invalidSourceFeaturesTest() {
        fileSystemManager.getJsonFilePaths("nonexistentpath");
    }

    @Test
    public void validSourceFeaturesTest() {
        String jsonPath = testFolder.getRoot().getPath();
        fileSystemManager.getJsonFilePaths(jsonPath);
    }

    @Test(expected = PathCreationException.class)
    public void createInvalidDirectory() throws Exception {
        fileSystemManager.createDirectory("");
    }

    @Test(expected = CluecumberPluginException.class)
    public void copyInvalidResourceFromJarTest() throws Exception {
        fileSystemManager.copyResourceFromJar("resource", "");
    }

    @Test
    public void copyResourceFromJarTest() throws Exception {
        String newLocation = testFolder.getRoot().getPath().concat("/test.ftl");
        fileSystemManager.copyResourceFromJar("/test.ftl", newLocation);
        assertThat(new File(newLocation).isFile(), is(true));
    }

    @Test(expected = CluecumberPluginException.class)
    public void copyResourceTest() throws Exception {
        fileSystemManager.copyResource("resource", "");
    }
}
