package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;

/**
 * Base interface for page collections to accept visitor classes.
 */
public interface Visitable {
    void accept(final PageVisitor visitor) throws CluecumberException;
}
