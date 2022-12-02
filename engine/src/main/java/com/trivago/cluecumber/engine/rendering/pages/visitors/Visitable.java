package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;

public interface Visitable {
    void accept(final PageVisitor visitor) throws CluecumberException;
}
