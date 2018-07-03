package com.trivago.rta.rendering.charts.pojos;

import java.util.List;

public class Data {
    private List<String> labels;
    private List<Dataset> datasets;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(final List<Dataset> datasets) {
        this.datasets = datasets;
    }

    @Override
    public String toString() {
        return "Data{" +
                "labels=" + labels +
                ", datasets=" + datasets +
                '}';
    }
}
