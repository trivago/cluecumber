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

import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Tag;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllTagsPageCollection extends ScenarioSummaryPageCollection {
    private final Map<Tag, ResultCount> tagResultCounts = new HashMap<>();

    public AllTagsPageCollection(List<Report> reports, final String pageTitle) {
        super(pageTitle);
        calculateTagResultCounts(reports);
    }

    /**
     * Get a map of {@link ResultCount} lists connected to tag names.
     *
     * @return a map of {@link ResultCount} lists with tags as keys.
     */
    public Map<Tag, ResultCount> getTagResultCounts() {
        return tagResultCounts;
    }

    public Set<Tag> getTags() {
        return tagResultCounts.keySet();
    }

    public int getTotalNumberOfTags() {
        return tagResultCounts.size();
    }

    /**
     * Calculate the numbers of failures, successes and skips per tag name.
     *
     * @param reports The {@link Report} list.
     */
    private void calculateTagResultCounts(final List<Report> reports) {
        if (reports == null) return;
        reports.forEach(report -> report.getElements().forEach(element -> element.getTags().forEach(tag -> {
            ResultCount tagResultCount = tagResultCounts.getOrDefault(tag, new ResultCount());
            updateResultCount(tagResultCount, element.getStatus());
            tagResultCounts.put(tag, tagResultCount);
            addScenarioIndexByStatus(element.getStatus(), element.getScenarioIndex());
        })));
    }
}
