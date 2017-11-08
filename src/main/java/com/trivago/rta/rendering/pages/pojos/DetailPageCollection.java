package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.json.pojo.Element;

public class DetailPageCollection {
    private Element element;
    private ReportDetails reportDetails;

    public DetailPageCollection(final Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public ReportDetails getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(final ReportDetails reportDetails) {
        this.reportDetails = reportDetails;
    }
}
