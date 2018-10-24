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

package com.trivago.cluecumber.json.pojo;

import java.nio.charset.StandardCharsets;

import org.codehaus.plexus.util.Base64;

import com.google.gson.annotations.SerializedName;

public class Embedding {
    private String data;
    @SerializedName("mime_type")
    private String mimeType = "unknown";

    private transient String filename;

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public boolean isImage() {
        return
                mimeType.equalsIgnoreCase("image/png") ||
                        mimeType.equalsIgnoreCase("image/jpeg") ||
                        mimeType.equalsIgnoreCase("image/gif") ||
                        mimeType.equalsIgnoreCase("image/svg") ||
                        mimeType.equalsIgnoreCase("image/svg+xml");
    }

    public boolean isPlainText() {
        return mimeType.equalsIgnoreCase("text/plain");
    }
    
    public String getDecodedData() {
    	if(mimeType.equalsIgnoreCase("text/xml") || mimeType.equalsIgnoreCase("application/xml")){
    		String xmlString = new String(Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    		xmlString = xmlString.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    		return xmlString;
    	}else{
    		return new String(Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    	}
    }
    
    public String getFileEnding() {
        switch (mimeType) {
        case "image/png":
        case "image/gif":
        case "image/bmp":
        case "image/jpg":
        case "image/jpeg":
        case "text/html":
        case "text/xml":
        case "application/json":
        case "application/xml":
            return mimeType.substring(mimeType.indexOf('/') + 1);
        case "image/svg":
        case "image/svg+xml":
            return "svg";
        case "text/plain":
            return "txt";
        case "application/pdf":
            return "pdf";
        default:
            return "unknown";
        }
    }        
}
