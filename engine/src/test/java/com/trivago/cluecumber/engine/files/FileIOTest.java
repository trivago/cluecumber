package com.trivago.cluecumber.engine.files;


import com.trivago.cluecumber.engine.exceptions.filesystem.FileCreationException;
import com.trivago.cluecumber.engine.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileIOTest {
    private final FileIO fileIO = new FileIO();

    @TempDir
    private Path testFolder;

    @Test
    public void writeToInvalidFileTest() {
        assertThrows(FileCreationException.class, () -> fileIO.writeContentToFile((byte[]) null, ""));
    }

    @Test
    public void fileReadWriteTest() throws FileCreationException, MissingFileException {
        String testString = "This is a test!";
        String path = testFolder.toString().concat("/test.tmp");
        fileIO.writeContentToFile(testString, path);
        assertEquals(fileIO.readContentFromFile(path), testString);
    }

    @Test
    public void writeContentToFileStringInvalidTest() {
        String testString = "This is a test!";
        String path = testFolder.toString().substring(1);
        assertThrows(FileCreationException.class, () -> fileIO.writeContentToFile(testString, path));
    }

    @Test
    public void writeContentToFileByteArrayInvalidTest() {
        String path = testFolder.toString();
        assertThrows(FileCreationException.class, () -> fileIO.writeContentToFile(new byte[]{}, path));
    }

    @Test
    public void readFromMissingFileTest() {
        String wrongPath = testFolder.toString().concat("/missing.tmp");
        assertThrows(MissingFileException.class, () -> fileIO.readContentFromFile(wrongPath));
    }

    @Test
    public void isExistingFileWrongFileTest() {
        assertFalse(fileIO.isExistingFile("nonexistent"));
    }

    @Test
    public void isExistingFileExistingFileTest() {
        assertTrue(fileIO.isExistingFile("src/test/resources/test.ftl"));
    }
}
