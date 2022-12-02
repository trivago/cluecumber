package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.constants.ChartConfiguration;
import com.trivago.cluecumber.engine.constants.PluginSettings;
import com.trivago.cluecumber.engine.constants.Status;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;
import com.trivago.cluecumber.engine.rendering.pages.charts.StackedBarChartBuilder;
import com.trivago.cluecumber.engine.rendering.pages.charts.pojos.Chart;
import com.trivago.cluecumber.engine.rendering.pages.pojos.ResultCount;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllStepsPageCollection;
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
            throws CluecumberException {

        addChartJsonToReportDetails(allStepsPageCollection);

        if (propertyManager.getCustomParametersDisplayMode() == PluginSettings.CustomParamDisplayMode.ALL_PAGES) {
            addCustomParametersToReportDetails(allStepsPageCollection, propertyManager.getCustomParameters());
        }

        return processedContent(template, allStepsPageCollection, propertyManager.getNavigationLinks());
    }

    private void addChartJsonToReportDetails(final AllStepsPageCollection allTagsPageCollection) {

        List<Float> passed = new ArrayList<>();
        List<Float> failed = new ArrayList<>();
        List<Float> skipped = new ArrayList<>();

        int maximumNumberOfRuns = 0;
        for (Map.Entry<Step, ResultCount> entry : allTagsPageCollection.getStepResultCounts().entrySet()) {
            ResultCount value = entry.getValue();
            passed.add((float) value.getPassed());
            failed.add((float) value.getFailed());
            skipped.add((float) value.getSkipped());
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
