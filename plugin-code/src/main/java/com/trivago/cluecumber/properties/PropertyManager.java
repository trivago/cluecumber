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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Singleton
public class PropertyManager {

    private final CluecumberLogger logger;

    private String sourceJsonReportDirectory;
    private String generatedHtmlReportDirectory;
    private Map<String, String> customParameters;
    private String customParametersFile;
    private boolean failScenariosOnPendingOrUndefinedSteps;
    private boolean expandBeforeAfterHooks;
    private boolean expandStepHooks;
    private boolean expandDocStrings;
    private String customCss;

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

    public Map<String, String> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(final Map<String, String> customParameters) {
        this.customParameters = customParameters;
    }

    public String getCustomParametersFile() {
        return customParametersFile;
    }

    public void setCustomParametersFile(final String customParametersFile) {
        this.customParametersFile = customParametersFile;
    }
    
    public boolean isFailScenariosOnPendingOrUndefinedSteps() {
        return failScenariosOnPendingOrUndefinedSteps;
    }

    public void setFailScenariosOnPendingOrUndefinedSteps(final boolean failScenariosOnPendingOrUndefinedSteps) {
        this.failScenariosOnPendingOrUndefinedSteps = failScenariosOnPendingOrUndefinedSteps;
    }

    public boolean isExpandBeforeAfterHooks() {
        return expandBeforeAfterHooks;
    }

    public void setExpandBeforeAfterHooks(final boolean expandBeforeAfterHooks) {
        this.expandBeforeAfterHooks = expandBeforeAfterHooks;
    }

    public boolean isExpandStepHooks() {
        return expandStepHooks;
    }

    public void setExpandStepHooks(final boolean expandStepHooks) {
        this.expandStepHooks = expandStepHooks;
    }

    public boolean isExpandDocStrings() {
        return expandDocStrings;
    }

    public void setExpandDocStrings(final boolean expandDocStrings) {
        this.expandDocStrings = expandDocStrings;
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
        } else if(customParametersFile != null && !customParametersFile.trim().isEmpty() && !Files.isReadable(Paths.get(customParametersFile))) {
            missingProperty = "customParametersFile";
        }

        if (missingProperty != null) {
            throw new WrongOrMissingPropertyException(missingProperty);
        }
    }

    public void logProperties() {
        logger.info("- source JSON report directory                : " + sourceJsonReportDirectory);
        logger.info("- generated HTML report directory             : " + generatedHtmlReportDirectory);

        if (customParameters != null && !customParameters.isEmpty()) {
            logger.logSeparator();
            for (Map.Entry<String, String> entry : customParameters.entrySet()) {
                logger.info("- custom parameter                           : " +
                        entry.getKey() + " -> " + entry.getValue());
            }
        }

        logger.logSeparator();

        logger.info("- fail scenarios with pending/undefined steps : " + failScenariosOnPendingOrUndefinedSteps);
        logger.info("- expand before/after hooks                   : " + expandBeforeAfterHooks);
        logger.info("- expand step hooks                           : " + expandStepHooks);
        logger.info("- expand doc strings                          : " + expandDocStrings);

        if (customCss != null && !customCss.isEmpty()) {
            logger.info("- custom CSS                                  : " + customCss);
        }

        logger.logSeparator();
    }
    
    public void initCustomParamatersFromFile() {
        customParameters = customParameters == null ? new HashMap<String, String>() : customParameters; 
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(customParametersFile));
        }
        catch (IOException e) {
            logger.error("Error loading properties from file '" + customParametersFile + "': " + e.getMessage());
        }
        
        properties.entrySet().forEach(e -> customParameters.put((String) e.getKey(), (String) e.getValue()));
    }
}
