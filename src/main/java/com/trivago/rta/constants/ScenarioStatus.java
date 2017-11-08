package com.trivago.rta.constants;

public enum ScenarioStatus {
    PASSED("passed"), FAILED("failed"), SKIPPED("skipped");

    private final String status;

    ScenarioStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
