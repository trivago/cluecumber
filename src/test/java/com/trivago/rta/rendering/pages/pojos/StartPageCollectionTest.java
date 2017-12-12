package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartPageCollectionTest {

    private StartPageCollection startPageCollection;

    @Before
    public void setup() {
        startPageCollection = new StartPageCollection();
    }

    @Test
    public void addReportsNullReportListTest() {
        startPageCollection.addReports(null);
        assertThat(startPageCollection.getReports().size(), is(0));
        assertThat(startPageCollection.getTotalNumberOfFeatures(), is(0));
        assertThat(startPageCollection.getTotalNumberOfScenarios(), is(0));
    }

    @Test
    public void addReportsValidReportListTest() {
        Report[] reportList = new Report[2];
        reportList[0] = new Report();
        reportList[1] = new Report();
        startPageCollection.addReports(reportList);
        assertThat(startPageCollection.getReports().size(), is(2));
        assertThat(startPageCollection.getTotalNumberOfFeatures(), is(2));
        assertThat(startPageCollection.getTotalNumberOfScenarios(), is(0));
    }

    @Test
    public void addReportsValidReportListWithScenariosTest() {
        Report[] reportList = new Report[1];

        Report report1 = new Report();
        List<Element> elements = new ArrayList<>();
        Element element1 = new Element();
        element1.setType("scenario");
        elements.add(element1);
        report1.setElements(elements);
        reportList[0] = report1;

        startPageCollection.addReports(reportList);
        assertThat(startPageCollection.getReports().size(), is(1));
        assertThat(startPageCollection.getTotalNumberOfFeatures(), is(1));
        assertThat(startPageCollection.getTotalNumberOfScenarios(), is(1));
    }

    @Test
    public void getTotalDurationDefaultValueTest() {
        long totalDuration = startPageCollection.getTotalDuration();
        assertThat(totalDuration, is(0L));
    }

}
