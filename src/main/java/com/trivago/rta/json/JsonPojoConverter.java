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