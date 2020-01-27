package com.trivago.cluecumber.json.pojo;

import com.google.gson.annotations.SerializedName;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;

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

    public String returnWithClickableLinks() {
        return RenderingUtils.turnUrlsIntoLinks(RenderingUtils.escapeHTML(value));
    }
}
