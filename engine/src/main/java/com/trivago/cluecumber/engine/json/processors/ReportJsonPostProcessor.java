/*
 * Copyright 2023 trivago N.V.
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
package com.trivago.cluecumber.engine.json.processors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.pojo.Step;
import com.trivago.cluecumber.engine.json.pojo.Tag;
import io.gsonfire.PostProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Post processor for GUICE that adds feature indices, merges background scenarios into
 * scenarios and adds feature information to scenarios.
 */
@Singleton
public class ReportJsonPostProcessor implements PostProcessor<Report> {

    private final List<String> featureUris;


    /**
     * The default constructor.
     */
    @Inject
    public ReportJsonPostProcessor() {
        featureUris = new ArrayList<>();
    }

    /**
     * Adds feature indices, merges background scenarios into scenarios and adds feature information to scenarios.
     *
     * @param report      The {@link Report} instance.
     * @param jsonElement The {@link JsonElement} that is being deserialized, unused here.
     * @param gson        The {@link Gson} instance for JSON conversion.
     */
    @Override
    public void postDeserialize(final Report report, final JsonElement jsonElement, final Gson gson) {
        addFeatureIndex(report);
        addFeatureInformationToScenarios(report);
        mergeBackgroundScenarios(report);
    }

    private void addFeatureInformationToScenarios(final Report report) {
        List<Tag> reportTags = report.getTags();
        String featureName = report.getName();
        String featureUri = report.getUri();
        int featureIndex = report.getFeatureIndex();
        for (Element element : report.getElements()) {
            element.setFeatureUri(featureUri);
            element.setFeatureName(featureName);
            element.setFeatureIndex(featureIndex);
            if (!reportTags.isEmpty()) {
                List<Tag> mergedTags = Stream.concat(element.getTags().stream(), reportTags.stream())
                        .distinct()
                        .collect(Collectors.toList());
                element.setTags(mergedTags);
            }
        }
    }

    private void mergeBackgroundScenarios(final Report report) {
        List<Element> updatedElements = new ArrayList<>();
        List<Step> currentBackgroundSteps = null;
        for (Element element : report.getElements()) {
            if (element.getType().equalsIgnoreCase("background")) {
                currentBackgroundSteps = element.getSteps();
            } else {
                if (currentBackgroundSteps != null) {
                    element.setBackgroundSteps(currentBackgroundSteps);
                }
                updatedElements.add(element);
            }
        }
        report.setElements(updatedElements);
    }

    private void addFeatureIndex(final Report report) {
        if (report == null) return;

        String featureName = report.getName();
        if (!featureUris.contains(featureName)) {
            featureUris.add(featureName);
        }
        report.setFeatureIndex(featureUris.indexOf(featureName));
    }

    /**
     * Unused post serialize hook.
     *
     * @param jsonElement The {@link JsonElement} that was deserialized.
     * @param report      The {@link Report} instance.
     * @param gson        The {@link Gson} instance.
     */
    @Override
    public void postSerialize(final JsonElement jsonElement, final Report report, final Gson gson) {
        // not used
    }
}
