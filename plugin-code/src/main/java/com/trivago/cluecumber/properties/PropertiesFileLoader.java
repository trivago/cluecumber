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

    private FileIO fileIO;

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
