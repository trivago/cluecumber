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

package com.trivago.cluecumber.json.postprocessors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trivago.cluecumber.exceptions.filesystem.FileCreationException;
import com.trivago.cluecumber.filesystem.FileIO;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Embedding;
import com.trivago.cluecumber.json.pojo.ResultMatch;
import com.trivago.cluecumber.json.pojo.Step;
import com.trivago.cluecumber.logging.CluecumberLogger;
import com.trivago.cluecumber.properties.PropertyManager;
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

    private int scenarioIndex = 1;
    private int attachmentIndex = 1;

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
        processAttachments(element.getSteps(), element.getAfter());
    }

    /**
     * Process attachments in steps and after hooks.
     *
     * @param steps      The {@link Step} list.
     * @param afterHooks The {@link ResultMatch} list.
     */
    private void processAttachments(final List<Step> steps, List<ResultMatch> afterHooks) {
        // Process step attachments
        for (Step step : steps) {
            processEmbedding(step.getEmbeddings());
        }

        // Process after hook attachments (Cucumber 2)
        for (ResultMatch afterHook : afterHooks) {
            processEmbedding(afterHook.getEmbeddings());
        }
    }

    /**
     * Save images to files, clear their Base64 content from JSON and store their filenames in order to save memory
     *
     * @param embeddings The {@link Embedding} list.
     */
    private void processEmbedding(final List<Embedding> embeddings) {
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
     * Add index to scenarios (used for link creation to the detail reports).
     *
     * @param element The current {@link Element}.
     */
    private void addScenarioIndex(final Element element) {
        // Filter out background scenarios
        if (!element.isScenario()) {
            return;
        }
        element.setScenarioIndex(scenarioIndex);
        scenarioIndex++;
    }

    @Override
    public void postSerialize(final JsonElement jsonElement, final Element element, final Gson gson) {
        // not used
    }
}
