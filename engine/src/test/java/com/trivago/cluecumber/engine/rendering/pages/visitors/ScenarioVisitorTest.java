package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.renderering.ScenarioDetailsPageRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ScenarioVisitorTest extends VisitorTest {

    private ScenarioVisitor scenarioVisitor;
    private ScenarioDetailsPageRenderer scenarioDetailsPageRenderer;

    @BeforeEach
    public void setUp()  {
        super.setUp();
        scenarioDetailsPageRenderer = mock(ScenarioDetailsPageRenderer.class);
        scenarioVisitor = new ScenarioVisitor(
                fileIo,
                templateEngine,
                propertyManager,
                allScenariosPageRenderer,
                scenarioDetailsPageRenderer
        );
    }

    @Test
    public void visitTest() throws CluecumberException {
        when(allScenariosPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedScenarios");
        when(scenarioDetailsPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedScenarioDetails");
        scenarioVisitor.visit(getAllScenarioPageCollection());
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/scenario-summary.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/scenario-sequence.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarioDetails", "dummyPath/pages/scenario-detail/scenario_0.html");
    }
}