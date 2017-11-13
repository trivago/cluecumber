package com.trivago.rta.constants;

public enum Color {
    PASSED(40, 167, 69), FAILED(220, 53, 69), SKIPPED(255, 193, 7);

    private final int r;
    private final int g;
    private final int b;

    Color(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static be.ceau.chart.color.Color getChartColorByStatus(Status status) {
        switch (status) {
            case FAILED:
                return new be.ceau.chart.color.Color(FAILED.r, FAILED.g, FAILED.b);
            case SKIPPED:
                return new be.ceau.chart.color.Color(SKIPPED.r, SKIPPED.g, SKIPPED.b);
            case PASSED:
            default:
                return new be.ceau.chart.color.Color(PASSED.r, PASSED.g, PASSED.b);
        }

    }
}
