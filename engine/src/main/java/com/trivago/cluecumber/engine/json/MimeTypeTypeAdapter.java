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

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.trivago.cluecumber.engine.constants.MimeType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps Cucumber attachment mime_type strings to {@link MimeType}, defaulting to {@link MimeType#UNKNOWN}.
 */
public class MimeTypeTypeAdapter extends TypeAdapter<MimeType> {

    private static final Map<String, MimeType> BY_SERIALIZED_NAME = buildLookup();

    private static Map<String, MimeType> buildLookup() {
        Map<String, MimeType> lookup = new HashMap<>();
        for (MimeType mimeType : MimeType.values()) {
            try {
                SerializedName annotation = MimeType.class.getField(mimeType.name()).getAnnotation(SerializedName.class);
                if (annotation != null) {
                    lookup.put(annotation.value(), mimeType);
                }
            } catch (NoSuchFieldException e) {
                throw new ExceptionInInitializerError(e);
            }
            lookup.put(mimeType.getContentType(), mimeType);
        }
        return Map.copyOf(lookup);
    }

    @Override
    public void write(final JsonWriter out, final MimeType value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.getContentType());
    }

    @Override
    public MimeType read(final JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return MimeType.UNKNOWN;
        }
        return BY_SERIALIZED_NAME.getOrDefault(in.nextString(), MimeType.UNKNOWN);
    }
}
