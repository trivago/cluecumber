package com.trivago.rta.rendering.charts.pojos;

import java.util.List;

public class Scales {
    private List<Axis> xAxes;
    private List<Axis> yAxes;

    public List<Axis> getxAxes() {
        return xAxes;
    }

    public void setxAxes(final List<Axis> xAxes) {
        this.xAxes = xAxes;
    }

    public List<Axis> getyAxes() {
        return yAxes;
    }

    public void setyAxes(final List<Axis> yAxes) {
        this.yAxes = yAxes;
    }
}
