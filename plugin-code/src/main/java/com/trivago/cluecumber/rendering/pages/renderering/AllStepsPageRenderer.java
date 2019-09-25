package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllStepsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllStepsPageRenderer extends PageRenderer {

    private final ChartConfiguration chartConfiguration;

    @Inject
    public AllStepsPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration
    ) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
    }

    public String getRenderedContent(
            final AllStepsPageCollection allStepsPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(allStepsPageCollection);
        return processedContent(template, allStepsPageCollection);
    }

    private void addChartJsonToReportDetails(final AllStepsPageCollection allTagsPageCollection) {

        List<Integer> passed = new ArrayList<>();
        List<Integer> failed = new ArrayList<>();
        List<Integer> skipped = new ArrayList<>();

        allTagsPageCollection.getStepResultCounts().forEach((key, value) -> {
            passed.add(value.getPassed());
            failed.add(value.getFailed());
            skipped.add(value.getSkipped());
        });

        List<String> keys = allTagsPageCollection.getStepResultCounts()
                .keySet()
                .stream()
                .map(Step::returnNameWithArgumentPlaceholders)
                .collect(Collectors.toList());

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setLabels(keys)
                        .setxAxisLabel(allTagsPageCollection.getTotalNumberOfSteps() + " Steps")
                        .setyAxisStepSize(keys.size())
                        .setyAxisLabel("Number of Usages")
                        .addValues(passed, Status.PASSED)
                        .addValues(failed, Status.FAILED)
                        .addValues(skipped, Status.SKIPPED)
                        .build();


        allTagsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }
}
