/*
 * Copyright 2017 trivago N.V.
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
import com.trivago.rta.json.pojo.Element;
import com.trivago.rta.json.pojo.Report;
import com.trivago.rta.json.postprocessors.ElementPostProcessor;
import io.gsonfire.GsonFireBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonPojoConverter {

    private final Gson gsonParser;

    @Inject
    public JsonPojoConverter(final ElementPostProcessor elementPostProcessor) {
        GsonFireBuilder builder = new GsonFireBuilder()
                .registerPostProcessor(Element.class, elementPostProcessor);
        gsonParser = builder.createGson();
    }

    public Report[] convertJsonToReportPojos(final String json) {
        return gsonParser.fromJson(json, Report[].class);
    }
}