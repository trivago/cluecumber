package com.trivago.rta.json.pojo;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EmbeddingTest {
    Embedding embedding;

    @Before
    public void setup(){
        embedding = new Embedding();
    }

    @Test
    public void getEncodedDataTest(){
        embedding.setData("A<B>C");
        assertThat(embedding.getEncodedData(), is("A&#60;B&#62;C"));
    }
}
