package com.trivago.coreexample;

import com.trivago.cluecumber.core.CluecumberCore;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;

public class Main {
    public static void main(String[] args) throws CluecumberException {
        String jsonDirectory = "examples/json";
        String reportDirectory = "examples/core-example/target/cluecumber_report";

        new CluecumberCore.Builder()
                .setCustomPageTitle("My cool report")
                .setGroupPreviousScenarioRuns(true)
                .build().generateReports(jsonDirectory, reportDirectory);
    }
}