package com.koloth.xml2csv.parsing.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class XMLValidatorTest {
    XMLValidator xmlValidator;

    @Before
    public void init(){
        xmlValidator = new XMLValidator();
    }

    @Test
    public void shouldCleanXML(){
        String str = "<!DOCTYPE html><html><!--Exemple of comment--><head><meta charset=\"utf-8\"></head><body><p width=88px height=\"28px\">TEST</p></BODY></html>";
        assertEquals("<html><head><meta charset=\"utf-8\"/></head><body><p width=\"88px\" height=\"28px\">TEST</p></body></html>",
                xmlValidator.cleanXML(str, "html"));
    }

    @Test
    public void shouldNotReturnEmptyString(){
        String str = "<HTML><BODY><P>TEST</P></BODY></HTML>";
        assertEquals( "<html><body><p>TEST</p></body></html>",
                xmlValidator.cleanTags(str));
    }

    @Test
    public void shouldRemoveAllXMLComments(){
        String str = "<html><body><p><!-- comment-->TEST</p></body></html>";
        assertEquals("<html><body><p>TEST</p></body></html>",
                xmlValidator.cleanComments(str));
    }

    @Test
    public void shouldCloseOrphanTags(){
        String str = "<br><html><meta charset=\"utf-8\"></html>";
        assertEquals("<br/><html><meta charset=\"utf-8\"/></html>",
                xmlValidator.closeAllOrphanTags(str));
    }

    @Test
    public void shouldCorrectAllAttributes(){
        String str = "<html><p width=\"50px\" tmp=25% height=60px></html>";
        assertEquals("<html><p width=\"50px\" tmp=\"25%\" height=\"60px\"></html>",
                xmlValidator.cleanAttributes(str));
    }
}