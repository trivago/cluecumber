package com.trivago.rta.rendering.pages;

import be.ceau.chart.PieChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.PieData;
import be.ceau.chart.dataset.PieDataset;
import be.ceau.chart.options.PieOptions;
import com.trivago.rta.constants.ScenarioStatus;
import com.trivago.rta.exceptions.TrupiReportingPluginException;
import com.trivago.rta.rendering.pages.pojos.ReportDetails;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

@Singleton
public class StartPageRenderer extends PageRenderer {

    public String getRenderedContent(
            final StartPageCollection startPageCollection, final Template template)
            throws TrupiReportingPluginException {

        ReportDetails reportDetails = new ReportDetails();
        addChartJsonToReportDetails(startPageCollection, reportDetails);
        addCurrentDateToReportDetails(reportDetails);
        startPageCollection.setReportDetails(reportDetails);

        Writer stringWriter = new StringWriter();
        try {
            template.process(startPageCollection, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new TrupiReportingPluginException(e.getMessage());
        }
        return stringWriter.toString();
    }

    private void addChartJsonToReportDetails(final StartPageCollection startPageCollection, final ReportDetails reportDetails) {
        PieDataset pieDataset = new PieDataset();
        pieDataset.setData(
                startPageCollection.getTotalNumberOfPassedScenarios(),
                startPageCollection.getTotalNumberOfFailedScenarios(),
                startPageCollection.getTotalNumberOfSkippedScenarios()
        );

        final int failedR = 220;
        final int failedG = 53;
        final int failedB = 69;

        final int skippedR = 255;
        final int skippedG = 193;
        final int skippedB = 7;

        final int passedR = 40;
        final int passedG = 167;
        final int passedB = 69;

        Color failedColor = new Color(failedR, failedG, failedB);
        Color skippedColor = new Color(skippedR, skippedG, skippedB);
        Color passedColor = new Color(passedR, passedG, passedB);

        pieDataset.addBackgroundColors(passedColor, failedColor, skippedColor);
        PieData pieData = new PieData();
        pieData.addDataset(pieDataset);
        pieData.addLabels(ScenarioStatus.PASSED.getStatus(), ScenarioStatus.FAILED.getStatus(), ScenarioStatus.SKIPPED.getStatus());
        PieOptions pieOptions = new PieOptions();

        reportDetails.setChartJson(new PieChart(pieData, pieOptions).toJson());
    }
}
