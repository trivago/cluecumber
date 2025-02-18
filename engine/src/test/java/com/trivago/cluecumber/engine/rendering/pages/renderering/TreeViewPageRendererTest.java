package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllFeaturesPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.TreeViewPageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TreeViewPageRendererTest {

    private TreeViewPageRenderer treeViewPageRenderer;
    private PropertyManager propertyManager;

    @BeforeEach
    void setup() {
        propertyManager = mock(PropertyManager.class);
        treeViewPageRenderer = new TreeViewPageRenderer(propertyManager);
    }

    @Test
    void testContentRenderingWithoutPathTreeView() throws CluecumberException, TemplateException, IOException {
        Template template = mock(Template.class);
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(
                List.of(aReportWithUri("/features/foo/feature1.feature")), "All Features");
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");

        givenNoPathTreeView();

        ArgumentCaptor<TreeViewPageCollection> pageCollectionCaptor = ArgumentCaptor.forClass(TreeViewPageCollection.class);

        treeViewPageRenderer.getRenderedContent(
                allFeaturesPageCollection,
                allScenariosPageCollection,
                template
        );

        verify(template, times(1)).process(pageCollectionCaptor.capture(), any());

        TreeViewPageCollection capturedPageCollection = pageCollectionCaptor.getValue();
        assertNotNull(capturedPageCollection);
        assertEquals("All Features", capturedPageCollection.getPageTitle());
        final Path expectedPath = Path.of("/");
        assertEquals(capturedPageCollection.getPaths(), Set.of(expectedPath));
        List<Feature> featuresAtPath = capturedPageCollection.getFeaturesByPath().get(expectedPath)
                .stream().map(TreeViewPageCollection.FeatureAndScenarios::getFeature).collect(Collectors.toList());
        assertTrue(featuresAtPath.containsAll(allFeaturesPageCollection.getFeatures()));
    }

    @Test
    void testContentRenderingWithPathTreeView() throws CluecumberException, TemplateException, IOException {
        Template template = mock(Template.class);
        AllFeaturesPageCollection allFeaturesPageCollection = new AllFeaturesPageCollection(
                List.of(aReportWithUri("/features/customers/registration/form_validation/email_address.feature")), "All Features");
        AllScenariosPageCollection allScenariosPageCollection = new AllScenariosPageCollection("");

        when(propertyManager.isGroupFeaturesByPath()).thenReturn(true);
        when(propertyManager.getRemovableBasePaths()).thenReturn(BasePaths.fromStrings(List.of("/features")));
        when(propertyManager.getDirectoryNameFormatter()).thenReturn(new DirectoryNameFormatter.SnakeCase());

        ArgumentCaptor<TreeViewPageCollection> pageCollectionCaptor = ArgumentCaptor.forClass(TreeViewPageCollection.class);

        treeViewPageRenderer.getRenderedContent(
                allFeaturesPageCollection,
                allScenariosPageCollection,
                template
        );

        verify(template, times(1)).process(pageCollectionCaptor.capture(), any());

        TreeViewPageCollection capturedPageCollection = pageCollectionCaptor.getValue();
        assertNotNull(capturedPageCollection);
        assertEquals("All Features", capturedPageCollection.getPageTitle());

        final Path expectedPath = Path.of("/customers/registration/form_validation");
        assertEquals(capturedPageCollection.getPaths(), Set.of(expectedPath));
        List<Feature> featuresAtPath = capturedPageCollection.getFeaturesByPath().get(expectedPath)
                .stream().map(TreeViewPageCollection.FeatureAndScenarios::getFeature).collect(Collectors.toList());
        assertTrue(featuresAtPath.containsAll(allFeaturesPageCollection.getFeatures()));
    }

    private void givenNoPathTreeView() {
        when(propertyManager.isGroupFeaturesByPath()).thenReturn(false);
    }

    private static Report aReportWithUri(String uri) {
        Report report = new Report();
        report.setName("Feature 1");
        report.setDescription("Description");
        report.setFeatureIndex(1);
        report.setUri(uri);
        return report;
    }
}