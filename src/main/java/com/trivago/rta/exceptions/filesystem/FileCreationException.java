package com.trivago.rta.exceptions.filesystem;

import com.trivago.rta.exceptions.TrupiReportingPluginException;

/**
 * Thrown when a file cannot be created.
 */
public class FileCreationException extends TrupiReportingPluginException {
    /**
     * Constructor.
     *
     * @param fileName The file to be created.
     */
    public FileCreationException(final String fileName) {
        super("File '" + fileName + "' could not be created.");
    }
}
