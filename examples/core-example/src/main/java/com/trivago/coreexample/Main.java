package com.trivago.coreexample;

import com.trivago.cluecumber.core.CluecumberCore;
import com.trivago.cluecumber.engine.CluecumberEngine;
import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.dagger.DaggerCluecumberCoreGraph;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.logging.CluecumberLogger;

import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) throws CluecumberException {
        String jsonDirectory = "examples/core-example/json";
        String reportDirectory = "examples/core-example/target/cluecumber_report";

        new CluecumberCore.Builder()
                .setCustomPageTitle("My cool report")
                .build().generateReports(jsonDirectory, reportDirectory);
    }
}