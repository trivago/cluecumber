package com.trivago.rta.rendering.charts.pojos;

import java.util.List;

public class Dataset {
    private List<Integer> data;
    private List<String> backgroundColor;
    private String label;
    private String stack;

    public List<Integer> getData() {
        return data;
    }

    public void setData(final List<Integer> data) {
        this.data = data;
    }

    public List<String> getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final List<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(final String stack) {
        this.stack = stack;
    }
}
