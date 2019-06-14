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
import com.trivago.cluecumber.exceptions.filesystem.MissingFileException;
import com.trivago.cluecumber.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.logging.CluecumberLogger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;

@Singleton
public class PropertyManager {

    private final CluecumberLogger logger;
    private FileIO fileIO;
    private PropertiesFileLoader propertiesFileLoader;

    private String sourceJsonReportDirectory;
    private String generatedHtmlReportDirectory;
    private Map<String, String> customParameters = new LinkedHashMap<>();
    private boolean failScenariosOnPendingOrUndefinedSteps;
    private boolean expandBeforeAfterHooks;
    private boolean expandStepHooks;
    private boolean expandDocStrings;
    private String customCssFile;
    private String customParametersFile;

    @Inject
    public PropertyManager(
            final CluecumberLogger logger,
            FileIO fileIO,
            final PropertiesFileLoader propertiesFileLoader
    ) {
        this.logger = logger;
        this.fileIO = fileIO;
        this.propertiesFileLoader = propertiesFileLoader;
    }

    public String getSourceJsonReportDirectory() {
        return sourceJsonReportDirectory;
    }

    public void setSourceJsonReportDirectory(final String sourceJsonReportDirectory)
            throws WrongOrMissingPropertyException {

        if (!isSet(sourceJsonReportDirectory)) {
            throw new WrongOrMissingPropertyException("sourceJsonReportDirectory");
        }
        this.sourceJsonReportDirectory = sourceJsonReportDirectory;
    }

    public String getGeneratedHtmlReportDirectory() {
        return generatedHtmlReportDirectory;
    }

    public void setGeneratedHtmlReportDirectory(final String generatedHtmlReportDirectory) throws WrongOrMissingPropertyException {
        if (!isSet(generatedHtmlReportDirectory)) {
            throw new WrongOrMissingPropertyException("generatedHtmlReportDirectory");
        }
        this.generatedHtmlReportDirectory = generatedHtmlReportDirectory;
    }

    public Map<String, String> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(final Map<String, String> customParameters) {
        this.customParameters.putAll(customParameters);
    }

    String getCustomParametersFile() {
        return customParametersFile;
    }

    public void setCustomParametersFile(String customParametersFile) throws CluecumberPluginException {
        if (isSet(customParametersFile) && !fileIO.isExistingFile(customParametersFile)) {
            throw new MissingFileException(customParametersFile);
        }
        this.customParametersFile = customParametersFile;
        Map<String, String> customParameters = propertiesFileLoader.loadPropertiesMap(customParametersFile);
        this.customParameters.putAll(customParameters);
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

    public String getCustomCssFile() {
        return customCssFile;
    }

    public void setCustomCssFile(final String customCssFile) throws MissingFileException {
        if (isSet(customCssFile) && !fileIO.isExistingFile(customCssFile)) {
            throw new MissingFileException(customCssFile);
        }
        this.customCssFile = customCssFile;
    }

    public void logProperties() {
        logger.info("- source JSON report directory     : " + sourceJsonReportDirectory);
        logger.info("- generated HTML report directory  : " + generatedHtmlReportDirectory);

        boolean customParametersFileExists = customParametersFile != null && !customParametersFile.isEmpty();
        if (customParametersFileExists) {
            logger.logSeparator();
            logger.info("- custom parameters file           : " + customParametersFile);
        }

        if (customParameters != null && !customParameters.isEmpty()) {
            if (!customParametersFileExists) {
                logger.logSeparator();
            }
            for (Map.Entry<String, String> entry : customParameters.entrySet()) {
                logger.info("- custom parameter                 : " +
                        entry.getKey() + " -> " + entry.getValue());
            }
        }

        logger.logSeparator();

        logger.info("- fail pending/undefined scenarios : " + failScenariosOnPendingOrUndefinedSteps);
        logger.info("- expand before/after hooks        : " + expandBeforeAfterHooks);
        logger.info("- expand step hooks                : " + expandStepHooks);
        logger.info("- expand doc strings               : " + expandDocStrings);

        if (customCssFile != null && !customCssFile.isEmpty()) {
            logger.info("- custom CSS file              : " + customCssFile);
        }

        logger.logSeparator();
    }

    private boolean isSet(final String string) {
        return string != null && !string.trim().isEmpty();
    }
}
