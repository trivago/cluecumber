package com.trivago.rta.json;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.postprocessors.ElementPostProcessor;
import com.trivago.rta.json.postprocessors.ReportPostProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JsonPojoConverterTest {
    private JsonPojoConverter pojoConverter;

    @Before
    public void setup() {
        ElementPostProcessor elementPostProcessor = mock(ElementPostProcessor.class);
        ReportPostProcessor reportPostProcessor = mock(ReportPostProcessor.class);
        pojoConverter = new JsonPojoConverter(reportPostProcessor, elementPostProcessor);
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
        assertThat(report.toString(), is("Report{line=1, elements=[Element{before=[ResultMatch{result=Result{duration=5554929, status='passed', errorMessage=''}, match=Match{location='BeforeAfterScenario.before(Scenario)', arguments=[]}, output=[], embeddings=[]}], line=5, name='Test feature', description='', id='test;id', after=[ResultMatch{result=Result{duration=153270, status='passed', errorMessage=''}, match=Match{location='BeforeAfterScenario.after(Scenario)', arguments=[]}, output=[], embeddings=[]}], type='scenario', keyword='Scenario', steps=[Step{line=7, name='the start page is opened', keyword='Given ', rows=[]}, Step{line=8, name='I see something', keyword='Then ', rows=[]}], tags=[Tag{name='@sometag'}, Tag{name='@someothertag'}], scenarioIndex=-1}], name='Test', description='', id='test', keyword='Feature', uri='parallel/features/Test.feature', featureIndex=-1}"));
    }

    @Test(expected = CluecumberPluginException.class)
    public void convertJsonToReportPojosInvalidTest() throws CluecumberPluginException {
        pojoConverter.convertJsonToReportPojos("!$%&§/");
    }
}
