package com.trivago.cluecumber.constants;

public class Charts {

    public enum Type {bar, pie}

    /**
     * This enum manages the colors for the overview and detail charts that correspond to the passed in {@link Status} value.
     */
    public enum Color {
        PASSED(40, 167, 69), FAILED(220, 53, 69), SKIPPED(255, 193, 7);

        private static final String COLOR_FORMAT = "rgba(%d, %d, %d, 1.000)";

        private final int r;
        private final int g;
        private final int b;

        Color(final int r, final int g, final int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        /**
         * Get the corresponding chart color string for the passed {@link Status}.
         *
         * @param status the {@link Status}.
         * @return the matching color string.
         */
        public static String getChartColorStringByStatus(Status status) {
            switch (status) {
                case FAILED:
                    return String.format(COLOR_FORMAT, FAILED.r, FAILED.g, FAILED.b);
                case SKIPPED:
                    return String.format(COLOR_FORMAT, SKIPPED.r, SKIPPED.g, SKIPPED.b);
                case PASSED:
                default:
                    return String.format(COLOR_FORMAT, PASSED.r, PASSED.g, PASSED.b);
            }
        }

    }
}
