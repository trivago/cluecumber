package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.renderering.AllFeaturesPageRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeatureVisitorTest extends VisitorTest {

    private FeatureVisitor featureVisitor;
    private AllFeaturesPageRenderer allFeaturesPageRenderer;

    @BeforeEach
    public void setUp()  {
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
    public void visitTest() throws CluecumberException {
        when(allFeaturesPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedFeatures");
        when(allScenariosPageRenderer.getRenderedContentByFeatureFilter(any(), any(), any())).thenReturn("MyRenderedScenarios");
        featureVisitor.visit(getAllScenarioPageCollection());
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedFeatures", "dummyPath/pages/feature-summary.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/feature-scenarios/feature_12.html");
    }
}