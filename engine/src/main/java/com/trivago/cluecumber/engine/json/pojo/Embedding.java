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
import com.trivago.cluecumber.engine.constants.MimeType;
import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * This class represents scenario attachments.
 */
public class Embedding {

    private String data;
    private String decodedData;
    @SerializedName("mime_type")
    private MimeType mimeType = MimeType.UNKNOWN;
    private String name = "";
    private boolean isExternalContent;

    private transient String filename = "";

    /**
     * Default constructor.
     */
    public Embedding() {
        // Default constructor
    }

    /**
     * Get the raw attachment data.
     *
     * @return The data as string.
     */
    public String getData() {
        return data;
    }

    /**
     * Set the attachment data.
     *
     * @param data The data string.
     */
    public void setData(final String data) {
        this.data = data;
    }

    /**
     * Retrieve the decoded data.
     *
     * @return The decoded data string.
     */
    public String getDecodedData() {
        return decodedData;
    }

    /**
     * Encode the data based on its mime type.
     *
     * @param data The data string.
     */
    public void decodeData(final String data) {
        decodedData = new String(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        switch (mimeType) {
            case HTML:
                decodedData = decodedData.replaceAll("\"", "'")
                        .replaceAll("&", "&amp;")
                        .replaceAll("\"", "&quot;");
                break;
            case XML:
            case APPLICATION_XML:
                decodedData = decodedData.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                break;
            case TXT:
            case PDF:
            case MP4:
                isExternalContent = RenderingUtils.isUrl(decodedData);
                break;
            case UNKNOWN:
                break;
        }
    }

    /**
     * Retrieve the mime type of this attachment.
     *
     * @return The {@link MimeType} instance.
     */
    public MimeType getMimeType() {
        return mimeType;
    }

    /**
     * Set the attachment's mime type.
     *
     * @param mimeType The {@link MimeType} instance.
     */
    public void setMimeType(final MimeType mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Get the name of this attachment.
     *
     * @return The name string.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this attachment.
     *
     * @param name The attachment name string.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Retrieve the attachment's file name after preprocessing.
     *
     * @return The file name string.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set the filename after preprocessing.
     *
     * @param filename The file name string.
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * Check if the attachment is an image.
     *
     * @return true if this is an image.
     */
    public boolean isImage() {
        return mimeType == MimeType.PNG ||
                mimeType == MimeType.GIF ||
                mimeType == MimeType.BMP ||
                mimeType == MimeType.JPEG ||
                mimeType == MimeType.JPG ||
                mimeType == MimeType.SVG ||
                mimeType == MimeType.SVG_XML;
    }

    /**
     * Check if the attachment is a plain text.
     *
     * @return true if this is text.
     */
    public boolean isPlainText() {
        return mimeType.getContentType().equalsIgnoreCase("text/plain");
    }

    /**
     * Determine the generated file ending.
     *
     * @return The file ending as string.
     */
    public String getFileEnding() {
        switch (mimeType) {
            case PNG:
            case GIF:
            case BMP:
            case JPG:
            case JPEG:
            case HTML:
            case XML:
            case JSON:
            case APPLICATION_XML:
                return mimeType.getContentType().substring(mimeType.getContentType().indexOf('/') + 1);
            case SVG:
            case SVG_XML:
                return "svg";
            case TXT:
                return "txt";
            case PDF:
                return "pdf";
            default:
                return "unknown";
        }
    }


    /**
     * This specifies if it is a link that refers to external data, e.g. an externally hosted video file.
     *
     * @return true if this is externally hosted.s
     */
    public boolean isExternalContent() {
        return isExternalContent;
    }
}
