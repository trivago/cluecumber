package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.constants.Status;
import com.trivago.rta.rendering.pages.pojos.ReportDetails;
import com.trivago.rta.rendering.pages.pojos.ResultCount;

public class PageCollection {
    private ReportDetails reportDetails;

    PageCollection(String pageName) {
        this.reportDetails = new ReportDetails(pageName);
    }

    public ReportDetails getReportDetails() {
        return reportDetails;
    }

    void updateResultCount (ResultCount resultCount, final Status status){
        switch (status){
            case PASSED:
                resultCount.addPassed(1);
                break;
            case FAILED:
                resultCount.addFailed(1);
                break;
            case SKIPPED:
            case PENDING:
            case UNDEFINED:
            case AMBIGUOUS:
                resultCount.addSkipped(1);
                break;
        }
    }
}
