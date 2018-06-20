package com.trivago.rta.rendering.pages.pojos.pagecollections;

import com.trivago.rta.constants.Status;
import com.trivago.rta.rendering.pages.pojos.ResultCount;

import java.util.Collection;

public class SummaryPageCollection extends PageCollection {
    SummaryPageCollection(final String pageName) {
        super(pageName);
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
