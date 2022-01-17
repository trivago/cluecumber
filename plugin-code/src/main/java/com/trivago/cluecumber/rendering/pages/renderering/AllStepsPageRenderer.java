package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.constants.ChartConfiguration;
import com.trivago.cluecumber.constants.PluginSettings;
import com.trivago.cluecumber.constants.Status;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.AllStepsPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllStepsPageRenderer extends PageWithChartRenderer {

    private final ChartConfiguration chartConfiguration;
    private final PropertyManager propertyManager;

    @Inject
    public AllStepsPageRenderer(
            final ChartJsonConverter chartJsonConverter,
            final ChartConfiguration chartConfiguration,
            final PropertyManager propertyManager
    ) {
        super(chartJsonConverter);
        this.chartConfiguration = chartConfiguration;
        this.propertyManager = propertyManager;
    }

    public String getRenderedContent(
            final AllStepsPageCollection allStepsPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(allStepsPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == PluginSettings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(allStepsPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, allStepsPageCollection);
    }

    private void addChartJsonToReportDetails(final AllStepsPageCollection allTagsPageCollection) {

        List<Integer> passed = new ArrayList<>();
        List<Integer> failed = new ArrayList<>();
        List<Integer> skipped = new ArrayList<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<Step, ResultCount> entry : allTagsPageCollection.getStepResultCounts().entrySet()) {
            ResultCount value = entry.getValue();
            passed.add(value.getPassed());
            failed.add(value.getFailed());
            skipped.add(value.getSkipped());
            if (value.getTotal() > maximumNumberOfRuns) {
                maximumNumberOfRuns = value.getTotal();
            }
        }

        List<String> keys = allTagsPageCollection.getStepResultCounts()
                .keySet()
                .stream()
                .map(Step::returnNameWithArgumentPlaceholders)
                .collect(Collectors.toList());

        Chart chart =
                new StackedBarChartBuilder(chartConfiguration)
                        .setLabels(keys)
                        .setxAxisLabel(allTagsPageCollection.getTotalNumberOfSteps() + " Steps")
                        .setyAxisStepSize(maximumNumberOfRuns)
                        .setyAxisLabel("Number of Usages")
                        .addValues(passed, Status.PASSED)
                        .addValues(failed, Status.FAILED)
                        .addValues(skipped, Status.SKIPPED)
                        .build();


        allTagsPageCollection.getReportDetails().setChartJson(convertChartToJson(chart));
    }
}
