package com.trivago.rta.rendering;

import be.ceau.chart.options.scales.ScaleLabel;
import com.trivago.rta.rendering.charts.pojos.Axis;
import com.trivago.rta.rendering.charts.pojos.Chart;
import com.trivago.rta.rendering.charts.pojos.Data;
import com.trivago.rta.rendering.charts.pojos.DataSet;
import com.trivago.rta.rendering.charts.pojos.Options;
import com.trivago.rta.rendering.charts.pojos.Scales;
import com.trivago.rta.rendering.charts.pojos.Ticks;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChartTest {

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
                "    \"dataSets\": [\n" +
                "      {\n" +
                "        \"data\": [\n" +
                "          340,\n" +
                "          0,\n" +
                "          0,\n" +
                "          440,\n" +
                "          340,\n" +
                "          0\n" +
                "        ],\n" +
                "        \"backgroundColor\": \"rgba(240,0,0,1.000)\",\n" +
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
                "        \"backgroundColor\": \"rgba(0,240,0,1.000)\",\n" +
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
                "            \"min\": 0\n" +
                "          },\n" +
                "          \"stacked\": false,\n" +
                "          \"scaleLabel\": {\n" +
                "            \"display\": true,\n" +
                "            \"labelString\": \"Step Number\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"yAxes\": [\n" +
                "        {\n" +
                "          \"ticks\": {\n" +
                "            \"min\": 0\n" +
                "          },\n" +
                "          \"stacked\": true,\n" +
                "          \"scaleLabel\": {\n" +
                "            \"display\": true,\n" +
                "            \"labelString\": \"Step Runtime\"\n" +
                "          }\n" +
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

        List<DataSet> dataSets = new ArrayList<>();

        DataSet dataSet1 = new DataSet();

        List<Integer> data1 = new ArrayList<>();
        data1.add(340);
        data1.add(0);
        data1.add(0);
        data1.add(440);
        data1.add(340);
        data1.add(0);
        dataSet1.setData(data1);
        dataSet1.setLabel("failed");
        dataSet1.setStack("complete");
        dataSet1.setBackgroundColor("rgba(240,0,0,1.000)");
        dataSets.add(dataSet1);

        DataSet dataSet2 = new DataSet();
        List<Integer> data2 = new ArrayList<>();
        data2.add(0);
        data2.add(340);
        data2.add(440);
        data2.add(0);
        data2.add(0);
        data2.add(340);
        dataSet2.setData(data2);
        dataSet2.setLabel("passed");
        dataSet2.setStack("complete");
        dataSet2.setBackgroundColor("rgba(0,240,0,1.000)");
        dataSets.add(dataSet2);

        data.setDataSets(dataSets);

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

        chart.setType("bar");

        assertThat(chart.getJson(), is(expected));
    }

}
