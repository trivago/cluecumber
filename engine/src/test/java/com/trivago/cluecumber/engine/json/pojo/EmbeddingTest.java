package com.trivago.cluecumber.engine.json.pojo;

import com.trivago.cluecumber.engine.constants.MimeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmbeddingTest {
    private Embedding embedding;

    @BeforeEach
    public void setup() {
        embedding = new Embedding();
    }

    @Test
    public void isImagePngTest() {
        embedding.setMimeType(MimeType.PNG);
        assertTrue(embedding.isImage());
    }

    @Test
    public void isImageJpgTest() {
        embedding.setMimeType(MimeType.JPEG);
        assertTrue(embedding.isImage());
    }

    @Test
    public void isImageGifTest() {
        embedding.setMimeType(MimeType.GIF);
        assertTrue(embedding.isImage());
    }

    @Test
    public void isImageSvgXmlTest() {
        embedding.setMimeType(MimeType.SVG_XML);
        assertTrue(embedding.isImage());
    }

    @Test
    public void isImageSvgTest() {
        embedding.setMimeType(MimeType.SVG);
        assertTrue(embedding.isImage());
    }

    @Test
    public void isImageInvalidTest() {
        embedding.setMimeType(MimeType.UNKNOWN);
        assertFalse(embedding.isImage());
    }

    @Test
    public void isPlainTextTest() {
        embedding.setMimeType(MimeType.TXT);
        assertTrue(embedding.isPlainText());
    }

    @Test
    public void getDecodedDataTest() {
        String originalInput = "This is getDecodeData() Test !!!";
        String encodeString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        embedding.setMimeType(MimeType.TXT);
        embedding.decodeData(encodeString);
        assertEquals(embedding.getDecodedData(), "This is getDecodeData() Test !!!");
    }

    @Test
    public void getDecodedDataHtmlTest() {
        String originalInput = "<pre>&lt;h1&gt;should be escapedd&lt;/h1&gt;</pre>";
        String encodeString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        embedding.setMimeType(MimeType.HTML);
        embedding.decodeData(encodeString);
        assertEquals(embedding.getDecodedData(), "<pre>&amp;lt;h1&amp;gt;should be escapedd&amp;lt;/h1&amp;gt;</pre>");
    }

    @Test
    public void getDecodedDataForXMLTest() {
        String originalInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        String encodeString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        embedding.setMimeType(MimeType.XML);
        embedding.decodeData(encodeString);
        assertEquals(embedding.getDecodedData(), "&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;note&gt;&lt;to&gt;Tove&lt;/to&gt;&lt;from&gt;Jani&lt;/from&gt;&lt;heading&gt;Reminder&lt;/heading&gt;&lt;body&gt;Don't forget me this weekend!&lt;/body&gt;&lt;/note&gt;");
    }

    @Test
    public void getDecodedDataForHTMLTest() {
        String originalInput = "<a href=\"test\">test</a>";
        String encodeString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        embedding.setMimeType(MimeType.HTML);
        embedding.decodeData(encodeString);
        assertEquals(embedding.getDecodedData(), "<a href='test'>test</a>");
    }

    @Test
    public void getFileEndingTest() {
        embedding.setMimeType(MimeType.PNG);
        assertEquals(embedding.getFileEnding(), "png");
        embedding.setMimeType(MimeType.GIF);
        assertEquals(embedding.getFileEnding(), "gif");
        embedding.setMimeType(MimeType.BMP);
        assertEquals(embedding.getFileEnding(), "bmp");
        embedding.setMimeType(MimeType.JPEG);
        assertEquals(embedding.getFileEnding(), "jpeg");
        embedding.setMimeType(MimeType.HTML);
        assertEquals(embedding.getFileEnding(), "html");
        embedding.setMimeType(MimeType.XML);
        assertEquals(embedding.getFileEnding(), "xml");
        embedding.setMimeType(MimeType.JSON);
        assertEquals(embedding.getFileEnding(), "json");
        embedding.setMimeType(MimeType.APPLICATION_XML);
        assertEquals(embedding.getFileEnding(), "xml");
        embedding.setMimeType(MimeType.SVG);
        assertEquals(embedding.getFileEnding(), "svg");
        embedding.setMimeType(MimeType.SVG_XML);
        assertEquals(embedding.getFileEnding(), "svg");
        embedding.setMimeType(MimeType.TXT);
        assertEquals(embedding.getFileEnding(), "txt");
        embedding.setMimeType(MimeType.PDF);
        assertEquals(embedding.getFileEnding(), "pdf");
        embedding.setMimeType(MimeType.UNKNOWN);
        assertEquals(embedding.getFileEnding(), "unknown");
    }
}
