package com.trivago.cluecumber.json.pojo;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DocStringTest {

    private DocString docString;

    @Before
    public void setup() {
        docString = new DocString();
    }

    @Test
    public void returnWithClickableLinksEmptyTest() {
        assertThat(docString.returnWithClickableLinks(), is(""));
    }

    @Test
    public void returnWithClickableLinksTest() {
        docString.setValue("This should be a http://www.google.de link");
        assertThat(docString.returnWithClickableLinks(), is("This should be a <a href='http://www.google.de' target='_blank'>http://www.google.de</a> link"));
    }
    
    @Test
    public void returnWithClickableLocalLinksTest() {
        docString.setValue("The shared location is file:\\MACHINE\\Folder\\SomeFolder");
        assertThat(docString.returnWithClickableLinks(), is("The shared location is <a href='file:\\MACHINE\\Folder\\SomeFolder' target='_blank'>file:\\MACHINE\\Folder\\SomeFolder</a>"));
    }

    @Test
    public void returnWithClickableLinksNoUrlTest() {
        docString.setValue("This should be not a clickable www.google.de link");
        assertThat(docString.returnWithClickableLinks(), is("This should be not a clickable www.google.de link"));
    }
}