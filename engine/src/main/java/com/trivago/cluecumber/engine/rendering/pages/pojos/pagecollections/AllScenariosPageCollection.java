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
/*
 * Copyright 2023 trivago N.V.
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
package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.json.pojo.Tag;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;
import com.trivago.cluecumber.engine.rendering.pages.visitors.PageVisitor;
import com.trivago.cluecumber.engine.rendering.pages.visitors.Visitable;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page collection for the scenario overview page.
 */
public class AllScenariosPageCollection extends PageCollection implements Visitable {
    private List<Report> reports = new ArrayList<>();
    private boolean groupPreviousScenarioRuns;
    private boolean expandPreviousScenarioRuns;
    private Tag tagFilter;
    private Feature featureFilter;
    private Step stepFilter;

    /**
     * Constructor.
     *
     * @param pageTitle The page title.
     */
    public AllScenariosPageCollection(final String pageTitle) {
        super(pageTitle);
    }

    /**
     * Get all reports.
     *
     * @return The list of {@link Report} instances.
     */
    public List<Report> getReports() {
        return reports;
    }

    /**
     * Get scenarios by feature index.
     *
     * @param featureIndex The feature index.
     * @return The list of related {@link Element} instances.
     */
    public List<Element> getElementsByFeatureIndex(final int featureIndex) {
        return getReports().stream()
                .filter(report -> report.getFeatureIndex() == featureIndex)
                .flatMap(report -> report.getElements().stream())
                .sorted(Comparator.comparing(Element::getName))
                .collect(Collectors.toList());
    }


    /**
     * Empty report list.
     */
    public void clearReports() {
        reports = new ArrayList<>();
    }


    /**
     * Add reports to the existing report list.
     *
     * @param reportList An array of {@link Report} instances to add.
     */
    public void addReports(final Report[] reportList) {
        if (reportList == null) {
            return;
        }
        addReports(Arrays.asList(reportList));
    }

    /**
     * Add reports to this page collection.
     *
     * @param reportList The {@link Report} list.
     */
    private void addReports(final List<Report> reportList) {
        this.reports.addAll(reportList);
    }


    /**
     * Return the total scenario count.
     *
     * @return The scenario count.
     */
    public int getTotalNumberOfScenarios() {
        if (!groupPreviousScenarioRuns) {
            return reports.stream().map(Report::getElements).
                    mapToInt(elements -> (int) elements.stream().filter(Element::isScenario).count()).sum();
        } else {
            return reports.stream().map(Report::getElements).
                    mapToInt(elements -> (int) elements.stream().filter(Element::isScenario).filter(
                            Element::isMultiRunChild
                    ).count()).sum();
        }
    }

    /**
     * Check if there are failed scenarios.
     *
     * @return true if there are failed scenarios.
     */
    public boolean hasFailedScenarios() {
        return getTotalNumberOfFailedScenarios() > 0;
    }

    /**
     * Check if there are failed scenarios.
     *
     * @return true if there are failed scenarios.
     */
    public boolean hasFailedScenariosNotPassedOnLastRun() {
        return getTotalNumberOfFailedScenarioWithoutLaterRuns() > 0;
    }

    /**
     * Check if there are passed scenarios.
     *
     * @return true if there are passed scenarios.
     */
    public boolean hasPassedScenarios() {
        return getTotalNumberOfPassedScenarios() > 0;
    }

    /**
     * Check if there are skipped scenarios.
     *
     * @return true if there are skipped scenarios.
     */
    public boolean hasSkippedScenarios() {
        return getTotalNumberOfSkippedScenarios() > 0;
    }

    /**
     * Check if there are scenarios run multiple times.
     *
     * @return true if there are scenarios run multiple times.
     */
    public boolean hasMultiRunChildren() {
        return getTotalNumberOfMultiRunChildren() > 0;
    }

    /**
     * Return the number of passed scenarios.
     *
     * @return The scenario count.
     */
    public int getTotalNumberOfPassedScenarios() {
        return getNumberOfScenariosWithStatus(Status.PASSED);
    }

    /**
     * Return the number of failed scenarios.
     *
     * @return The scenario count.
     */
    public int getTotalNumberOfFailedScenarios() {
        return getNumberOfScenariosWithStatus(Status.FAILED);
    }

    /**
     * Return the number of scenarios runs that failed and were either single runs or the last of multiple runs.
     *
     * @return The scenario count.
     */
    public int getTotalNumberOfFailedScenarioWithoutLaterRuns() {
        return reports.stream().mapToInt(
                report -> (int) report.getElements().stream().filter(
                        element -> element.getStatus().equals(Status.FAILED) && !element.isMultiRunChild()
                ).count()).sum();
    }

