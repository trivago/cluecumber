package cluecumber.rendering.pages.visitors;

import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;
import com.trivago.cluecumberCore.rendering.pages.renderering.AllFeaturesPageRenderer;
import com.trivago.cluecumberCore.rendering.pages.visitors.FeatureVisitor;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeatureVisitorTest extends VisitorTest {

    private FeatureVisitor featureVisitor;
    private AllFeaturesPageRenderer allFeaturesPageRenderer;

    @Override
    public void setUp() throws CluecumberPluginException {
        super.setUp();
        allFeaturesPageRenderer = mock(AllFeaturesPageRenderer.class);
        featureVisitor = new FeatureVisitor(
                fileIo,
                templateEngine,
                propertyManager,
                allFeaturesPageRenderer,
                allScenariosPageRenderer
        );
    }

    @Test
    public void visitTest() throws CluecumberPluginException {
        when(allFeaturesPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedFeatures");
        when(allScenariosPageRenderer.getRenderedContentByFeatureFilter(any(), any(), any())).thenReturn("MyRenderedScenarios");
        featureVisitor.visit(getAllScenarioPageCollection());
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedFeatures", "dummyPath/pages/feature-summary.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/feature-scenarios/feature_12.html");
    }
}