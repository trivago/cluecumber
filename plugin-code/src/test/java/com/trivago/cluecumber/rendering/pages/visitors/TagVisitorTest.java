package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.rendering.pages.renderering.AllTagsPageRenderer;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagVisitorTest extends VisitorTest {

    private TagVisitor tagVisitor;
    private AllTagsPageRenderer allTagsPageRenderer;

    @Override
    public void setUp() throws CluecumberPluginException {
        super.setUp();
        allTagsPageRenderer = mock(AllTagsPageRenderer.class);
        tagVisitor = new TagVisitor(
                fileIo,
                templateEngine,
                propertyManager,
                allTagsPageRenderer,
                allScenariosPageRenderer
        );
    }

    @Test
    public void visitTest() throws CluecumberPluginException {
        when(allTagsPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedTags");
        when(allScenariosPageRenderer.getRenderedContentByTagFilter(any(), any(), any())).thenReturn("MyRenderedScenarios");
        tagVisitor.visit(getAllScenarioPageCollection());
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedTags", "dummyPath/pages/tag-summary.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/tag-scenarios/tag_myTag.html");
    }
}