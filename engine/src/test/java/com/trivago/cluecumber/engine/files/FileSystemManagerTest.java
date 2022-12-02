package com.trivago.cluecumber.engine.files;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.exceptions.filesystem.PathCreationException;
import com.trivago.cluecumber.engine.filesystem.FileSystemManager;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class FileSystemManagerTest {
    @TempDir
    private Path testFolder;
    private FileSystemManager fileSystemManager;

    @BeforeEach
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
        String jsonPath = testFolder.toString();
        fileSystemManager.getJsonFilePaths(jsonPath);
    }

    @Test
    public void createInvalidDirectory() {
        assertThrows(PathCreationException.class, () -> fileSystemManager.createDirectory(""));
    }

    @Test
    public void copyInvalidResourceFromJarTest() {
        assertThrows(CluecumberException.class, () -> fileSystemManager.copyResourceFromJar("resource", ""));
    }

    @Test
    public void copyResourceFromJarTest() throws CluecumberException {
        String newLocation = testFolder.toString().concat("/test.ftl");
        fileSystemManager.copyResourceFromJar("/test.ftl", newLocation);
        assertTrue(new File(newLocation).isFile());
    }

    @Test
    public void copyResourceTest() {
        assertThrows(CluecumberException.class, () -> fileSystemManager.copyResource("resource", ""));
    }
}
