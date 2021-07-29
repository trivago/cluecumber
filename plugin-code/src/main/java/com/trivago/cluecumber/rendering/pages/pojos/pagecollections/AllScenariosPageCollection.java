/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumber.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.json.pojo.Tag;
import com.trivago.cluecumber.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;
import com.trivago.cluecumber.rendering.pages.visitors.PageVisitor;
import com.trivago.cluecumber.rendering.pages.visitors.Visitable;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class AllScenariosPageCollection extends PageCollection implements Visitable {
    private List<Report> reports = new ArrayList<>();
    private Tag tagFilter;
    private Feature featureFilter;
    private Step stepFilter;

    public AllScenariosPageCollection(final String pageTitle) {
        super(pageTitle);
    }

    public List<Report> getReports() {
        return reports;
    }

    public void clearReports() {
        reports = new ArrayList<>();
    }

    public void addReports(final Report[] reportList) {
        if (reportList == null) {
            return;
        }
        addReports(Arrays.asList(reportList));
    }

    private void addReports(final List<Report> reportList) {
        this.reports.addAll(reportList);
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

    long getTotalDuration() {
        return reports.stream().mapToLong(Report::getTotalDuration).sum();
    }

    public String getTotalDurationString() {
        ZonedDateTime earliestStartDateTime = getEarliestStartDateTime();
        ZonedDateTime latestEndDateTime = getLatestEndDateTime();

        // Return total runtime if no timestamps exist...
        if (earliestStartDateTime == null || latestEndDateTime == null) {
            return RenderingUtils.convertNanosecondsToTimeString(getTotalDuration());
        }

        // ...else return the calculated runtime.
        return RenderingUtils.convertNanosecondsToTimeString(
                ChronoUnit.NANOS.between(earliestStartDateTime, latestEndDateTime)
        );
    }

    private ZonedDateTime getEarliestStartDateTime() {
        ZonedDateTime earliestStartDateTime = null;
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                ZonedDateTime currentStartDateTime = element.getStartDateTime();
                if (currentStartDateTime != null &&
                        (earliestStartDateTime == null || currentStartDateTime.isBefore(earliestStartDateTime))) {
                    earliestStartDateTime = currentStartDateTime;
                }
            }
        }
        return earliestStartDateTime;
    }

    private ZonedDateTime getLatestEndDateTime() {
        ZonedDateTime latestEndDateTime = null;
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                ZonedDateTime currentEndDateTime = element.getEndDateTime();
                if (currentEndDateTime != null &&
                        (latestEndDateTime == null || currentEndDateTime.isAfter(latestEndDateTime))) {
                    latestEndDateTime = currentEndDateTime;
                }
            }
        }
        return latestEndDateTime;
    }

    public String returnStartDateTimeString() {
        ZonedDateTime earliestStartDateTime = getEarliestStartDateTime();
        if (earliestStartDateTime != null) {
            return RenderingUtils.convertZonedDateTimeToDateString(earliestStartDateTime) + " " +
                    RenderingUtils.convertZonedDateTimeToTimeString(earliestStartDateTime);
        }
        return "";
    }

    public String returnEndDateTimeString() {
        ZonedDateTime latestEndDateTime = getLatestEndDateTime();
        if (latestEndDateTime != null) {
            return RenderingUtils.convertZonedDateTimeToDateString(latestEndDateTime) + " " +
                    RenderingUtils.convertZonedDateTimeToTimeString(latestEndDateTime);
        }
        return "";
    }

    public Tag getTagFilter() {
        return tagFilter;
    }

    public void setTagFilter(final Tag tagFilter) {
        this.tagFilter = tagFilter;
    }

    public Feature getFeatureFilter() {
        return featureFilter;
    }

    public void setFeatureFilter(final Feature featureFilter) {
        this.featureFilter = featureFilter;
    }

    public Step getStepFilter() {
        return stepFilter;
    }

    public void setStepFilter(final Step stepFilter) {
        this.stepFilter = stepFilter;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final AllScenariosPageCollection clone = (AllScenariosPageCollection) super.clone();
        clone.setFeatureFilter(null);
        clone.setStepFilter(null);
        clone.setTagFilter(null);
        clone.clearReports();
        List<Report> clonedReports = new ArrayList<>();
        for (Report r : getReports()) {
            clonedReports.add((Report) r.clone());
        }
        clone.addReports(clonedReports);
        return clone;
    }

    @Override
    public void accept(final PageVisitor visitor) throws CluecumberPluginException {
        visitor.visit(this);
    }
}
