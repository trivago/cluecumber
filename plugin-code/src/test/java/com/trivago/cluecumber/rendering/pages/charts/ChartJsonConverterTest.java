package com.trivago.cluecumber.rendering.pages.charts;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Axis;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Data;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Dataset;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Options;
import com.trivago.cluecumber.rendering.pages.charts.pojos.ScaleLabel;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Scales;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Ticks;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChartJsonConverterTest {

    private ChartJsonConverter chartJsonConverter;

    @Before
    public void setup() {
        chartJsonConverter = new ChartJsonConverter();
    }

    @Test
    public void getJsonTest() {
        String expected = "{\n" +
                "  \"data\": {\n" +
                "    \"labels\": [\n" +
                "      \"1\",\n" +
                "      \"2\",\n" +
                "      \"3\",\n" +
                "      \"4\",\n" +
                "      \"5\",\n" +
                "      \"6\"\n" +
                "    ],\n" +
                "    \"datasets\": [\n" +
                "      {\n" +
                "        \"data\": [\n" +
                "          340,\n" +
                "          0,\n" +
                "          0,\n" +
                "          440,\n" +
                "          340,\n" +
                "          0\n" +
                "        ],\n" +
                "        \"backgroundColor\": [\n" +
                "          \"rgba(240,0,0,1.000)\"\n" +
                "        ],\n" +
                "        \"label\": \"failed\",\n" +
                "        \"stack\": \"complete\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"data\": [\n" +
                "          0,\n" +
                "          340,\n" +
                "          440,\n" +
                "          0,\n" +
                "          0,\n" +
                "          340\n" +
                "        ],\n" +
                "        \"backgroundColor\": [\n" +
                "          \"rgba(0,240,0,1.000)\"\n" +
                "        ],\n" +
                "        \"label\": \"passed\",\n" +
                "        \"stack\": \"complete\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"options\": {\n" +
                "    \"scales\": {\n" +
                "      \"xAxes\": [\n" +
                "        {\n" +
                "          \"ticks\": {\n" +
                "            \"min\": 0,\n" +
                "            \"stepSize\": 0,\n" +
                "            \"display\": true\n" +
                "          },\n" +
                "          \"stacked\": false,\n" +
                "          \"scaleLabel\": {\n" +
                "            \"display\": true,\n" +
                "            \"labelString\": \"Step Number\"\n" +
                "          },\n" +
                "          \"stepSize\": 0.0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"yAxes\": [\n" +
                "        {\n" +
                "          \"ticks\": {\n" +
                "            \"min\": 0,\n" +
                "            \"stepSize\": 0,\n" +
                "            \"display\": true\n" +
                "          },\n" +
                "          \"stacked\": true,\n" +
                "          \"scaleLabel\": {\n" +
                "            \"display\": true,\n" +
                "            \"labelString\": \"Step Runtime\"\n" +
                "          },\n" +
                "          \"stepSize\": 0.0\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"type\": \"bar\"\n" +
                "}";

        Chart chart = new Chart();

        Data data = new Data();

        List<String> labels = new ArrayList<>();
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        data.setLabels(labels);

        List<Dataset> datasets = new ArrayList<>();

        Dataset dataset1 = new Dataset();

        List<Integer> data1 = new ArrayList<>();
        data1.add(340);
        data1.add(0);
        data1.add(0);
        data1.add(440);
        data1.add(340);
        data1.add(0);
        dataset1.setData(data1);
        dataset1.setLabel("failed");
        dataset1.setStack("complete");
        dataset1.setBackgroundColor(new ArrayList<>(Collections.singletonList("rgba(240,0,0,1.000)")));
        datasets.add(dataset1);

        Dataset dataset2 = new Dataset();
        List<Integer> data2 = new ArrayList<>();
        data2.add(0);
        data2.add(340);
        data2.add(440);
        data2.add(0);
        data2.add(0);
        data2.add(340);
        dataset2.setData(data2);
        dataset2.setLabel("passed");
        dataset2.setStack("complete");
        dataset2.setBackgroundColor(new ArrayList<>(Collections.singletonList("rgba(0,240,0,1.000)")));
        datasets.add(dataset2);

        data.setDatasets(datasets);

        chart.setData(data);

        Options options = new Options();
        Scales scales = new Scales();
        List<Axis> xAxes = new ArrayList<>();
        Axis xAxis = new Axis();
        Ticks xTicks = new Ticks();
        xAxis.setTicks(xTicks);
        ScaleLabel xScaleLabel = new ScaleLabel();
        xScaleLabel.setDisplay(true);
        xScaleLabel.setLabelString("Step Number");
        xAxis.setScaleLabel(xScaleLabel);
        xAxes.add(xAxis);
        scales.setxAxes(xAxes);

        List<Axis> yAxes = new ArrayList<>();
        Axis yAxis = new Axis();
        yAxis.setStacked(true);
        Ticks yTicks = new Ticks();
        yAxis.setTicks(yTicks);
        ScaleLabel yScaleLabel = new ScaleLabel();
        yScaleLabel.setDisplay(true);
        yScaleLabel.setLabelString("Step Runtime");
        yAxis.setScaleLabel(yScaleLabel);
        yAxes.add(yAxis);
        scales.setyAxes(yAxes);

        options.setScales(scales);
        chart.setOptions(options);

        chart.setType(ChartConfiguration.Type.bar);

        assertThat(chartJsonConverter.convertChartToJson(chart), is(expected));
    }

}
