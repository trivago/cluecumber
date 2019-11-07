/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumberCore.json.processors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trivago.cluecumberCore.exceptions.filesystem.FileCreationException;
import com.trivago.cluecumberCore.filesystem.FileIO;
import com.trivago.cluecumberCore.json.pojo.Element;
import com.trivago.cluecumberCore.json.pojo.Embedding;
import com.trivago.cluecumberCore.json.pojo.ResultMatch;
import com.trivago.cluecumberCore.json.pojo.Step;
import com.trivago.cluecumberCore.logging.CluecumberLogger;
import com.trivago.cluecumberCore.properties.PropertyManager;
import io.gsonfire.PostProcessor;
import org.codehaus.plexus.util.Base64;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Singleton
public class ElementJsonPostProcessor implements PostProcessor<Element> {

    private final PropertyManager propertyManager;
    private final FileIO fileIO;
    private final CluecumberLogger logger;

    private int attachmentIndex = 1;

    @Inject
    public ElementJsonPostProcessor(
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
        element.setFailOnPendingOrUndefined(propertyManager.isFailScenariosOnPendingOrUndefinedSteps());
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
        steps.forEach(step -> {
            for (ResultMatch before : step.getBefore()) {
                processEmbedding(before.getEmbeddings());
            }
            processEmbedding(step.getEmbeddings());
            step.getAfter().stream().map(ResultMatch::getEmbeddings).forEach(this::processEmbedding);
        });

        // Process after hook attachments (Cucumber 2)
        afterHooks.stream().map(ResultMatch::getEmbeddings).forEach(this::processEmbedding);
    }

   /**
     * Save embeddings to files, clear their Base64 content from JSON and store their filenames in order to save memory
     *
     * @param embeddings The {@link Embedding} list.
     */
    private void processEmbedding(final List<Embedding> embeddings) {
        embeddings.forEach(embedding -> {
            String filename = saveEmbeddingToFileAndGetFilename(embedding);
            embedding.setFilename(filename);
            attachmentIndex++;
        });
    }

    /**
     * Saves attachments to a file and returns the filename.
     *
     * @param embedding The {@link Embedding} to process.
     * @return The filename to the processed image.
     */
    private String saveEmbeddingToFileAndGetFilename(final Embedding embedding) {        
        String fileEnding = "." + embedding.getFileEnding();
        byte[] dataBytes = Base64.decodeBase64(embedding.getData().getBytes(StandardCharsets.UTF_8));
        String filename = String.format("attachment%03d%s", attachmentIndex, fileEnding);
        try {
            fileIO.writeContentToFile(dataBytes, propertyManager.getGeneratedHtmlReportDirectory() + "/attachments/" + filename);
        } catch (FileCreationException e) {
            logger.warn("Could not process image " + filename + " but will continue report generation...");
        }
        embedding.encodeData(embedding.getData());
        // Clear attachment data to reduce memory
        embedding.setData("");
        return filename;
    }

    @Override
    public void postSerialize(final JsonElement jsonElement, final Element element, final Gson gson) {
        // not used
    }
}
