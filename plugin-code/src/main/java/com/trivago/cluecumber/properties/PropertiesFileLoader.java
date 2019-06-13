package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.filesystem.FileIO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Singleton
public class PropertiesFileLoader {

    private FileIO fileIO;

    @Inject
    public PropertiesFileLoader(final FileIO fileIO) {
        this.fileIO = fileIO;
    }

    LinkedHashMap<String, String> loadPropertiesMap(final String propertiesFilePath) throws CluecumberPluginException {
        LinkedHashMap<String, String> propertiesMap = new LinkedHashMap<>();
        String content = fileIO.readContentFromFile(propertiesFilePath);
        LinkedProperties properties = new LinkedProperties();
        try {
            properties.load(new StringReader(content));
        } catch (IOException e) {
            throw new CluecumberPluginException("Could not parse properties file '" + "': " + e.getMessage());
        }
        for (Map.Entry<Object, Object> propertyEntry : properties.entrySet()) {
            propertiesMap.put((String) propertyEntry.getKey(), (String) propertyEntry.getValue());
        }
        return propertiesMap;
    }
}
