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

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.exceptions.filesystem.PathCreationException;
import com.trivago.cluecumber.logging.CluecumberLogger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class FileSystemManager {

    private CluecumberLogger logger;

    @Inject
    public FileSystemManager(final CluecumberLogger logger) {
        this.logger = logger;
    }

    /**
     * Return a list of JSON files in a given directory.
     *
     * @param sourcePath The path in which to search for JSON files.
     * @return A list of JSON file paths.
     */
    public List<Path> getJsonFilePaths(final String sourcePath) {
        List<Path> jsonFilePaths = new ArrayList<>();
        try {
            jsonFilePaths =
                    Files.walk(Paths.get(sourcePath))
                            .filter(Files::isRegularFile)
                            .filter(p -> p.toString().toLowerCase().endsWith(".json"))
                            .collect(Collectors.toList());

        } catch (IOException e) {
            logger.warn("Unable to traverse JSON files in " + sourcePath);
        }
        return jsonFilePaths;
    }

    /**
     * Creates a directory if it does not exists.
     *
     * @param dirName Name of directory.
     */
    public void createDirectory(final String dirName) throws PathCreationException {
        File directory = new File(dirName);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new PathCreationException(dirName);
        }
    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName path to the embedded resource.
     * @param destination  full path to the destination resource.
     * @throws CluecumberPluginException see {@link CluecumberPluginException}.
     */
    public void copyResourceFromJar(final String resourceName, final String destination) throws CluecumberPluginException {
        final int BYTE_BLOCK = 4096;
        try (InputStream inputStream = this.getClass().getResourceAsStream(resourceName)) {
            int readBytes;
            byte[] buffer = new byte[BYTE_BLOCK];
            try (OutputStream outputStream = new FileOutputStream(destination)) {
                while ((readBytes = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readBytes);
                }
            } catch (Exception e) {
                throw new CluecumberPluginException("Cannot write resource '" + resourceName + "': " + e.getMessage());
            }
        } catch (Exception e) {
            throw new CluecumberPluginException("Cannot process resource '" + resourceName + "': " + e.getMessage());
        }
    }

    /**
     * Copy file to a new location.
     *
     * @param source      The source file.
     * @param destination The destination file.
     * @throws CluecumberPluginException see {@link CluecumberPluginException}.
     */
    public void copyResource(final String source, final String destination) throws CluecumberPluginException {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CluecumberPluginException("Cannot copy resource '" + source + "': " + e.getMessage());
        }
    }
}
