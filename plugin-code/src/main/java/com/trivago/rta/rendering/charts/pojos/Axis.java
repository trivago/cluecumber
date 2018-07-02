package com.trivago.rta.rendering.charts.pojos;

import be.ceau.chart.options.scales.ScaleLabel;

public class Axis {
    private Ticks ticks;
    private boolean stacked;
    private ScaleLabel scaleLabel;
    private float stepSize;

    public Ticks getTicks() {
        return ticks;
    }

    public void setTicks(final Ticks ticks) {
        this.ticks = ticks;
    }

    public boolean isStacked() {
        return stacked;
    }

    public void setStacked(final boolean stacked) {
        this.stacked = stacked;
    }

    public ScaleLabel getScaleLabel() {
        return scaleLabel;
    }

    public void setScaleLabel(final ScaleLabel scaleLabel) {
        this.scaleLabel = scaleLabel;
    }

    public float getStepSize() {
        return stepSize;
    }

    public void setStepSize(final float stepSize) {
        this.stepSize = stepSize;
    }
}
