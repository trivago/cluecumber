package com.trivago.cluecumber.rendering.pages.visitors;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class VisitorDirectory {

    private final ArrayList<PageVisitor> visitors;

    @Inject
    public VisitorDirectory(
            final ScenarioVisitor scenarioVisitor,
            final FeatureVisitor featureVisitor,
            final TagVisitor tagVisitor,
            final StepVisitor stepVisitor
    ) {
        visitors = new ArrayList<>();
        visitors.add(scenarioVisitor);
        visitors.add(featureVisitor);
        visitors.add(tagVisitor);
        visitors.add(stepVisitor);
    }

    public List<PageVisitor> getVisitors() {
        return visitors;
    }
}
