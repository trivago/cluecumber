package com.trivago.cluecumber.json.pojo;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DocStringTest {

    private DocString docString;

    @Before
    public void setup() {
        docString = new DocString();
    }

    @Test
    public void returnWithClickableLinksEmptyTest() throws CluecumberPluginException {
        assertThat(docString.returnWithClickableLinks(), is(""));
    }

    @Test
    public void returnWithClickableLinksTest() throws CluecumberPluginException {
        docString.setValue("This should be a http://www.google.de link");
        assertThat(docString.returnWithClickableLinks(), is("This should be a <a href='http://www.google.de' target='_blank'>http://www.google.de</a> link"));
    }
    
    @Test
    public void returnWithClickableLocalLinksTest() throws CluecumberPluginException {
        docString.setValue("The shared location is file:\\MACHINE\\Folder\\Some Folder");
        assertThat(docString.returnWithClickableLinks(), is("The shared location is <a href='file:\\MACHINE\\Folder\\Some Folder' target='_blank'>file:\\MACHINE\\Folder\\Some Folder</a>"));
    }

    @Test
    public void returnWithClickableLinksNoUrlTest() throws CluecumberPluginException {
        docString.setValue("This should be not a clickable www.google.de link");
        assertThat(docString.returnWithClickableLinks(), is("This should be not a clickable www.google.de link"));
    }
}