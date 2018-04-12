package com.trivago.rta.rendering.pages.pojos;

public class PageCollection {
    private ReportDetails reportDetails;

    public PageCollection(String pageName) {
        this.reportDetails = new ReportDetails(pageName);
    }

    public ReportDetails getReportDetails() {
        return reportDetails;
    }
}
