/*
 * Copyright 2018 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.rta.json.postprocessors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.exceptions.filesystem.FileCreationException;
import com.trivago.rta.filesystem.FileIO;
import com.trivago.rta.json.pojo.After;
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
    private final CluecumberLogger logger;

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
            processAttachments(element.getSteps(), element.getAfter());
        } catch (CluecumberPluginException e) {
            // If an attachment cannot be processed, don't stop the report generation.
            logger.error(e.getMessage());
        }
    }

    /**
     * Process attachments in steps and after hooks.
     *
     * @param steps      The {@link Step} list.
     * @param afterHooks The {@link After} list.
     * @throws CluecumberPluginException Exception if the attachments cannot be processed.
     */
    private void processAttachments(final List<Step> steps, List<After> afterHooks) throws CluecumberPluginException {
        // Process step attachments
        for (Step step : steps) {
            processEmbedding(step.getEmbeddings());
        }

        // Process after hook attachments (Cucumber 2)
        for (After afterHook : afterHooks) {
            processEmbedding(afterHook.getEmbeddings());
        }
    }

    /**
     * Save images to files, clear their Base64 content from JSON and store their filenames in order to save memory
     *
     * @param embeddings The {@link Embedding} list.
     * @throws CluecumberPluginException The exception if the attachment cannot be processed.
     */
    private void processEmbedding(final List<Embedding> embeddings) throws CluecumberPluginException {
        for (Embedding embedding : embeddings) {
            if (embedding.isImage()) {
                String filename = saveImageEmbeddingToFileAndGetFilename(embedding);
                embedding.setFilename(filename);
            }
            attachmentIndex++;
        }
    }

    /**
     * Saves image attachments to a file and returns the filename.
     *
     * @param embedding The {@link Embedding} to process.
     * @return The filename to the processed image.
     */
    private String saveImageEmbeddingToFileAndGetFilename(final Embedding embedding) {
        if (!embedding.isImage()) {
            return "";
        }

        String fileEnding;
        switch (embedding.getMimeType()) {
            case "image/png":
                fileEnding = ".png";
                break;
            case "image/jpeg":
                fileEnding = ".jpg";
                break;
            case "image/gif":
                fileEnding = ".gif";
                break;
            case "image/svg+xml":
            case "image/svg":
                fileEnding = ".svg";
                break;
            default:
                fileEnding = ".unknown";
        }

        byte[] dataBytes = Base64.decodeBase64(embedding.getData().getBytes(StandardCharsets.UTF_8));

        // Clear attachment data to reduce memory
        embedding.setData("");

        String filename = String.format("attachment%03d%s", attachmentIndex, fileEnding);
        try {
            fileIO.writeContentToFile(dataBytes, propertyManager.getGeneratedHtmlReportDirectory() + "/attachments/" + filename);
        } catch (FileCreationException e) {
            logger.error("Could not process image " + filename + " but will continue report generation...");
        }
        return filename;
    }

    /**
     * Add index to elements (used for link creation to the detail reports).
     *
     * @param element The current {@link Element}.
     */
    private void addScenarioIndex(final Element element) {
        element.setScenarioIndex(scenarioIndex);
        scenarioIndex++;
    }

    @Override
    public void postSerialize(final JsonElement jsonElement, final Element element, final Gson gson) {
        // not used
    }
}
