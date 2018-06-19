package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.rendering.pages.pojos.ReportDetails;

public class PageCollection {
    private ReportDetails reportDetails;

    PageCollection(String pageName) {
        this.reportDetails = new ReportDetails(pageName);
    }

    public ReportDetails getReportDetails() {
        return reportDetails;
    }
}
