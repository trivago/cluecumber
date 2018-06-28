package com.trivago.rta.rendering.charts.pojos;

import java.util.List;

public class Data {
    private List<String> labels;
    private List<DataSet> dataSets;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    public List<DataSet> getDataSets() {
        return dataSets;
    }

    public void setDataSets(final List<DataSet> dataSets) {
        this.dataSets = dataSets;
    }

    @Override
    public String toString() {
        return "Data{" +
                "labels=" + labels +
                ", dataSets=" + dataSets +
                '}';
    }
}
