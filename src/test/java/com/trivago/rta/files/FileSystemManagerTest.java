package com.trivago.rta.files;

import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.properties.PropertyManager;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class FileSystemManagerTest {
    private PropertyManager propertyManager;
    private FileSystemManager fileSystemManager;

    @Before
    public void setup() {
        propertyManager = mock(PropertyManager.class);
        fileSystemManager = new FileSystemManager(propertyManager);
    }
}
