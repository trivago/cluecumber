package com.trivago.cluecumber.rendering.visitors;

public interface Visitable {
    void accept(final PageVisitor visitor);
}
