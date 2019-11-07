package com.trivago.cluecumberCore.rendering.pages.visitors;

import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;

public interface PageVisitor {
    void visit(final AllScenariosPageCollection pageCollection) throws CluecumberPluginException;
}
