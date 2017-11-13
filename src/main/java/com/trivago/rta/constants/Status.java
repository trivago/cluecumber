package com.trivago.rta.constants;

public enum Status {
    PASSED("passed"), FAILED("failed"), SKIPPED("skipped");

    private final String status;

    Status(final String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }

    public static Status fromString(String status) {
        return valueOf(status.toUpperCase());
    }
}
