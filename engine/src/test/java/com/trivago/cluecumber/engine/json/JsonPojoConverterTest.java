package com.trivago.cluecumber.engine.json;

import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.processors.ElementJsonPostProcessor;
import com.trivago.cluecumber.engine.json.processors.ReportJsonPostProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class JsonPojoConverterTest {
    private JsonPojoConverter pojoConverter;

    @BeforeEach
    public void setup() {
        ElementJsonPostProcessor elementJsonPostProcessor = mock(ElementJsonPostProcessor.class);
        ReportJsonPostProcessor reportJsonPostProcessor = mock(ReportJsonPostProcessor.class);
        pojoConverter = new JsonPojoConverter(
                reportJsonPostProcessor,
                elementJsonPostProcessor
        );
    }

    @Test
    public void convertEmptyJsonToReportPojosTest() throws CluecumberException {
        String json = "";
        Report[] reports = pojoConverter.convertJsonToReportPojos(json);
        assertNull(reports);
    }

    @Test
    public void convertJsonToReportPojosTest() throws CluecumberException {
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
        assertEquals(reports.length, 1);
        Report report = reports[0];
        assertEquals(report.getName(), "Test");
        assertEquals(report.getId(), "test");
        assertEquals(report.getFeatureIndex(), -1);
        assertEquals(report.getTotalDuration(), 12751234816L);
        assertEquals(report.getDescription(), "");
        assertEquals(report.getLine(), 1);
        assertEquals(report.getUri(), "parallel/features/Test.feature");
        assertEquals(report.getElements().size(), 1);
        Element element = report.getElements().get(0);
        assertEquals(element.getStatus(), Status.PASSED);
        assertEquals(element.getSteps().size(), 2);
        assertEquals(element.getBefore().size(), 1);
        assertEquals(element.getAfter().size(), 1);
        assertEquals(element.getScenarioIndex(), 0);
        assertEquals(element.getTotalDuration(), 12751234816L);
        assertEquals(element.getTotalNumberOfPassedSteps(), 2);
        assertEquals(element.getTotalNumberOfSkippedSteps(), 0);
        assertEquals(element.getTotalNumberOfFailedSteps(), 0);
        assertEquals(element.getAllResultMatches().size(), 4);
    }

    @Test
    public void convertJsonToReportPojosInvalidTest() {
        assertThrows(CluecumberException.class, () -> pojoConverter.convertJsonToReportPojos("!$%&§/"));
    }
}
