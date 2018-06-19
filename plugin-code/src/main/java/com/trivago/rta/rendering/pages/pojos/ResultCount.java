package com.trivago.rta.rendering.pages.pojos;

public class ResultCount {
    private int passed;
    private int failed;
    private int skipped;

    public ResultCount() {
        this.passed = 0;
        this.failed = 0;
        this.skipped = 0;
    }

    public void addPassed(int passedCount) {
        this.passed += passedCount;
    }

    public void addFailed(int failedCount) {
        this.failed += failedCount;
    }

    public void addSkipped(int skippedCount) {
        this.skipped += skippedCount;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getTotal() {
        return passed + failed + skipped;
    }

    @Override
    public String toString() {
        return "ResultCount{" +
                "passed=" + passed +
                ", failed=" + failed +
                ", skipped=" + skipped +
                '}';
    }
}
