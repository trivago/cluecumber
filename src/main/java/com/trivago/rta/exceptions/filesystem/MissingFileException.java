package com.trivago.rta.exceptions.filesystem;

import com.trivago.rta.exceptions.CluecumberPluginException;

/**
 * Thrown when a file is not found.
 */
public class MissingFileException extends CluecumberPluginException {
    /**
     * Constructor.
     *
     * @param fileName The name of the missing file.
     */
    public MissingFileException(final String fileName) {
        super("File '" + fileName + "' could not be found.");
    }
}
