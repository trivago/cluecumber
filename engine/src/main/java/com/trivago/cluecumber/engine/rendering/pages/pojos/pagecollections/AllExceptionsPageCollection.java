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

import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Page collection for the exception overview page.
 */
public class AllExceptionsPageCollection extends ScenarioSummaryPageCollection {
    private final Map<String, ResultCount> exceptionResultCounts = new HashMap<>();

    /**
     * Constructor.
     *
     * @param reports   The {@link Report} list.
     * @param pageTitle The page title.
     */
    public AllExceptionsPageCollection(List<Report> reports, final String pageTitle) {
        super(pageTitle);
        calculateExceptionResultCounts(reports);
    }

    /**
     * Get a map of {@link ResultCount} lists connected to tag names.
     *
     * @return a map of {@link ResultCount} lists with tags as keys.
     */
    public Map<String, ResultCount> getExceptionResultCounts() {
        return exceptionResultCounts;
    }

    /**
     * Get all exceptions.
     *
     * @return The exception set.
     */
    public Set<String> getExceptions() {
        return exceptionResultCounts.keySet();
    }

    /**
     * Get the number of exceptions.
     *
     * @return The count.
     */
    public int getTotalNumberOfExceptions() {
        return exceptionResultCounts.size();
    }

    /**
     * Calculate the numbers of failures, successes and skips per exception.
     *
     * @param reports The {@link Report} list.
     */
    private void calculateExceptionResultCounts(final List<Report> reports) {
        if (reports == null) return;
        reports.forEach(report -> report.getElements().forEach(element -> {
            String exceptionClass = element.getFirstExceptionClass();
            if (exceptionClass != null && !exceptionClass.isEmpty()) {
                ResultCount exceptionResultCount = exceptionResultCounts.getOrDefault(exceptionClass, new ResultCount());
                updateResultCount(exceptionResultCount, element.getStatus());
                exceptionResultCounts.put(exceptionClass, exceptionResultCount);
                addScenarioIndexByStatus(element.getStatus(), element.getScenarioIndex());
            }
        }));
    }
}
