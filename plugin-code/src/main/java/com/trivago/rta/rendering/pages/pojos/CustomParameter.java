package com.trivago.rta.rendering.pages.pojos;

import java.net.URL;

public class CustomParameter {
    private String key;
    private String value;

    public CustomParameter(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value != null ? value : "";
    }

    public boolean isUrl() {
        try {
            new URL(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "CustomParameter{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
