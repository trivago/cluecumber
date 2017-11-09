import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.json.JsonPojoConverter;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import com.trivago.rta.rendering.ReportGenerator;
import com.trivago.rta.rendering.pages.pojos.StartPageCollection;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;

/**
 * The main plugin class.
 */
@Mojo(name = "reporting")
public final class CluecumberReportPlugin extends AbstractMojo {

    private final CluecumberLogger logger;
    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;
    private final FileIO fileIO;
    private final JsonPojoConverter jsonPojoConverter;
    private final ReportGenerator reportGenerator;

    /**
     * The path to the Cucumber JSON files.
     */
    @Parameter(property = "reporting.sourceJsonReportDirectory", required = true)
    private String sourceJsonReportDirectory = "";

    /**
     * The location of the generated report.
     */
    @Parameter(property = "reporting.generatedHtmlReportDirectory", required = true)
    private String generatedHtmlReportDirectory = "";

    @Inject
    public CluecumberReportPlugin(
            final CluecumberLogger logger,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final FileIO fileIO,
            final JsonPojoConverter jsonPojoConverter,
            final ReportGenerator reportGenerator
    ) {
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
        this.fileIO = fileIO;
        this.jsonPojoConverter = jsonPojoConverter;
        this.logger = logger;
        this.reportGenerator = reportGenerator;
    }

    /**
     * Cluecumber Report start method.
     *
     * @throws CluecumberPluginException When thrown, the plugin execution is stopped.
     */
    public void execute() throws CluecumberPluginException {
        // Initialize logger to be available outside the AbstractMojo class
        logger.setMojoLogger(getLog());

        // Initialize and validate passed pom properties
        propertyManager.setSourceJsonReportDirectory(sourceJsonReportDirectory);
        propertyManager.setGeneratedHtmlReportDirectory(generatedHtmlReportDirectory);
        propertyManager.validateSettings();

        logger.info("--------------------------------");
        logger.info(" Cluecumber Report Maven Plugin ");
        logger.info("--------------------------------");

        propertyManager.logProperties();

        // Create attachments directory for json attachment saving
        fileSystemManager.createDirectory(propertyManager.getGeneratedHtmlReportDirectory() + "/attachments");

        StartPageCollection startPageCollection = new StartPageCollection();
        List<Path> jsonFilePaths = fileSystemManager.getJsonFilePaths();
        for (Path jsonFilePath : jsonFilePaths) {
            String jsonString = fileIO.readContentFromFile(jsonFilePath.toString());
            Report[] reports = jsonPojoConverter.convertJsonToReportPojos(jsonString);
            startPageCollection.addReports(reports);
        }
        reportGenerator.generateReports(startPageCollection);
        logger.info("Converted " + startPageCollection.getTotalNumberOfFeatures() + " features into test report.");
    }
}



