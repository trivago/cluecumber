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

import com.google.gson.annotations.SerializedName;

/**
 * Enum to get all MimeTypes for embedded content.
 */
public enum MimeType {
    @SerializedName("image/png") PNG("image/png"),
    @SerializedName("image/gif") GIF("image/gif"),
    @SerializedName("image/bmp") BMP("image/bmp"),
    @SerializedName("image/jpg") JPG("image/jpg"),
    @SerializedName("image/jpeg") JPEG("image/jpeg"),
    @SerializedName("image/svg") SVG("image/svg"),
    @SerializedName("image/svg+xml") SVG_XML("image/svg+xml"),
    @SerializedName("text/html") HTML("text/html"),
    @SerializedName("text/xml") XML("text/xml"),
    @SerializedName("application/xml") APPLICATION_XML("application/xml"),
    @SerializedName("application/json") JSON("application/json"),
    @SerializedName("text/plain") TXT("text/plain"),
    @SerializedName("application/pdf") PDF("application/pdf"),
    @SerializedName("video/mp4") MP4("video/mp4"),
    @SerializedName("unknown") UNKNOWN("unknown");

    private final String contentType;

    MimeType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
