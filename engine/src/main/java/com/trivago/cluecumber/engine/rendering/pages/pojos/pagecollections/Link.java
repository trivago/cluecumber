package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

/**
 * Represents a link in the generated report.
 */
public class Link {
    private final String name;
    private final String target;
    private final LinkType type;

    /**
     * Default constructor.
     *
     * @param name   The name to display.
     * @param target The link target.
     * @param type   The type of link (see {@link LinkType}).
     */
    public Link(String name, String target, LinkType type) {
        this.name = name;
        this.target = target;
        this.type = type;
    }

    /**
     * Get the name of the link.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the target of the link.
     *
     * @return The target.
     */
    public String getTarget() {
        return target;
    }

    /**
     * Get the type of the link (internal or external).
     *
     * @return The type.
     */
    public LinkType getType() {
        return type;
    }
}
