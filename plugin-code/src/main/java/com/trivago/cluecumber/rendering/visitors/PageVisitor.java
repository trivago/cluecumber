package com.trivago.cluecumber.rendering.visitors;

import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.PageCollection;

public interface PageVisitor {
    void visit(final PageCollection pageCollection);
}
