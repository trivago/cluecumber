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
package com.trivago.cluecumber.engine.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.trivago.cluecumber.engine.constants.MimeType;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.json.pojo.Report;
import com.trivago.cluecumber.engine.json.processors.ElementJsonPostProcessor;
import com.trivago.cluecumber.engine.json.processors.ReportJsonPostProcessor;
import io.gsonfire.GsonFireBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The converter to turn JSON into a report array including pre- and post-processing.
 */
@Singleton
public class JsonPojoConverter {

    private final Gson gsonParserWithProcessors;

    /**
     * The constructor for dependency injection.
     *
     * @param reportJsonPostProcessor  The {@link ReportJsonPostProcessor} instance.
     * @param elementJsonPostProcessor The {@link ElementJsonPostProcessor} instance.
     */
    @Inject
    public JsonPojoConverter(
            final ReportJsonPostProcessor reportJsonPostProcessor,
            final ElementJsonPostProcessor elementJsonPostProcessor
    ) {
        GsonFireBuilder builder = new GsonFireBuilder()
                .registerPostProcessor(Report.class, reportJsonPostProcessor)
                .registerPostProcessor(Element.class, elementJsonPostProcessor)
                .enumDefaultValue(MimeType.class, MimeType.UNKNOWN);
        gsonParserWithProcessors = builder.createGson();
    }

    /**
     * Turn Cucumber JSON into a report array.
     *
     * @param json The JSON data.
     * @return The {@link Report} array.
     * @throws CluecumberException Thrown on all errors.
     */
    public Report[] convertJsonToReportPojos(final String json) throws CluecumberException {
        Report[] reports;
        try {
            reports = gsonParserWithProcessors.fromJson(json, Report[].class);
        } catch (JsonParseException e) {
            throw new CluecumberException(e.getMessage());
        }
        return reports;
    }
}
