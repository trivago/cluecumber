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

package com.trivago.cluecumber.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Enum to manage all states for steps and scenarios.
 */
public enum Status {
    PASSED("passed"),
    FAILED("failed"),
    SKIPPED("skipped"),
    PENDING("pending"),
    UNDEFINED("undefined"),
    AMBIGUOUS("ambiguous");

    public static final List<Status> BASIC_STATES = Arrays.asList(Status.PASSED, Status.FAILED, Status.SKIPPED);

    private final String status;

    Status(final String statusString) {
        this.status = statusString;
    }

    public static Status fromString(String status) {
        return valueOf(status.toUpperCase());
    }

    public String getStatusString() {
        return status;
    }
}
