package com.trivago.cluecumber.engine.rendering.pages.charts;

import com.trivago.cluecumber.engine.constants.ChartConfiguration;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Axis;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Data;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Dataset;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Options;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.ScaleLabel;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Scales;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Ticks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChartJsonConverterTest {

    private ChartJsonConverter chartJsonConverter;

    @BeforeEach
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
                "          340.0,\n" +
                "          0.0,\n" +
                "          0.0,\n" +
                "          440.0,\n" +
                "          340.0,\n" +
                "          0.0\n" +
                "        ],\n" +
                "        \"backgroundColor\": [\n" +
                "          \"rgba(240,0,0,1.000)\"\n" +
                "        ],\n" +
                "        \"label\": \"failed\",\n" +
                "        \"stack\": \"complete\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"data\": [\n" +
                "          0.0,\n" +
                "          340.0,\n" +
                "          440.0,\n" +
                "          0.0,\n" +
                "          0.0,\n" +
                "          340.0\n" +
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
                "      \"x\": {\n" +
                "        \"ticks\": {\n" +
                "          \"min\": 0,\n" +
                "          \"stepSize\": 0.0,\n" +
                "          \"display\": true\n" +
                "        },\n" +
                "        \"stacked\": false,\n" +
                "        \"title\": {\n" +
                "          \"display\": true,\n" +
                "          \"text\": \"Step Number\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"y\": {\n" +
                "        \"ticks\": {\n" +
                "          \"min\": 0,\n" +
                "          \"stepSize\": 0.0,\n" +
                "          \"display\": true\n" +
                "        },\n" +
                "        \"stacked\": true,\n" +
                "        \"title\": {\n" +
                "          \"display\": true,\n" +
                "          \"text\": \"Step Runtime\"\n" +
                "        }\n" +
                "      }\n" +
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

        List<Float> data1 = new ArrayList<>();
        data1.add(340f);
        data1.add(0f);
        data1.add(0f);
        data1.add(440f);
        data1.add(340f);
        data1.add(0f);
        dataset1.setData(data1);
        dataset1.setLabel("failed");
        dataset1.setStack("complete");
        dataset1.setBackgroundColor(new ArrayList<>(Collections.singletonList("rgba(240,0,0,1.000)")));
        datasets.add(dataset1);

        Dataset dataset2 = new Dataset();
        List<Float> data2 = new ArrayList<>();
        data2.add(0f);
        data2.add(340f);
        data2.add(440f);
        data2.add(0f);
        data2.add(0f);
        data2.add(340f);
        dataset2.setData(data2);
        dataset2.setLabel("passed");
        dataset2.setStack("complete");
        dataset2.setBackgroundColor(new ArrayList<>(Collections.singletonList("rgba(0,240,0,1.000)")));
        datasets.add(dataset2);

        data.setDatasets(datasets);

        chart.setData(data);

        Options options = new Options();
        Scales scales = new Scales();
        Axis xAxis = new Axis();
        Ticks xTicks = new Ticks();
        xAxis.setTicks(xTicks);
        ScaleLabel xTitle = new ScaleLabel();
        xTitle.setDisplay(true);
        xTitle.setText("Step Number");
        xAxis.setTitle(xTitle);
        scales.setX(xAxis);

        Axis yAxis = new Axis();
        yAxis.setStacked(true);
        Ticks yTicks = new Ticks();
        yAxis.setTicks(yTicks);
        ScaleLabel yTitle = new ScaleLabel();
        yTitle.setDisplay(true);
        yTitle.setText("Step Runtime");
        yAxis.setTitle(yTitle);
        scales.setY(yAxis);

        options.setScales(scales);
        chart.setOptions(options);

        chart.setType(ChartConfiguration.Type.bar);

        assertEquals(chartJsonConverter.convertChartToJson(chart), expected);
    }

}
