package com.trivago.rta.json.pojo;

import com.google.gson.annotations.SerializedName;

public class Embedding {
    private String data;

    @SerializedName("mime_type")
    private String mimeType;

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
}
