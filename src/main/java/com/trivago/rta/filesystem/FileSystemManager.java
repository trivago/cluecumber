/*
 * Copyright 2017 trivago N.V.
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

package com.trivago.rta.filesystem;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.exceptions.filesystem.PathCreationException;
import com.trivago.rta.properties.PropertyManager;

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
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class FileSystemManager {

    private static final int BYTE_BLOCK = 4096;
    private final PropertyManager propertyManager;

    @Inject
    public FileSystemManager(final PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public List<Path> getJsonFilePaths() throws CluecumberPluginException {
        String sourceJsonReportDirectory = propertyManager.getSourceJsonReportDirectory();
        List<Path> jsonFilePaths;
        try {
            jsonFilePaths =
                    Files.walk(Paths.get(sourceJsonReportDirectory))
                            .filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".json"))
                            .collect(Collectors.toList());

        } catch (IOException e) {
            throw new CluecumberPluginException(
                    "Unable to traverse JSON files in " + sourceJsonReportDirectory);
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
     * @param baseClass    jar base class.
     * @param resourceName path to the embedded resource.
     * @param destination  full path to the destination resource.
     * @throws CluecumberPluginException (see {@link CluecumberPluginException}.
     */
    public void exportResource(final Class baseClass, final String resourceName, final String destination) throws CluecumberPluginException {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = baseClass.getResourceAsStream(resourceName);
            if (stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[BYTE_BLOCK];
            resStreamOut = new FileOutputStream(destination);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception e) {
            throw new CluecumberPluginException(e.getMessage());
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (resStreamOut != null) {
                    resStreamOut.close();
                }
            } catch (IOException e) {
                throw new CluecumberPluginException(e.getMessage());
            }
        }
    }
}
