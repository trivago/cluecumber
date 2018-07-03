package com.trivago.rta.rendering.charts.pojos;

public class Chart {
    private Data data;
    private Options options;
    private String type;

    public Data getData() {
        return data;
    }

    public void setData(final Data data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(final Options options) {
        this.options = options;
    }
}
