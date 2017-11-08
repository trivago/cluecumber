package com.trivago.rta.json.pojo;

import java.util.ArrayList;
import java.util.List;

public class Step extends ResultMatch {
    private int line;
    private String name;
    private String keyword;
    private List<Embedding> embeddings = new ArrayList<>();

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public List<Embedding> getEmbeddings() {
        return embeddings;
    }

    public void setEmbeddings(final List<Embedding> embeddings) {
        this.embeddings = embeddings;
    }

    @Override
    public String toString() {
        return "Step{" +
                "line=" + line +
                ", name='" + name + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
