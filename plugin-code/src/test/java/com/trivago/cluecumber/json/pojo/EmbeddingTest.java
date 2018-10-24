package com.trivago.cluecumber.json.pojo;

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

    @Test
    public void isPlainTextTest() {
        embedding.setMimeType("text/plain");
        assertThat(embedding.isPlainText(), is(true));
    }
    
    @Test
    public void getDecodedDataTest() {
		String originalInput = "This is getDecodeData() Test !!!";
		String encodeString = new String(Base64.encodeBase64(originalInput.getBytes()));
		embedding.setData(encodeString);
		assertThat(embedding.getDecodedData(),is("This is getDecodeData() Test !!!"));
    }
    
    @Test
    public void getDecodedDataForXMLTest() {
		String originalInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
		String encodeString = new String(Base64.encodeBase64(originalInput.getBytes()));
		embedding.setData(encodeString);
        embedding.setMimeType("text/xml");
		assertThat(embedding.getDecodedData(),is("&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;note&gt;&lt;to&gt;Tove&lt;/to&gt;&lt;from&gt;Jani&lt;/from&gt;&lt;heading&gt;Reminder&lt;/heading&gt;&lt;body&gt;Don't forget me this weekend!&lt;/body&gt;&lt;/note&gt;"));        
    }
    
    @Test
    public void getFileEndingTest() {
        embedding.setMimeType("image/png");
        assertThat(embedding.getFileEnding(), is("png"));
        embedding.setMimeType("image/gif");
        assertThat(embedding.getFileEnding(), is("gif"));
        embedding.setMimeType("image/bmp");
        assertThat(embedding.getFileEnding(), is("bmp"));
        embedding.setMimeType("image/jpeg");
        assertThat(embedding.getFileEnding(), is("jpeg"));
        embedding.setMimeType("text/html");
        assertThat(embedding.getFileEnding(), is("html"));
        embedding.setMimeType("text/xml");
        assertThat(embedding.getFileEnding(), is("xml"));
        embedding.setMimeType("application/json");
        assertThat(embedding.getFileEnding(), is("json"));
        embedding.setMimeType("application/xml");
        assertThat(embedding.getFileEnding(), is("xml"));
        embedding.setMimeType("image/svg");
        assertThat(embedding.getFileEnding(), is("svg"));
        embedding.setMimeType("image/svg+xml");
        assertThat(embedding.getFileEnding(), is("svg"));
        embedding.setMimeType("text/plain");        
        assertThat(embedding.getFileEnding(), is("txt"));
        embedding.setMimeType("application/pdf");
        assertThat(embedding.getFileEnding(), is("pdf"));
        embedding.setMimeType("unknown/unknown");
        assertThat(embedding.getFileEnding(), is("unknown"));
    }        
}
