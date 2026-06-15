package com.trivago.cluecumber.engine.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivago.cluecumber.engine.constants.MimeType;
import com.trivago.cluecumber.engine.json.pojo.Embedding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MimeTypeTypeAdapterTest {

    private Gson gson;

    @BeforeEach
    void setup() {
        gson = new GsonBuilder()
                .registerTypeAdapter(MimeType.class, new MimeTypeTypeAdapter())
                .create();
    }

    @Test
    void knownMimeTypeTest() {
        Embedding embedding = gson.fromJson("{\"mime_type\":\"image/png\"}", Embedding.class);
        assertEquals(MimeType.PNG, embedding.getMimeType());
    }

    @Test
    void unknownMimeTypeTest() {
        Embedding embedding = gson.fromJson("{\"mime_type\":\"application/octet-stream\"}", Embedding.class);
        assertEquals(MimeType.UNKNOWN, embedding.getMimeType());
    }

    @Test
    void nullMimeTypeTest() {
        Embedding embedding = gson.fromJson("{\"mime_type\":null}", Embedding.class);
        assertEquals(MimeType.UNKNOWN, embedding.getMimeType());
    }
}
