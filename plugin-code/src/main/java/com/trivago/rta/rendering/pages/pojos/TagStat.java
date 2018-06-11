package com.trivago.rta.rendering.pages.pojos;

public class TagStat {
    private int passed;
    private int failed;
    private int skipped;

    TagStat() {
        this.passed = 0;
        this.failed = 0;
        this.skipped = 0;
    }

    void addPassed(int passedCount) {
        this.passed += passedCount;
    }

    void addFailed(int failedCount) {
        this.failed += failedCount;
    }

    void addSkipped(int skippedCount) {
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
        return "TagStat{" +
                "passed=" + passed +
                ", failed=" + failed +
                ", skipped=" + skipped +
                '}';
    }
}
