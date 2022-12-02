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
    public void returnWithClickableLocalLinksTest() {
        docString.setValue("The shared location is file:\\MACHINE\\Folder\\Some Folder");
        assertEquals(docString.returnWithClickableLinks(), "The shared location is <a href='file:\\MACHINE\\Folder\\Some Folder' target='_blank'>file:\\MACHINE\\Folder\\Some Folder</a>");
    }

    @Test
    public void returnWithClickableLinksNoUrlTest() {
        docString.setValue("This should be not a clickable www.google.de link");
        assertEquals(docString.returnWithClickableLinks(), "This should be not a clickable www.google.de link");
    }
}