package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.constants.PluginSettings;

public class ReportDetails {
    private String overviewChartJson;
    private String date;

    public void setChartJson(final String chartJson) {
        this.overviewChartJson = chartJson;
    }

    public String getChartJson() {
        return overviewChartJson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getGeneratorName() {
        return PluginSettings.NAME;
    }
}
