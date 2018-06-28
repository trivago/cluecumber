package com.trivago.rta.rendering.charts.pojos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public String getJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
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
