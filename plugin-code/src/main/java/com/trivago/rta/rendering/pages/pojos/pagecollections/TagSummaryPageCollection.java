/*
 * Copyright 2018 trivago N.V.
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

package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.constants.Status;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Tag;
import com.trivago.rta.rendering.pages.pojos.ResultCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagSummaryPageCollection extends SummaryPageCollection {
    private Map<Tag, ResultCount> tagResultCounts;

    public TagSummaryPageCollection(List<Report> reports) {
        super(PluginSettings.TAG_SUMMARY_PAGE_NAME);
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

    public Set<Tag> getTags(){
        return tagResultCounts.keySet();
    }

    public int getTotalNumberOfTags() {
        return tagResultCounts.size();
    }

    public int getTotalNumberOfPassedTags() {
        return getNumberOfResultsWithStatus(tagResultCounts.values(), Status.PASSED);
    }

    public int getTotalNumberOfFailedTags() {
        return getNumberOfResultsWithStatus(tagResultCounts.values(), Status.FAILED);
    }

    public int getTotalNumberOfSkippedTags() {
        return getNumberOfResultsWithStatus(tagResultCounts.values(), Status.SKIPPED);
    }

    /**
     * Calculate the numbers of failures, successes and skips per tag name.
     *
     * @param reports The {@link Report} list.
     */
    private void calculateTagResultCounts(final List<Report> reports) {
        if (reports == null) return;
        tagResultCounts = new HashMap<>();
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                for (Tag tag : element.getTags()) {
                    ResultCount tagResultCount = tagResultCounts.getOrDefault(tag, new ResultCount());
                    updateResultCount(tagResultCount, element.getStatus());
                    tagResultCounts.put(tag, tagResultCount);
                }
            }
        }
    }
}
