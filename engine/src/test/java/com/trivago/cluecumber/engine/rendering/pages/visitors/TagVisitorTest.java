package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.renderering.AllTagsPageRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagVisitorTest extends VisitorTest {

    private TagVisitor tagVisitor;
    private AllTagsPageRenderer allTagsPageRenderer;

    @BeforeEach
    public void setUp() {
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
    public void visitTest() throws CluecumberException {
        when(allTagsPageRenderer.getRenderedContent(any(), any())).thenReturn("MyRenderedTags");
        when(allScenariosPageRenderer.getRenderedContentByTagFilter(any(), any(), any())).thenReturn("MyRenderedScenarios");
        tagVisitor.visit(getAllScenarioPageCollection());
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedTags", "dummyPath/pages/tag-summary.html");
        verify(fileIo, times(1))
                .writeContentToFile("MyRenderedScenarios", "dummyPath/pages/tag-scenarios/tag_myTag.html");
    }
}