    /**
     * Return the number of skipped scenarios.
     *
     * @return The scenario count.
     */
    public int getTotalNumberOfSkippedScenarios() {
        return getNumberOfScenariosWithStatus(Status.SKIPPED);
    }

    /**
     * Return the number of scenarios runs that were not the last.
     *
     * @return The scenario count.
     */
    public int getTotalNumberOfMultiRunChildren() {
        int sum = 0;
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                sum += element.getMultiRunChildren().size();
            }
        }
        return sum;
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

    /**
     * Return a human-readable time string of the total duration.
     *
     * @return The time string.
     */
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

    /**
     * Return the start date and time string of the earliest scenario that was started in the test run.
     *
     * @return The time string.
     */
    public String returnStartDateTimeString() {
        ZonedDateTime earliestStartDateTime = getEarliestStartDateTime();
        if (earliestStartDateTime != null) {
            return RenderingUtils.convertZonedDateTimeToDateString(earliestStartDateTime) + " " +
                   RenderingUtils.convertZonedDateTimeToTimeString(earliestStartDateTime);
        }
        return "";
    }

    /**
     * Return the human-readable time and date string of the latest scenario end.
     *
     * @return The time string.
     */
    public String returnEndDateTimeString() {
        ZonedDateTime latestEndDateTime = getLatestEndDateTime();
        if (latestEndDateTime != null) {
            return RenderingUtils.convertZonedDateTimeToDateString(latestEndDateTime) + " " +
                   RenderingUtils.convertZonedDateTimeToTimeString(latestEndDateTime);
        }
        return "";
    }

    /**
     * Get the current tag filter to filter scenario by a specific tag.
     *
     * @return The {@link Tag} to filter by.
     */
    public Tag getTagFilter() {
        return tagFilter;
    }

    /**
     * Set the current tag filter to filter scenario by a specific tag.
     *
     * @param tagFilter The {@link Tag} to filter by.
     */
    public void setTagFilter(final Tag tagFilter) {
        this.tagFilter = tagFilter;
    }

    /**
     * Return the feature by which scenarios are filtered.
     *
     * @return The {@link Feature} to filter by.
     */
    public Feature getFeatureFilter() {
        return featureFilter;
    }

    /**
     * Set the feature by which scenarios should be filtered.
     *
     * @param featureFilter The {@link Feature} to filter by.
     */
    public void setFeatureFilter(final Feature featureFilter) {
        this.featureFilter = featureFilter;
    }

    /**
     * Return the step by which scenarios are filtered.
     *
     * @return The {@link Step} to filter by.
     */
    public Step getStepFilter() {
        return stepFilter;
    }

    /**
     * Set the step by which scenarios should be filtered.
     *
     * @param stepFilter The {@link Step} to filter by.
     */
    public void setStepFilter(final Step stepFilter) {
        this.stepFilter = stepFilter;
    }

    /**
     * This determines whether the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     *
     * @return true means scenarios should be grouped and toggle should be shown.
     */
    public boolean isGroupPreviousScenarioRuns() {
        return groupPreviousScenarioRuns;
    }

    /**
     * Set whether the scenarios run multiple times should be grouped and the show not last run toggle should be shown.
     *
     * @param groupPreviousScenarioRuns true means scenarios should be grouped and toggle should be shown.
     */
    public void setGroupPreviousScenarioRuns(final boolean groupPreviousScenarioRuns) {
        this.groupPreviousScenarioRuns = groupPreviousScenarioRuns;
    }

    /**
     * This determines whether the not last run elements should be expanded and shown.
     *
     * @return true means it should be expanded.
     */
    public boolean isExpandPreviousScenarioRuns() {
        return expandPreviousScenarioRuns;
    }

    /**
     * Set whether the not last run elements should be expanded and shown.
     *
     * @param expandPreviousScenarioRuns true means elements should be expanded.
     */
    public void setExpandPreviousScenarioRuns(final boolean expandPreviousScenarioRuns) {
        this.expandPreviousScenarioRuns = expandPreviousScenarioRuns;
    }

    /**
     * Function to clone the {@link AllScenariosPageCollection} including all included data.
     *
     * @return The clone of the {@link AllScenariosPageCollection}
     */
    @Override
    public AllScenariosPageCollection clone() throws CloneNotSupportedException {
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

    /**
     * Method to accept a {@link PageVisitor}.
     *
     * @param visitor The {@link PageVisitor} instance.
     * @throws CluecumberException thrown on any error.
     */
    @Override
    public void accept(final PageVisitor visitor) throws CluecumberException {
        visitor.visit(this);
    }
}
