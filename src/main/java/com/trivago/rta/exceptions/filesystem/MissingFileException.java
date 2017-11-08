package com.trivago.rta.exceptions.filesystem;

import com.trivago.rta.exceptions.TrupiReportingPluginException;

/**
 * Thrown when a file is not found.
 */
public class MissingFileException extends TrupiReportingPluginException {
    /**
     * Constructor.
     *
     * @param fileName The name of the missing file.
     */
    public MissingFileException(final String fileName) {
        super("File '" + fileName + "' could not be found.");
    }
}
