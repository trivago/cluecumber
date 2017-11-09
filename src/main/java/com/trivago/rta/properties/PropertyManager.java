package com.trivago.rta.properties;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.rta.logging.CluecumberLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PropertyManager {

    private final CluecumberLogger logger;

    private String sourceJsonReportDirectory;
    private String generatedHtmlReportDirectory;

    @Inject
    public PropertyManager(final CluecumberLogger logger) {
        this.logger = logger;
    }

    public String getSourceJsonReportDirectory() {
        return sourceJsonReportDirectory;
    }

    public void setSourceJsonReportDirectory(final String reportDirectory) {
        this.sourceJsonReportDirectory = reportDirectory;
    }

    public String getGeneratedHtmlReportDirectory() {
        return generatedHtmlReportDirectory;
    }

    public void setGeneratedHtmlReportDirectory(final String generatedHtmlReportDirectory) {
        this.generatedHtmlReportDirectory = generatedHtmlReportDirectory;
    }

    /**
     * Checks the pom settings for the plugin.
     *
     * @throws CluecumberPluginException Thrown when a required setting
     *                                       is not specified in the pom.
     */
    public void validateSettings() throws CluecumberPluginException {
        String missingProperty = null;
        if (sourceJsonReportDirectory.equals("")) {
            missingProperty = "sourceJsonReportDirectory";
        } else if (generatedHtmlReportDirectory.equals("")) {
            missingProperty = "generatedHtmlReportDirectory";
        }

        if (missingProperty != null) {
            throw new WrongOrMissingPropertyException(missingProperty);
        }
    }

    public void logProperties() {
        logger.info("─ sourceJsonReportDirectory     : " + sourceJsonReportDirectory);
        logger.info("─ generatedHtmlReportDirectory  : " + generatedHtmlReportDirectory);
    }
}
