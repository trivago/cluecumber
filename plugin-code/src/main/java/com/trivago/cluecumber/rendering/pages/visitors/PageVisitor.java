package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;

public interface PageVisitor {
    void visit(final AllScenariosPageCollection pageCollection) throws CluecumberPluginException;
}
