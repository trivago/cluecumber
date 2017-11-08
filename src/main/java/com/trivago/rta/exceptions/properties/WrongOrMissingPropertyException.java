package com.trivago.rta.exceptions.properties;


import com.trivago.rta.exceptions.TrupiReportingPluginException;

/**
 * Thrown when an expected plugin property is not found or wrong
 * (typically set inside a configuration block within the pom file).
 */
public class WrongOrMissingPropertyException extends TrupiReportingPluginException {
    /**
     * Constructor.
     *
     * @param property The name of the missing property.
     */
    public WrongOrMissingPropertyException(final String property) {
        super("Property '" + property
                + "' is not specified in the configuration section "
                + "of your pom file or contains an invalid value.");
    }
}
