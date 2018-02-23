package com.koloth.xml2csv.parsing;

import com.koloth.xml2csv.parsing.object.XMLObject;
import com.koloth.xml2csv.parsing.utils.XMLValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class ParseWebStreamImplTest {
    private ParseWebStream parseWebStream;
    private String stream;
    private XMLValidator xmlValidator;
    private ArrayList<String> schema = new ArrayList<>();

    @Before
    public void init(){
        String template  = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"/><title>Document de test</title></head><body><table><tr><td>Lorem</td><td>ipsum</td><td>dolor</td><td>sit</td></tr><tr><td>amet,</td><td>consectetur</td><td>adipiscing</td><td>elit</td></tr></table></body></html>";
        String root = "body";
        schema.add("tr");
        parseWebStream = new ParseWebStream();
        xmlValidator = new XMLValidator();
        stream = xmlValidator.fromStream(template, root);
    }

    @Test
    public void shouldFindAtLeastOneMatch(){
        ArrayList<String>s = parseWebStream.getAllNodesCorresponding(stream, schema);
        for(String t : s){
            System.out.println(t);
        }
        assertNotNull(s);
    }

    @Test
    public void shouldReturnAllObjects(){
        ArrayList<XMLObject> o = parseWebStream.parse(stream, schema);

        for(XMLObject t: o)
            System.out.print(t.toString(","));

        assertNotNull(o);
    }

    @Test
    public void shouldNotReturnNull(){
        ArrayList<String> list = parseWebStream.initSchemaFromFile("schema.txt");
        assertNull(list);
    }
}