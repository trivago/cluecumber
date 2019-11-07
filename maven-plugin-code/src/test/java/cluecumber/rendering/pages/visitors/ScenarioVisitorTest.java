package cluecumber.rendering.pages.visitors;

import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.rendering.pages.renderering.ScenarioDetailsPageRenderer;
import com.trivago.cluecumberCore.rendering.pages.visitors.ScenarioVisitor;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScenarioVisitorTest extends VisitorTest {

    private ScenarioVisitor scenarioVisitor;
    private ScenarioDetailsPageRenderer scenarioDetailsPageRenderer;

    @Override
    public void setUp() throws CluecumberPluginException {
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
    public void visitTest() throws CluecumberPluginException {
        when(allScenariosPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedScenarios");
        when(scenarioDetailsPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedScenarioDetails");
        scenarioVisitor.visit(getAllScenarioPageCollection());
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/index.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/scenario-sequence.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarioDetails", "dummyPath/pages/scenario-detail/scenario_0.html");
    }
}