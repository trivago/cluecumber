package com.trivago.cluecumber.engine.json.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocStringTest {

    private DocString docString;

    @BeforeEach
    public void setup() {
        docString = new DocString();
    }

    @Test
    public void returnWithClickableLinksEmptyTest() {
        assertEquals(docString.returnWithClickableLinks(), "");
    }

    @Test
    public void returnWithClickableLinksTest() {
        docString.setValue("This should be a https://www.google.de link");
        assertEquals(docString.returnWithClickableLinks(), "This should be a <a href='https://www.google.de' target='_blank'>https://www.google.de</a> link");
    }

    @Test
    public void returnWithClickableLinksFalseAlarmTest() {
        docString.setValue("This should be a profile link with some profile stuff.");
        assertEquals(docString.returnWithClickableLinks(), "This should be a profile link with some profile stuff.");
    }

    @Test
    public void returnWithClickableLocalLinksTest() {
        docString.setValue("The shared location is <a href='file:\\MACHINE\\Folder\\Some Folder' target='_blank'>file:\\MACHINE\\Folder\\Some Folder</a>");
        assertEquals(docString.returnWithClickableLinks(), "The shared location is &#60;a href='file:\\MACHINE\\Folder\\Some Folder' target='_blank'&#62;file:\\MACHINE\\Folder\\Some Folder&#60;/a&#62;");
    }

    @Test
    public void returnWithClickableLinksNoUrlTest() {
        docString.setValue("This should be not a clickable www.google.de link");
        assertEquals(docString.returnWithClickableLinks(), "This should be not a clickable www.google.de link");
    }
}