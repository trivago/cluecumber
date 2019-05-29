package com.trivago.cluecumber.rendering.visitors;

import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.PageCollection;

public class AllScenariosVisitor implements PageVisitor {

    @Override
    public void visit(final PageCollection pageCollection) {
        System.out.println("I am visiting the page collection " + pageCollection.getClass().getName());
    }
}
