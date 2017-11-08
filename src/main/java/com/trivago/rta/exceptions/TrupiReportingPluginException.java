package com.trivago.rta.exceptions;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * The base Trupi Reporting plugin exception that all exceptions extend.
 * Since this extends the {@link MojoExecutionException},
 * it will stop the plugin execution when thrown.
 */
public class TrupiReportingPluginException extends MojoExecutionException {
    /**
     * Constructor.
     *
     * @param message The error message for the exception.
     */
    public TrupiReportingPluginException(final String message) {
        super(message);
    }
}
