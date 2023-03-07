package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;

/**
 * Base interface for the visitor pattern.
 */
public interface PageVisitor {
    /**
     * Base method for all visitable classes.
     *
     * @param pageCollection The {@link AllScenariosPageCollection} instance.
     */
    void visit(final AllScenariosPageCollection pageCollection) throws CluecumberException;
}
