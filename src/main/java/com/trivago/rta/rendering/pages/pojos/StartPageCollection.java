/*
 * Copyright 2017 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.constants.Status;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.rendering.RenderingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartPageCollection {
    private List<Report> reports = new ArrayList<>();
    private ReportDetails reportDetails;
    private List<CustomParameter> customParameters;

    public List<Report> getReports() {
        return reports;
    }

    public void addReports(final Report[] reportList) {
        if (reportList == null) {
            return;
        }
        this.reports.addAll(Arrays.asList(reportList));
    }

    public int getTotalNumberOfFeatures() {
        return reports.size();
    }

    public int getTotalNumberOfScenarios() {
        return reports.stream().map(Report::getElements).
                mapToInt(elements -> (int) elements.stream().filter(Element::isScenario).count()).sum();
    }

    public boolean hasFailedScenarios() {
        return getTotalNumberOfFailedScenarios() > 0;
    }

    public boolean hasPassedScenarios() {
        return getTotalNumberOfPassedScenarios() > 0;
    }

    public boolean hasSkippedScenarios() {
        return getTotalNumberOfSkippedScenarios() > 0;
    }

    public int getTotalNumberOfPassedScenarios() {
        return getNumberOfScenariosWithStatus(Status.PASSED);
    }

    public int getTotalNumberOfFailedScenarios() {
        return getNumberOfScenariosWithStatus(Status.FAILED);
    }

    public int getTotalNumberOfSkippedScenarios() {
        return getNumberOfScenariosWithStatus(Status.SKIPPED);
    }

    private int getNumberOfScenariosWithStatus(final Status status) {
        return reports.stream().mapToInt(
                report -> (int) report.getElements().stream().filter(
                        element -> element.getStatus().equals(status)
                ).count()).sum();
    }

    public long getTotalDuration() {
        long totalDurationMicroseconds = 0;
        for (Report report : reports) {
            totalDurationMicroseconds += report.getTotalDuration();
        }
        return totalDurationMicroseconds;
    }

    public String getTotalDurationString() {
        return RenderingUtils.convertMicrosecondsToTimeString(getTotalDuration());
    }

    public ReportDetails getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(final ReportDetails reportDetails) {
        this.reportDetails = reportDetails;
    }

    public void setCustomParameters(final List<CustomParameter> customParameters) {
        this.customParameters = customParameters;
    }

    public List<CustomParameter> getCustomParameters() {
        return customParameters;
    }

    public boolean hasCustomParameters(){
        return customParameters != null && !customParameters.isEmpty();
    }
}
