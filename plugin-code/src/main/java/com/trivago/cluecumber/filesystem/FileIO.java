/*
 * Copyright 2019 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.cluecumber.filesystem;

import com.trivago.cluecumber.exceptions.filesystem.FileCreationException;
import com.trivago.cluecumber.exceptions.filesystem.MissingFileException;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

/**
 * This class manages reading from and writing to files.
 */
@Singleton
public class FileIO {
    /**
     * Write string content to a file.
     *
     * @param content  the string content to be written.
     * @param filePath the complete path to the target file.
     * @throws FileCreationException a {@link FileCreationException} in case the file cannot be created.
     */
    public void writeContentToFile(final String content, final String filePath) throws FileCreationException {
        try (PrintStream ps = new PrintStream(filePath)) {
            ps.println(content);
        } catch (IOException e) {
            throw new FileCreationException(filePath);
        }
    }

    /**
     * Write byte array content to a file.
     *
     * @param content  the byte array content to be written.
     * @param filePath the complete path to the target file.
     * @throws FileCreationException a {@link FileCreationException} in case the file cannot be created.
     */
    public void writeContentToFile(final byte[] content, final String filePath) throws FileCreationException {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, content);
        } catch (Exception e) {
            throw new FileCreationException(path.toString());
        }
    }

    /**
     * Read string content from a file.
     *
     * @param filePath the complete path to the source file.
     * @return the file contents as a string.
     * @throws MissingFileException a {@link MissingFileException} in case the file does not exist.
     */
    public String readContentFromFile(final String filePath) throws MissingFileException {
        try {
            byte[] bytes = readAllBytes(Paths.get(filePath));
            return new String(bytes).trim();
        } catch (IOException e) {
            throw new MissingFileException(filePath);
        }
    }

    /**
     * Check if a file exists.
     *
     * @param filePath the complete path to the file.
     * @return true if the file exists.
     */
    public boolean isExistingFile(final String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }
}
