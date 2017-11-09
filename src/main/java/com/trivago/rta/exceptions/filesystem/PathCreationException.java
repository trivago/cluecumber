package com.trivago.rta.exceptions.filesystem;

import com.trivago.rta.exceptions.CluecumberPluginException;

/**
 * Thrown when a path cannot be created.
 */
public class PathCreationException extends CluecumberPluginException {
    /**
     * Constructor.
     *
     * @param path The path to be created.
     */
    public PathCreationException(final String path) {
        super("Path '" + path + "' could not be created.");
    }
}
