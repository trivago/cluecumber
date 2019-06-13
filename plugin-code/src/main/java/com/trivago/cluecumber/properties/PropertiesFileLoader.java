package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.filesystem.FileIO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class PropertiesFileLoader {

    private FileIO fileIO;

    @Inject
    public PropertiesFileLoader(final FileIO fileIO) {
        this.fileIO = fileIO;
    }

    public Map<String, String> loadPropertiesMap(final String propertiesFilePath) throws MissingFileException {
        Map<String, String> properties = null;
        String content = fileIO.readContentFromFile(propertiesFilePath);
        System.out.println(content);
        return properties;
    }
}
