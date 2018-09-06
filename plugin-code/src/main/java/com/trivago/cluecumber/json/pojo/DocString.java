package com.trivago.cluecumber.json.pojo;

import com.google.gson.annotations.SerializedName;

public class DocString {
    @SerializedName("content_type")
    private String contentType = "";
    private int line;
    private String value = "";

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
