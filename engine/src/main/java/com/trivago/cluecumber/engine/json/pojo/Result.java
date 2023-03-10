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
package com.trivago.cluecumber.engine.json.pojo;

import com.google.gson.annotations.SerializedName;
import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

/**
 * This represents a result of a step or scenario.
 */
public class Result {

    private long duration = 0;
    private String status = Status.UNDEFINED.toString();

    @SerializedName("error_message")
    private String errorMessage = "";

    /**
     * Get the duration of this result.
     *
     * @return The duration in nanoseconds.
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Set the duration of this result.
     *
     * @param duration The duration in nanoseconds.
     */
    public void setDuration(final long duration) {
        this.duration = duration;
    }

    /**
     * Get the status.
     *
     * @return The status string.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status.
     *
     * @param status The status string.
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Check if there is an error message.
     *
     * @return true if an error message exists.
     */
    public boolean hasErrorMessage() {
        return errorMessage != null && !errorMessage.trim().isEmpty();
    }


    /**
     * Get the error message.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the error message.
     *
     * @param errorMessage The error message.
     */
    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Get the duration in milliseconds.
     *
     * @return The converted duration.
     */
    public long getDurationInMilliseconds() {
        return RenderingUtils.convertNanosecondsToMilliseconds(duration);
    }

    /**
     * Get the human readable duration string.
     *
     * @return The duration string.
     */
    public String returnDurationString() {
        return RenderingUtils.convertNanosecondsToTimeString(duration);
    }

    /**
     * Get the error message with URLs turned into clickable HTML links.
     *
     * @return The converted error message.
     */
    public String returnErrorMessageWithClickableLinks() {
        return RenderingUtils.turnUrlsIntoLinks(RenderingUtils.escapeHTML(errorMessage));
    }
}
