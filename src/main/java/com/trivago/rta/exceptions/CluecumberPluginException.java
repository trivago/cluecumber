package com.trivago.rta.exceptions;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * The base Cluecumber Report plugin exception that all exceptions extend.
 * Since this extends the {@link MojoExecutionException},
 * it will stop the plugin execution when thrown.
 */
public class CluecumberPluginException extends MojoExecutionException {
    /**
     * Constructor.
     *
     * @param message The error message for the exception.
     */
    public CluecumberPluginException(final String message) {
        super(message);
    }
}
