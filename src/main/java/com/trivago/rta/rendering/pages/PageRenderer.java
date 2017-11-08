package com.trivago.rta.rendering.pages;

import com.trivago.rta.rendering.pages.pojos.ReportDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class PageRenderer {
    void addCurrentDateToReportDetails(final ReportDetails reportDetails) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        reportDetails.setDate(dateFormat.format(date));
    }
}
