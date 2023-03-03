package com.trivago.cluecumber.engine.json.pojo;

import com.trivago.cluecumber.engine.rendering.pages.renderering.RenderingUtils;

/**
 * The class to hold docstring information of steps.
 */
public class DocString {
    private int line;
    private String value = "";

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
