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

package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.pojo.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagSummaryPageCollection extends PageCollection {
    private List<Report> reports;

    public TagSummaryPageCollection(List<Report> reports) {
        this.reports = reports;
    }

    public Map<String, TagStat> getTagStats() {
        Map<String, TagStat> tagStats = new HashMap<>();
        for (Report report : reports) {
            for (Element element : report.getElements()) {
                for (Tag tag : element.getTags()) {
                    TagStat tagStat = tagStats.get(tag.getName());
                    if (tagStat == null) {
                        tagStat = new TagStat();
                    }

                    switch (element.getStatus()) {
                        case PASSED:
                            tagStat.addPassed(1);
                            break;
                        case FAILED:
                            tagStat.addFailed(1);
                            break;
                        case SKIPPED:
                        case PENDING:
                        case UNDEFINED:
                        case AMBIGUOUS:
                            tagStat.addSkipped(1);
                            break;
                    }

                    tagStats.put(tag.getName(), tagStat);
                }
            }
        }
        return tagStats;
    }
}
