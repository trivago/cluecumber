package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.json.pojo.Report;

import java.util.List;

public class FeatureSummaryPageCollection extends PageCollection {
    private final List<Report> reports;

    public FeatureSummaryPageCollection(final List<Report> reports) {
        super(PluginSettings.FEATURE_SUMMARY_PAGE_NAME);
        this.reports = reports;
    }
}
