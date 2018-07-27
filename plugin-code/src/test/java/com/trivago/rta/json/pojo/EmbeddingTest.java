package com.trivago.rta.json.pojo;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EmbeddingTest {
    private Embedding embedding;

    @Before
    public void setup() {
        embedding = new Embedding();
    }

    @Test
    public void isImagePngTest() {
        embedding.setMimeType("image/png");
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageJpgTest() {
        embedding.setMimeType("image/jpeg");
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageGifTest() {
        embedding.setMimeType("image/gif");
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageSvgXmlTest() {
        embedding.setMimeType("image/svg+xml");
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageSvgTest() {
        embedding.setMimeType("image/svg");
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageInvalidTest() {
        embedding.setMimeType("no/image");
        assertThat(embedding.isImage(), is(false));
    }
}
