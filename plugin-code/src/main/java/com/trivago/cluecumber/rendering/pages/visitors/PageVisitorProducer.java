package com.trivago.cluecumber.rendering.pages.visitors;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class PageVisitorProducer implements Provider<List<PageVisitor>> {

    private final List<PageVisitor> pageVisitors;

    @Inject
    public PageVisitorProducer(
            final AllScenariosVisitor allScenariosVisitor,
            final AllFeaturesVisitor allFeaturesVisitor
    ) {
        pageVisitors = new ArrayList<>();
        pageVisitors.add(allScenariosVisitor);
        pageVisitors.add(allFeaturesVisitor);
    }

    @Override
    public List<PageVisitor> get() {
        return pageVisitors;
    }
}
