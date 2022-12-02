package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Result;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.json.pojo.Tag;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllTagsPageCollectionTest {
    private AllTagsPageCollection allTagsPageCollection;

    @Test
    public void getEmptyTagStatsTest() {
        List<Report> reports = new ArrayList<>();
        allTagsPageCollection = new AllTagsPageCollection(reports, "");
        Map<Tag, ResultCount> tagStats = allTagsPageCollection.getTagResultCounts();
        assertEquals(tagStats.size(), 0);
    }

    @Test
    public void getTagStatsTest() {
        allTagsPageCollection = new AllTagsPageCollection(getTestReports(), "");
        Map<Tag, ResultCount> tagStats = allTagsPageCollection.getTagResultCounts();
        assertEquals(tagStats.size(), 3);

        Tag tag1 = new Tag();
        tag1.setName("tag1");
        ResultCount tag1Stats = tagStats.get(tag1);
        assertEquals(tag1Stats.getTotal(), 1);
        assertEquals(tag1Stats.getPassed(), 0);
        assertEquals(tag1Stats.getFailed(), 1);
        assertEquals(tag1Stats.getSkipped(), 0);

        Tag tag2 = new Tag();
        tag2.setName("tag2");
        ResultCount tag2Stats = tagStats.get(tag2);
        assertEquals(tag2Stats.getTotal(), 2);
        assertEquals(tag2Stats.getPassed(), 1);
        assertEquals(tag2Stats.getFailed(), 1);
        assertEquals(tag2Stats.getSkipped(), 0);

        Tag tag3 = new Tag();
        tag3.setName("tag3");
        ResultCount tag3Stats = tagStats.get(tag3);
        assertEquals(tag3Stats.getTotal(), 1);
        assertEquals(tag3Stats.getPassed(), 0);
        assertEquals(tag3Stats.getFailed(), 0);
        assertEquals(tag3Stats.getSkipped(), 1);
    }

    @Test
    public void getTagResultsTest() {
        allTagsPageCollection = new AllTagsPageCollection(getTestReports(), "");
        assertEquals(allTagsPageCollection.getTotalNumberOfTags(), 3);
        assertEquals(allTagsPageCollection.getTotalNumberOfFailed(), 1);
        assertEquals(allTagsPageCollection.getTotalNumberOfPassed(), 1);
        assertEquals(allTagsPageCollection.getTotalNumberOfSkipped(), 1);
    }

    @Test
    public void getTotalNumberOfTaggedScenariosTest() {
        allTagsPageCollection = new AllTagsPageCollection(getTestReports(), "");
        assertEquals(allTagsPageCollection.getTotalNumberOfScenarios(), 3);
    }

    @Test
    public void getTagsTest() {
        allTagsPageCollection = new AllTagsPageCollection(getTestReports(), "");
        assertEquals(allTagsPageCollection.getTags().size(), 3);
    }

    private List<Report> getTestReports() {
        List<Report> reports = new ArrayList<>();

        Report report = new Report();
        List<Element> elements = new ArrayList<>();

        Element element = new Element();
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("tag1");
        tags.add(tag);
        tag = new Tag();
        tag.setName("tag2");
        tags.add(tag);
        element.setTags(tags);
        List<Step> steps = new ArrayList<>();
        Step step = new Step();
        Result result = new Result();
        result.setStatus(Status.FAILED.getStatusString());
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);

        element = new Element();
        tags = new ArrayList<>();
        tag = new Tag();
        tag.setName("tag2");
        tags.add(tag);
        element.setTags(tags);
        steps = new ArrayList<>();
        step = new Step();
        result = new Result();
        result.setStatus(Status.PASSED.getStatusString());
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);

        element = new Element();
        tags = new ArrayList<>();
        tag = new Tag();
        tag.setName("tag3");
        tags.add(tag);
        element.setTags(tags);
        steps = new ArrayList<>();
        step = new Step();
        result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        step.setResult(result);
        steps.add(step);
        element.setSteps(steps);
        elements.add(element);
        report.setElements(elements);

        reports.add(report);
        return reports;
    }
}
