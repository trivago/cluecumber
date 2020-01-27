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

package com.trivago.cluecumber.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.trivago.cluecumber.constants.MimeType;
import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.json.pojo.Element;
import com.trivago.cluecumber.json.pojo.Report;
import com.trivago.cluecumber.json.processors.ElementJsonPostProcessor;
import com.trivago.cluecumber.json.processors.ReportJsonPostProcessor;
import io.gsonfire.GsonFireBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class JsonPojoConverter {

    private final Gson gsonParserWithProcessors;

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

    public List<Report> readJsonStream(InputStream in) throws CluecumberPluginException, IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<Report> reports = new ArrayList<Report>();
        reader.beginArray();
        while (reader.hasNext()) {
            Report report = gsonParserWithProcessors.fromJson(reader, Report.class);
            reports.add(report);
        }
        reader.endArray();
        reader.close();
        return reports;
    }

    public Report[] convertJsonToReportPojos(final String json) throws CluecumberPluginException {
        Report[] reports;
        try {
            reports = gsonParserWithProcessors.fromJson(json, Report[].class);
        } catch (JsonParseException e) {
            throw new CluecumberPluginException(e.getMessage());
        }
        return reports;
    }
}
