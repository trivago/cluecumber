package com.trivago.cluecumber.rendering.pages.renderers;

import com.trivago.cluecumber.constants.Charts;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.rendering.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.charts.pojos.Axis;
import com.trivago.cluecumber.rendering.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.charts.pojos.Data;
import com.trivago.cluecumber.rendering.charts.pojos.Dataset;
import com.trivago.cluecumber.rendering.charts.pojos.Options;
import com.trivago.cluecumber.rendering.charts.pojos.ScaleLabel;
import com.trivago.cluecumber.rendering.charts.pojos.Scales;
import com.trivago.cluecumber.rendering.charts.pojos.Ticks;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllStepsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AllStepsPageRenderer extends PageRenderer {

    @Inject
    public AllStepsPageRenderer(final ChartJsonConverter chartJsonConverter) {
        super(chartJsonConverter);
    }

    public String getRenderedContent(
            final AllStepsPageCollection allStepsPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(allStepsPageCollection);
        return processedContent(template, allStepsPageCollection);
    }

    private void addChartJsonToReportDetails(final AllStepsPageCollection allTagsPageCollection) {

        Chart chart = new Chart();
        Data data = new Data();
        chart.setData(data);

        List<Dataset> datasets = new ArrayList<>();

        List<Integer> passed = new ArrayList<>();
        List<Integer> failed = new ArrayList<>();
        List<Integer> skipped = new ArrayList<>();

        int maxY = 0;
        for (Map.Entry<Step, ResultCount> entry : allTagsPageCollection.getStepResultCounts().entrySet()) {
            passed.add(entry.getValue().getPassed());
            failed.add(entry.getValue().getFailed());
            skipped.add(entry.getValue().getSkipped());
            maxY = entry.getValue().getTotal();
        }

        Dataset passedDataset = new Dataset();
        passedDataset.setLabel("passed");
        passedDataset.setData(passed);
        List<String> passedBG = new ArrayList<>(Collections.nCopies(passed.size(), Charts.Color.getChartColorStringByStatus(Status.PASSED)));
        passedDataset.setBackgroundColor(passedBG);
        datasets.add(passedDataset);

        Dataset failedDataset = new Dataset();
        failedDataset.setLabel("failed");
        failedDataset.setData(failed);
        List<String> failedBG = new ArrayList<>(Collections.nCopies(passed.size(), Charts.Color.getChartColorStringByStatus(Status.FAILED)));
        failedDataset.setBackgroundColor(failedBG);
        datasets.add(failedDataset);

        Dataset skippedDataset = new Dataset();
        skippedDataset.setLabel("skipped");
        skippedDataset.setData(skipped);
        List<String> skippedBG = new ArrayList<>(Collections.nCopies(passed.size(), Charts.Color.getChartColorStringByStatus(Status.SKIPPED)));
        skippedDataset.setBackgroundColor(skippedBG);
        datasets.add(skippedDataset);

        data.setDatasets(datasets);

        List<String> keys = new ArrayList<>();
        for (Step step : allTagsPageCollection.getStepResultCounts().keySet()) {
            keys.add(step.returnNameWithArgumentPlaceholders());
        }
        data.setLabels(keys);

        Options options = new Options();
        Scales scales = new Scales();
        List<Axis> xAxes = new ArrayList<>();
        Axis xAxis = new Axis();
        xAxis.setStacked(true);
        Ticks xTicks = new Ticks();
        xTicks.setDisplay(false);
        xAxis.setTicks(xTicks);
        ScaleLabel xScaleLabel = new ScaleLabel();
        xScaleLabel.setDisplay(true);
        xScaleLabel.setLabelString(allTagsPageCollection.getTotalNumberOfSteps() + " Step(s)");
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
        yScaleLabel.setLabelString("Number of Scenarios");
        yAxis.setScaleLabel(yScaleLabel);
        yAxis.setStepSize(maxY);
        yAxes.add(yAxis);
        scales.setyAxes(yAxes);

        options.setScales(scales);
        chart.setOptions(options);

        chart.setType(Charts.Type.bar);

        allTagsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }
}
