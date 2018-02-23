package com.koloth.xml2csv.parsing;


import com.koloth.xml2csv.parsing.object.XMLObject;
import java.util.ArrayList;

/**
 * @author Timothée Arnauld
 */
public interface Parsing {
    ArrayList<String> getAllNodesCorresponding(String stream, ArrayList<String>schema);
    ArrayList<XMLObject> parse(String stream, ArrayList<String>schema);
    ArrayList<String> initSchemaFromFile(String filename);
}
