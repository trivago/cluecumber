package com.trivago.cluecumber.engine.rendering.pages.visitors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class VisitorDirectoryTest {
    private VisitorDirectory visitorDirectory;

    @BeforeEach
    public void setUp() {
        ScenarioVisitor scenarioVisitor = mock(ScenarioVisitor.class);
        FeatureVisitor featureVisitor = mock(FeatureVisitor.class);
        TagVisitor tagVisitor = mock(TagVisitor.class);
        ExceptionVisitor exceptionVisitor = mock(ExceptionVisitor.class);
        StepVisitor stepVisitor = mock(StepVisitor.class);
        CustomViewVisitor customViewVisitor = mock(CustomViewVisitor.class);
        visitorDirectory = new VisitorDirectory(
                scenarioVisitor,
                featureVisitor,
                tagVisitor,
                exceptionVisitor,
                stepVisitor,
                customViewVisitor
        );
    }

    @Test
    public void getVisitorDirectoryTest() {
        assertEquals(visitorDirectory.getVisitors().size(), 6);
    }
}
