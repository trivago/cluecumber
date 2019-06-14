package com.trivago.cluecumber.files;

import com.trivago.cluecumber.exceptions.filesystem.FileCreationException;
import com.trivago.cluecumber.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.filesystem.FileIO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FileIOTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private FileIO fileIO = new FileIO();

    @Test(expected = FileCreationException.class)
    public void writeToInvalidFileTest() throws Exception {
        fileIO.writeContentToFile((byte[]) null, "");
    }

    @Test
    public void fileReadWriteTest() throws Exception {
        String testString = "This is a test!";
        String path = testFolder.getRoot().getPath().concat("/test.tmp");
        fileIO.writeContentToFile(testString, path);
        assertThat(fileIO.readContentFromFile(path), is(testString));
    }

    @Test(expected = FileCreationException.class)
    public void writeContentToFileStringInvalidTest() throws Exception {
        String testString = "This is a test!";
        String path = testFolder.getRoot().getPath().substring(1);
        fileIO.writeContentToFile(testString, path);
    }

    @Test(expected = FileCreationException.class)
    public void writeContentToFileByteArrayInvalidTest() throws Exception {
        String path = testFolder.getRoot().getPath();
        fileIO.writeContentToFile(new byte[]{}, path);
    }

    @Test(expected = MissingFileException.class)
    public void readFromMissingFileTest() throws Exception {
        String wrongPath = testFolder.getRoot().getPath().concat("/missing.tmp");
        fileIO.readContentFromFile(wrongPath);
    }

    @Test
    public void isExistingFileWrongFileTest() {
        assertThat(fileIO.isExistingFile("nonexistent"), is(false));
    }

    @Test
    public void isExistingFileExistingFileTest() {
        assertThat(fileIO.isExistingFile("src/test/resources/test.ftl"), is(true));
    }
}
