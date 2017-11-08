package com.trivago.rta.filesystem;


import com.trivago.rta.exceptions.filesystem.FileCreationException;
import com.trivago.rta.exceptions.filesystem.MissingFileException;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

@Singleton
public class FileIO {
    public void writeContentToFile(final String content, final String filePath) throws FileCreationException {
        try (PrintStream ps = new PrintStream(filePath)) {
            ps.println(content);
        } catch (IOException e) {
            throw new FileCreationException(filePath);
        }
    }

    public void writeContentToFile(final byte[] content, final String filePath) throws FileCreationException {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, content);
        } catch (Exception e) {
            throw new FileCreationException(path.toString());
        }
    }

    public String readContentFromFile(final String filePath) throws MissingFileException {
        try {
            byte[] bytes = readAllBytes(Paths.get(filePath));
            return new String(bytes).trim();
        } catch (IOException e) {
            throw new MissingFileException(filePath);
        }
    }
}
