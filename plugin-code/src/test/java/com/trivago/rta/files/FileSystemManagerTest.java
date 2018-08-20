package com.trivago.rta.files;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.exceptions.filesystem.PathCreationException;
import com.trivago.rta.filesystem.FileSystemManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileSystemManagerTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private FileSystemManager fileSystemManager;

    @Before
    public void setup() {
        fileSystemManager = new FileSystemManager();
    }

    @Test(expected = CluecumberPluginException.class)
    public void invalidSourceFeaturesTest() throws Exception {
        fileSystemManager.getJsonFilePaths("nonexistentpath");
    }

    @Test
    public void validSourceFeaturesTest() throws Exception {
        String jsonPath = testFolder.getRoot().getPath();
        fileSystemManager.getJsonFilePaths(jsonPath);
    }

    @Test(expected = PathCreationException.class)
    public void createInvalidDirectory() throws Exception {
        fileSystemManager.createDirectory("");
    }

    @Test(expected = CluecumberPluginException.class)
    public void exportResourceTest() throws Exception {
        fileSystemManager.copyResourceFromJar("resource", "");
    }
}
