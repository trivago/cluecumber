package com.trivago.cluecumber.rendering.pages.visitors;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class VisitorDirectoryTest {
    private VisitorDirectory visitorDirectory;

    @Before
    public void setUp() {
        ScenarioVisitor scenarioVisitor = mock(ScenarioVisitor.class);
        FeatureVisitor featureVisitor = mock(FeatureVisitor.class);
        TagVisitor tagVisitor = mock(TagVisitor.class);
        StepVisitor stepVisitor = mock(StepVisitor.class);
        visitorDirectory = new VisitorDirectory(
                scenarioVisitor,
                featureVisitor,
                tagVisitor,
                stepVisitor
        );
    }

    @Test
    public void getVisitorDirectoryTest() {
        assertThat(visitorDirectory.getVisitors().size(), is(4));
    }
}
