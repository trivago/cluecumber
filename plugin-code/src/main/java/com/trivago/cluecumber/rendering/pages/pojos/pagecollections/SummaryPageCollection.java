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
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;

import java.util.Collection;

class SummaryPageCollection extends PageCollection {
    SummaryPageCollection(final String pageTitle) {
        super(pageTitle);
    }

    void updateResultCount(ResultCount resultCount, final Status status) {
        switch (status) {
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

    int getNumberOfResultsWithStatus(final Collection<ResultCount> resultCounts, final Status status) {
        int sum = 0;
        for (ResultCount resultCount : resultCounts) {
            switch (status) {
                case PASSED:
                    sum += resultCount.getPassed();
                    break;
                case FAILED:
                    sum += resultCount.getFailed();
                    break;
                case SKIPPED:
                case PENDING:
                case UNDEFINED:
                case AMBIGUOUS:
                    sum += resultCount.getSkipped();
                    break;
            }
        }
        return sum;
    }
}
