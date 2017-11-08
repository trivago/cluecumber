package com.trivago.rta.json.pojo;

import java.util.List;

public class Report {
    private int line;
    private List<Element> elements;
    private String name;
    private String description;
    private String id;
    private String keyword;
    private String uri;

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(final List<Element> elements) {
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Report{" +
                "line=" + line +
                ", elements=" + elements +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", keyword='" + keyword + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
