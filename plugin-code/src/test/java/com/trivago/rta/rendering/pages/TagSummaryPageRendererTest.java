package com.trivago.rta.rendering.pages;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.rendering.pages.pojos.TagSummaryPageCollection;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TagSummaryPageRendererTest {

    private TagSummaryPageRenderer tagSummaryPageRenderer;

    @Before
    public void setup() {
        tagSummaryPageRenderer = new TagSummaryPageRenderer();
    }

    @Test
    public void testContentRendering() throws CluecumberPluginException {
        Template template = mock(Template.class);
        Report report = new Report();
        List<Report> reports = new ArrayList<>();
        reports.add(report);
        TagSummaryPageCollection tagSummaryPageCollection = new TagSummaryPageCollection(reports);
        tagSummaryPageRenderer.getRenderedContent(tagSummaryPageCollection, template);
    }
}
