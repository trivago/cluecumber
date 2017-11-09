package com.trivago.rta.json.postprocessors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.exceptions.filesystem.FileCreationException;
import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Embedding;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.logging.CluecumberLogger;
import com.trivago.rta.properties.PropertyManager;
import io.gsonfire.PostProcessor;
import org.codehaus.plexus.util.Base64;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Singleton
public class ElementPostProcessor implements PostProcessor<Element> {

    private final PropertyManager propertyManager;
    private final FileIO fileIO;
    private CluecumberLogger logger;

    private int scenarioIndex = 0;
    private int attachmentIndex = 0;

    @Inject
    public ElementPostProcessor(
            final PropertyManager propertyManager,
            final FileIO fileIO,
            final CluecumberLogger logger
            ) {
        this.propertyManager = propertyManager;
        this.fileIO = fileIO;
        this.logger = logger;
    }

    @Override
    public void postDeserialize(final Element element, final JsonElement jsonElement, final Gson gson) {
        addScenarioIndex(element);
        try {
            processAttachments(element.getSteps());
        } catch (CluecumberPluginException e) {
            // If an attachment cannot be processed, don't stop the report generation.
            logger.warn(e.getMessage());
        }
    }

    private void processAttachments(final List<Step> steps) throws CluecumberPluginException {
        for (Step step : steps) {
            List<Embedding> embeddings = step.getEmbeddings();
            for (Embedding embedding : embeddings) {
                String filename = saveEmbeddingToFileAndGetFilename(embedding);
                embedding.setFilename(filename);
                attachmentIndex++;
            }
        }
    }

    private String saveEmbeddingToFileAndGetFilename(final Embedding embedding) throws CluecumberPluginException {
        String fileEnding;
        switch (embedding.getMimeType()) {
            case "image/png":
                fileEnding = ".png";
                break;
            case "image/jpg":
                fileEnding = ".jpg";
                break;
            default:
                fileEnding = ".unknown";
        }

        byte[] dataBytes = Base64.decodeBase64(embedding.getData().getBytes(StandardCharsets.UTF_8));
        embedding.setData("");
        String filename = String.format("attachment%03d%s", attachmentIndex, fileEnding);
        try {
            fileIO.writeContentToFile(dataBytes, propertyManager.getGeneratedHtmlReportDirectory() + "/attachments/" + filename);
        } catch (FileCreationException e) {
            throw new CluecumberPluginException("Could not process attachment of type " + embedding.getMimeType());
        }
        return filename;
    }

    private void addScenarioIndex(final Element element) {
        // Add index to elements (used for link creation to the detail reports).
        element.setScenarioIndex(scenarioIndex);
        scenarioIndex++;
    }
    
    @Override
    public void postSerialize(final JsonElement jsonElement, final Element element, final Gson gson) {
        // not used
    }
}
