package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;

public interface PageVisitor {
    void visit(final AllScenariosPageCollection pageCollection) throws CluecumberException;
}
