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

package com.trivago.rta.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.postprocessors.ElementPostProcessor;
import com.trivago.rta.json.postprocessors.ReportPostProcessor;
import io.gsonfire.GsonFireBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonPojoConverter {

    private final Gson gsonParser;
    private final Gson gsonParserWithProcessors;

    @Inject
    public JsonPojoConverter(final ReportPostProcessor reportPostProcessor, final ElementPostProcessor elementPostProcessor) {
        GsonFireBuilder builder = new GsonFireBuilder()
                .registerPostProcessor(Report.class, reportPostProcessor)
                .registerPostProcessor(Element.class, elementPostProcessor);
        gsonParserWithProcessors = builder.createGson();
        gsonParser = new Gson();
    }

    public Report[] convertJsonToReportPojos(final String json) throws CluecumberPluginException {
        Report[] reports = null;
        try {
            reports = gsonParserWithProcessors.fromJson(json, Report[].class);
        } catch (JsonParseException e) {
            throw new CluecumberPluginException(e.getMessage());
        }
        return reports;
    }
}