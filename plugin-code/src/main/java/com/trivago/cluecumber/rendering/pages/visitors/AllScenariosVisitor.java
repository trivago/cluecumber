package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.PageCollection;
import com.trivago.cluecumber.rendering.pages.renderers.AllScenariosPageRenderer;
import com.trivago.cluecumber.rendering.pages.renderers.PageRenderer;

import javax.inject.Inject;

public class AllScenariosVisitor implements PageVisitor {

    private PageRenderer pageRenderer;

    @Inject
    public AllScenariosVisitor(final AllScenariosPageRenderer pageRenderer) {
        this.pageRenderer = pageRenderer;
    }

    @Override
    public void visit(final PageCollection pageCollection) {
        System.out.println("Using renderer " + pageRenderer);
    }
}
