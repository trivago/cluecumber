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

package com.trivago.cluecumber.json.pojo;

import com.google.gson.annotations.SerializedName;
import com.trivago.cluecumber.constants.MimeType;
import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;
import org.codehaus.plexus.util.Base64;

import java.nio.charset.StandardCharsets;

public class Embedding {

    private String data;
    private String decodedData;
    @SerializedName("mime_type")
    private MimeType mimeType = MimeType.UNKNOWN;
    private String name = "";
    private boolean isExternalContent;

    private transient String filename = "";

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public String getDecodedData() {
        return decodedData;
    }

    public void decodeData(final String data) {
        decodedData = new String(Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
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

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(final MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public boolean isImage() {
        return mimeType == MimeType.PNG ||
               mimeType == MimeType.GIF ||
               mimeType == MimeType.BMP ||
               mimeType == MimeType.JPEG ||
               mimeType == MimeType.JPG ||
               mimeType == MimeType.SVG ||
               mimeType == MimeType.SVG_XML;
    }

    public boolean isPlainText() {
        return mimeType.getContentType().equalsIgnoreCase("text/plain");
    }

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

    public boolean isExternalContent() {
        return isExternalContent;
    }
}
