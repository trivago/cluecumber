package com.trivago.rta.rendering.charts.pojos;

public class ScaleLabel {
    private boolean display;
    private String labelString;

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(final boolean display) {
        this.display = display;
    }

    public String getLabelString() {
        return labelString;
    }

    public void setLabelString(final String labelString) {
        this.labelString = labelString;
    }
}
