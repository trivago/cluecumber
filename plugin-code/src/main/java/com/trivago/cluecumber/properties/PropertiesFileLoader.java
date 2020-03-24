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

package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Singleton
public class PropertiesFileLoader {

    private final FileIO fileIO;

    @Inject
    public PropertiesFileLoader(final FileIO fileIO) {
        this.fileIO = fileIO;
    }

    LinkedHashMap<String, String> loadPropertiesMap(final String propertiesFilePath) throws CluecumberPluginException {
        LinkedHashMap<String, String> propertiesMap;
        String content = fileIO.readContentFromFile(propertiesFilePath);
        LinkedProperties properties = new LinkedProperties();
        try {
            properties.load(new StringReader(content));
        } catch (IOException e) {
            throw new CluecumberPluginException("Could not parse properties file '" + "': " + e.getMessage());
        }
        propertiesMap = properties.entrySet().stream().collect(Collectors.toMap(propertyEntry -> (String) propertyEntry.getKey(), propertyEntry -> (String) propertyEntry.getValue(), (a, b) -> b, LinkedHashMap::new));
        return propertiesMap;
    }
}
