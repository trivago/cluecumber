package com.trivago.cluecumber.json;

import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.processors.ElementJsonPostProcessor;
import com.trivago.cluecumber.json.processors.ReportJsonPostProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class JsonPojoConverterTest {
    private JsonPojoConverter pojoConverter;

    @Before
    public void setup() {
        ElementJsonPostProcessor elementJsonPostProcessor = mock(ElementJsonPostProcessor.class);
        ReportJsonPostProcessor reportJsonPostProcessor = mock(ReportJsonPostProcessor.class);
        pojoConverter = new JsonPojoConverter(
                reportJsonPostProcessor,
                elementJsonPostProcessor
        );
    }

    @Test
    public void convertEmptyJsonToReportPojosTest() throws CluecumberPluginException {
        String json = "";
        Report[] reports = pojoConverter.convertJsonToReportPojos(json);
        assertThat(reports, is(nullValue()));
    }

    @Test
    public void convertJsonToReportPojosTest() throws CluecumberPluginException {
        String json = "[\n" +
                "  {\n" +
                "    \"line\": 1,\n" +
                "    \"elements\": [\n" +
                "      {\n" +
                "        \"before\": [\n" +
                "          {\n" +
                "            \"result\": {\n" +
                "              \"duration\": 5554929,\n" +
                "              \"status\": \"passed\"\n" +
                "            },\n" +
                "            \"match\": {\n" +
                "              \"location\": \"BeforeAfterScenario.before(Scenario)\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"line\": 5,\n" +
                "        \"name\": \"Test feature\",\n" +
                "        \"description\": \"\",\n" +
                "        \"id\": \"test;id\",\n" +
                "        \"after\": [\n" +
                "          {\n" +
                "            \"result\": {\n" +
                "              \"duration\": 153270,\n" +
                "              \"status\": \"passed\"\n" +
                "            },\n" +
                "            \"match\": {\n" +
                "              \"location\": \"BeforeAfterScenario.after(Scenario)\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"type\": \"scenario\",\n" +
                "        \"keyword\": \"Scenario\",\n" +
                "        \"steps\": [\n" +
                "          {\n" +
                "            \"result\": {\n" +
                "              \"duration\": 12453061125,\n" +
                "              \"status\": \"passed\"\n" +
                "            },\n" +
                "            \"line\": 7,\n" +
                "            \"name\": \"the start page is opened\",\n" +
                "            \"match\": {\n" +
                "              \"location\": \"PageSteps.theStartPageIsOpened()\"\n" +
                "            },\n" +
                "            \"keyword\": \"Given \"\n" +
                "          },\n" +
                "          {\n" +
                "            \"result\": {\n" +
                "              \"duration\": 292465492,\n" +
                "              \"status\": \"passed\"\n" +
                "            },\n" +
                "            \"line\": 8,\n" +
                "            \"name\": \"I see something\",\n" +
                "            \"match\": {\n" +
                "              \"location\": \"SomeSteps.iSeeSomething()\"\n" +
                "            },\n" +
                "            \"keyword\": \"Then \"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"tags\": [\n" +
                "          {\n" +
                "            \"line\": 3,\n" +
                "            \"name\": \"@sometag\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"line\": 4,\n" +
                "            \"name\": \"@someothertag\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"name\": \"Test\",\n" +
                "    \"description\": \"\",\n" +
                "    \"id\": \"test\",\n" +
                "    \"keyword\": \"Feature\",\n" +
                "    \"uri\": \"parallel/features/Test.feature\"\n" +
                "  }\n" +
                "]";
        Report[] reports = pojoConverter.convertJsonToReportPojos(json);
        assertThat(reports.length, is(1));
        Report report = reports[0];
        assertThat(report.getName(), is("Test"));
        assertThat(report.getId(), is("test"));
        assertThat(report.getFeatureIndex(), is(-1));
        assertThat(report.getTotalDuration(), is(12751234816L));
        assertThat(report.getDescription(), is(""));
        assertThat(report.getLine(), is(1));
        assertThat(report.getUri(), is("parallel/features/Test.feature"));
        assertThat(report.getElements().size(), is(1));
        Element element = report.getElements().get(0);
        assertThat(element.getStatus(), is(Status.PASSED));
        assertThat(element.getSteps().size(), is(2));
        assertThat(element.getBefore().size(), is(1));
        assertThat(element.getAfter().size(), is(1));
        assertThat(element.getScenarioIndex(), is(0));
        assertThat(element.getTotalDuration(), is(12751234816L));
        assertThat(element.getTotalNumberOfPassedSteps(), is(2));
        assertThat(element.getTotalNumberOfSkippedSteps(), is(0));
        assertThat(element.getTotalNumberOfFailedSteps(), is(0));
        assertThat(element.getAllResultMatches().size(), is(4));
    }

    @Test(expected = CluecumberPluginException.class)
    public void convertJsonToReportPojosInvalidTest() throws CluecumberPluginException {
        pojoConverter.convertJsonToReportPojos("!$%&§/");
    }
}
