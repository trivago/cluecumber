package cluecumber.rendering.pages.visitors;

import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllStepsPageRenderer;
import com.trivago.cluecumberCore.rendering.pages.visitors.StepVisitor;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StepVisitorTest extends VisitorTest {

    private StepVisitor stepVisitor;
    private AllStepsPageRenderer allStepsPageRenderer;

    @Override
    public void setUp() throws CluecumberPluginException {
        super.setUp();
        allStepsPageRenderer = mock(AllStepsPageRenderer.class);
        stepVisitor = new StepVisitor(
                fileIo,
                templateEngine,
                propertyManager,
                allStepsPageRenderer,
                allScenariosPageRenderer
        );
    }

    @Test
    public void visitTest() throws CluecumberPluginException {
        when(allStepsPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedSteps");
        when(allScenariosPageRenderer.getRenderedContentByStepFilter(any(), any(), any())).thenReturn("MyRenderedScenarios");
        stepVisitor.visit(getAllScenarioPageCollection());
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedSteps", "dummyPath/pages/step-summary.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/step-scenarios/step_31.html");
    }
}