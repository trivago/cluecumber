/*
 * Copyright 2018 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.cluecumber.properties;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.logging.CluecumberLogger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class PropertyManager {

    private final CluecumberLogger logger;

    private String skip;
    private String sourceJsonReportDirectory;
    private String generatedHtmlReportDirectory;
    private Map<String, String> customParameters;
    private String customCss;

    @Inject
    public PropertyManager(final CluecumberLogger logger) {
        this.logger = logger;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
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

    public Map<String, String> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(final Map<String, String> customParameters) {
        this.customParameters = customParameters;
    }

    public String getCustomCss() {
        return customCss;
    }

    public void setCustomCss(final String customCss) {
        this.customCss = customCss;
    }

    /**
     * Checks the pom settings for the plugin.
     *
     * @throws CluecumberPluginException Thrown when a required setting
     *                                   is not specified in the pom.
     */
    public void validateSettings() throws CluecumberPluginException {
        String missingProperty = null;
        if (sourceJsonReportDirectory == null || sourceJsonReportDirectory.equals("")) {
            missingProperty = "sourceJsonReportDirectory";
        } else if (generatedHtmlReportDirectory == null || generatedHtmlReportDirectory.equals("")) {
            missingProperty = "generatedHtmlReportDirectory";
        }

        if (missingProperty != null) {
            throw new WrongOrMissingPropertyException(missingProperty);
        }
    }

    public void logProperties() {
        logger.info("- skip report generation    : " + skip);
        logger.info("- source JSON report directory    : " + sourceJsonReportDirectory);
        logger.info("- generated HTML report directory : " + generatedHtmlReportDirectory);

        if (customParameters != null && !customParameters.isEmpty()) {
            for (Map.Entry<String, String> entry : customParameters.entrySet()) {
                logger.info("- custom parameter                : " +
                        entry.getKey() + " -> " + entry.getValue());
            }
        }

        if (customCss != null && !customCss.isEmpty()) {
            logger.info("- custom CSS                      : " + customCss);
        }

        logger.info("------------------------------------------------------------------------");
    }
}
