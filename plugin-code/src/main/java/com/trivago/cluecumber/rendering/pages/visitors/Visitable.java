package com.trivago.cluecumber.rendering.pages.visitors;

public interface Visitable {
    void accept(final PageVisitor visitor);
}
