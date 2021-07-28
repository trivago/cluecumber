package com.trivago.cluecumber.json.pojo;

import com.trivago.cluecumber.constants.MimeType;
import org.codehaus.plexus.util.Base64;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmbeddingTest {
    private Embedding embedding;

    @Before
    public void setup() {
        embedding = new Embedding();
    }

    @Test
    public void isImagePngTest() {
        embedding.setMimeType(MimeType.PNG);
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageJpgTest() {
        embedding.setMimeType(MimeType.JPEG);
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageGifTest() {
        embedding.setMimeType(MimeType.GIF);
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageSvgXmlTest() {
        embedding.setMimeType(MimeType.SVG_XML);
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageSvgTest() {
        embedding.setMimeType(MimeType.SVG);
        assertThat(embedding.isImage(), is(true));
    }

    @Test
    public void isImageInvalidTest() {
        embedding.setMimeType(MimeType.UNKNOWN);
        assertThat(embedding.isImage(), is(false));
    }

    @Test
    public void isPlainTextTest() {
        embedding.setMimeType(MimeType.TXT);
        assertThat(embedding.isPlainText(), is(true));
    }

    @Test
    public void getDecodedDataTest() {
        String originalInput = "This is getDecodeData() Test !!!";
        String encodeString = new String(Base64.encodeBase64(originalInput.getBytes()));
        embedding.setMimeType(MimeType.TXT);
        embedding.decodeData(encodeString);
        assertThat(embedding.getDecodedData(), is("This is getDecodeData() Test !!!"));
    }

    @Test
    public void getDecodedDataHtmlTest() {
        String originalInput = "<pre>&lt;h1&gt;should be escapedd&lt;/h1&gt;</pre>";
        String encodeString = new String(Base64.encodeBase64(originalInput.getBytes()));
        embedding.setMimeType(MimeType.HTML);
        embedding.decodeData(encodeString);
        assertThat(embedding.getDecodedData(), is("<pre>&amp;lt;h1&amp;gt;should be escapedd&amp;lt;/h1&amp;gt;</pre>"));
    }

    @Test
    public void getDecodedDataForXMLTest() {
        String originalInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        String encodeString = new String(Base64.encodeBase64(originalInput.getBytes()));
        embedding.setMimeType(MimeType.XML);
        embedding.decodeData(encodeString);
        assertThat(embedding.getDecodedData(), is("&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;note&gt;&lt;to&gt;Tove&lt;/to&gt;&lt;from&gt;Jani&lt;/from&gt;&lt;heading&gt;Reminder&lt;/heading&gt;&lt;body&gt;Don't forget me this weekend!&lt;/body&gt;&lt;/note&gt;"));
    }

    @Test
    public void getDecodedDataForHTMLTest() {
        String originalInput = "<a href=\"test\">test</a>";
        String encodeString = new String(Base64.encodeBase64(originalInput.getBytes()));
        embedding.setMimeType(MimeType.HTML);
        embedding.decodeData(encodeString);
        assertThat(embedding.getDecodedData(), is("<a href='test'>test</a>"));
    }

    @Test
    public void getFileEndingTest() {
        embedding.setMimeType(MimeType.PNG);
        assertThat(embedding.getFileEnding(), is("png"));
        embedding.setMimeType(MimeType.GIF);
        assertThat(embedding.getFileEnding(), is("gif"));
        embedding.setMimeType(MimeType.BMP);
        assertThat(embedding.getFileEnding(), is("bmp"));
        embedding.setMimeType(MimeType.JPEG);
        assertThat(embedding.getFileEnding(), is("jpeg"));
        embedding.setMimeType(MimeType.HTML);
        assertThat(embedding.getFileEnding(), is("html"));
        embedding.setMimeType(MimeType.XML);
        assertThat(embedding.getFileEnding(), is("xml"));
        embedding.setMimeType(MimeType.JSON);
        assertThat(embedding.getFileEnding(), is("json"));
        embedding.setMimeType(MimeType.APPLICATION_XML);
        assertThat(embedding.getFileEnding(), is("xml"));
        embedding.setMimeType(MimeType.SVG);
        assertThat(embedding.getFileEnding(), is("svg"));
        embedding.setMimeType(MimeType.SVG_XML);
        assertThat(embedding.getFileEnding(), is("svg"));
        embedding.setMimeType(MimeType.TXT);
        assertThat(embedding.getFileEnding(), is("txt"));
        embedding.setMimeType(MimeType.PDF);
        assertThat(embedding.getFileEnding(), is("pdf"));
        embedding.setMimeType(MimeType.UNKNOWN);
        assertThat(embedding.getFileEnding(), is("unknown"));
    }
}
