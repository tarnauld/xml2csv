package com.koloth.xml2csv.writing;


import com.koloth.xml2csv.parsing.object.XMLObject;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class XmlToCSV {
    public enum Delimiter{
        PERIOD,
        SEMICOLON,
        PIPE
    }

    private String getdelimiter(Delimiter d){
        switch (d){
            case PIPE:
                return "|";
            case PERIOD:
                return ",";
            case SEMICOLON:
                return ";";
        }
        return " ";
    }

    public void write(String f, ArrayList<XMLObject> o, Delimiter d){
        Path p = Paths.get(f);
        String delimiter = getdelimiter(d);

        try {
            Files.deleteIfExists(p);
            Files.createFile(p);

            for(XMLObject x: o){
                Files.write(p, x.toString(delimiter).getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
