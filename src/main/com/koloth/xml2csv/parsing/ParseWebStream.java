package com.koloth.xml2csv.parsing;

import com.koloth.xml2csv.parsing.object.XMLObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Timothée Arnauld
 */
public class ParseWebStream implements Parsing {
    /**
     * This method returns an ArrayList containing all data to parse
     * which correspond to the schema defining as a parameter
     * @param stream: the stream to parse
     * @param schema: the schema used to parse the stream
     * @return an ArrayList containing all the matched strings.
     */
    @Override
    public ArrayList<String> getAllNodesCorresponding(String stream, ArrayList<String>schema){
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> result = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;

        for(String s: schema){
            String t = "<" + s + ".*?>";
            stringBuilder.append(t);
        }
        Collections.reverse(schema);
        for(String s: schema){
            String t = ".*?</" + s + ".*?>";
            stringBuilder.append(t);
        }

        pattern = Pattern.compile(stringBuilder.toString());
        matcher = pattern.matcher(stream);

        while(matcher.find()){
            result.add(stream.substring(matcher.start(), matcher.end()));
        }

        return result;
    }

    @Override
    public ArrayList<XMLObject> parse(String stream, ArrayList<String> schema) {
        ArrayList<String> result = getAllNodesCorresponding(stream, schema);
        ArrayList<XMLObject> objects = new ArrayList<>();

        for(String s: result){
            Pattern pattern = Pattern.compile(">\\s*[a-zA-Z0-9_]+\\s*<");
            Matcher matcher = pattern.matcher(s);
            XMLObject o = new XMLObject();

            while (matcher.find()){
                String t = s.substring(matcher.start() + 1, matcher.end() - 1);
                o.addValue(t);
            }
            objects.add(o);
        }

        return objects;
    }

    @Override
    public ArrayList<String> initSchemaFromFile(String filename) {
        Path p = Paths.get(filename);
        Pattern pattern = Pattern.compile("<[a-zA-Z]+\\s*>");
        ArrayList<String> lines, result = new ArrayList<>();

        try {
            lines = (ArrayList<String>) Files.readAllLines(p);
            for(String s: lines){
                Matcher matcher = pattern.matcher(s);
                while(matcher.find()){
                    String t = s.substring(matcher.start() + 1, matcher.end() - 1).
                            replace(" ", "");
                    result.add(t);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return result;
    }
}
