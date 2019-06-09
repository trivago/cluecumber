package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.pojo.Tag;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.rendering.pages.renderering.AllFeaturesPageRenderer;
import com.trivago.cluecumber.rendering.pages.renderering.AllScenariosPageRenderer;
import com.trivago.cluecumber.rendering.pages.templates.TemplateEngine;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FeatureVisitorTest extends VisitorTest {

    private FeatureVisitor featureVisitor;

    @Override
    public void setUp() throws CluecumberPluginException {
        super.setUp();
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