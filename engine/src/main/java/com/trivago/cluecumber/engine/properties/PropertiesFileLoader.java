/*
 * Copyright 2023 trivago N.V.
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
package com.trivago.cluecumber.engine.properties;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.filesystem.FileIO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Loads a properties file.
 */
@Singleton
public class PropertiesFileLoader {

    private final FileIO fileIO;

    /**
     * The constructor for dependency injection.
     *
     * @param fileIO The {@link FileIO} instance.
     */
    @Inject
    public PropertiesFileLoader(final FileIO fileIO) {
        this.fileIO = fileIO;
    }

    /**
     * Loads a property file and returns it as a {@link LinkedHashMap}.
     *
     * @param propertiesFilePath The path to the properties file.
     * @return A {@link LinkedHashMap} of property keys and values.
     * @throws CluecumberException Thrown on any error.
     */
    LinkedHashMap<String, String> loadPropertiesMap(final String propertiesFilePath) throws CluecumberException {
        LinkedHashMap<String, String> propertiesMap;
        String content = fileIO.readContentFromFile(propertiesFilePath);
        LinkedProperties properties = new LinkedProperties();
        try {
            properties.load(new StringReader(content));
        } catch (IOException e) {
            throw new CluecumberException("Could not parse properties file '" + "': " + e.getMessage());
        }
        propertiesMap = properties.entrySet().stream().collect(Collectors.toMap(propertyEntry -> (String) propertyEntry.getKey(), propertyEntry -> (String) propertyEntry.getValue(), (a, b) -> b, LinkedHashMap::new));
        return propertiesMap;
    }
}
