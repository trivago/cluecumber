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

import com.google.gson.annotations.SerializedName;

/**
 * Enum to get all MimeTypes for embedded content.
 */
public enum MimeType {
    /**
     * PNG image format
     */
    @SerializedName("image/png") PNG("image/png"),
    /**
     * GIF image format
     */
    @SerializedName("image/gif") GIF("image/gif"),
    /**
     * BMP image format
     */
    @SerializedName("image/bmp") BMP("image/bmp"),
    /**
     * JPG image format
     */
    @SerializedName("image/jpg") JPG("image/jpg"),
    /**
     * JPEG image format
     */
    @SerializedName("image/jpeg") JPEG("image/jpeg"),
    /**
     * SVG image format
     */
    @SerializedName("image/svg") SVG("image/svg"),
    /**
     * SVG with XML image format
     */
    @SerializedName("image/svg+xml") SVG_XML("image/svg+xml"),
    /**
     * HTML format
     */
    @SerializedName("text/html") HTML("text/html"),
    /**
     * XML format
     */
    @SerializedName("text/xml") XML("text/xml"),
    /**
     * Alternative XML format
     */
    @SerializedName("application/xml") APPLICATION_XML("application/xml"),
    /**
     * JSON format
     */
    @SerializedName("application/json") JSON("application/json"),
    /**
     * Plain TXT format
     */
    @SerializedName("text/plain") TXT("text/plain"),
    /**
     * PDF format
     */
    @SerializedName("application/pdf") PDF("application/pdf"),
    /**
     * MP4 video format
     */
    @SerializedName("video/mp4") MP4("video/mp4"),
    /**
     * Unknown format
     */
    @SerializedName("unknown") UNKNOWN("unknown");

    private final String contentType;

    MimeType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Get the content type of this mime type.
     *
     * @return The content type.
     */
    public String getContentType() {
        return contentType;
    }
}
