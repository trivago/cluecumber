package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

public class Link {
    private final String name;
    private final String target;
    private final LinkType type;

    public Link(String name, String target, LinkType type) {
        this.name = name;
        this.target = target;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    public LinkType getType() {
        return type;
    }
}
