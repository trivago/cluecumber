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
package com.trivago.cluecumber.engine.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Enum to manage all states for steps and scenarios.
 */
public enum Status {
    /**
     * Passed status.
     */
    PASSED("passed"),
    /**
     * Failed status.
     */
    FAILED("failed"),
    /**
     * Skipped status.
     */
    SKIPPED("skipped"),
    /**
     * Pending status.
     */
    PENDING("pending"),
    /**
     * Undefined status.
     */
    UNDEFINED("undefined"),
    /**
     * Ambiguous status.
     */
    AMBIGUOUS("ambiguous");

    /**
     * The three basic states: passed, failed and skipped.
     */
    public static final List<Status> BASIC_STATES = Arrays.asList(Status.PASSED, Status.FAILED, Status.SKIPPED);

    private final String status;

    Status(final String statusString) {
        this.status = statusString;
    }

    /**
     * Get a status enum from a status string.
     *
     * @param status The status string.
     * @return The matching {@link Status} enum.
     */
    public static Status fromString(String status) {
        return valueOf(status.toUpperCase());
    }

    /**
     * Return the status string from this enum.
     *
     * @return The status string.
     */
    public String getStatusString() {
        return status;
    }
}
