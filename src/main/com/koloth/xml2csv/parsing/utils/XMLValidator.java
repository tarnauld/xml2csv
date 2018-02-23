package com.koloth.xml2csv.parsing.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.Charset.*;

public class XMLValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private StringBuffer stringBuffer;
    private List<String> htmlOrphansTags = Arrays.asList("area","base","br","col","command","embed","hr","img","input","keygen","link","meta","param","source","track","wbr");

    /**
     * Pass the xml string you need to clean as a parameter.
     * This method will remove every null byte and bugs from encoding.
     * @param stream: the xml string you need to clean
     * @return a new string cleaned.
     */
    @Override
    public String cleanXML(String stream, String root) {
        StringBuilder stringBuilder = new StringBuilder(stream.replace("\0", "").replace("'", "\""));

        if(stringBuilder.toString().contains(root)){
            stringBuilder.delete(0, stringBuilder.indexOf("<" + root ));
            stringBuilder.delete(stringBuilder.indexOf("</" + root + ">") + ("</" + root + ">").length(), stringBuilder.length());
        }

        return closeAllOrphanTags(cleanTags(cleanAttributes(cleanComments(stringBuilder.toString()))));
    }

    /**
     * Pass the xml string you need to clean as a parameter.
     * This method will clean every attribute to respect all
     * xml naming conventions.
     * @param stream: the xml string you need to clean
     * @return a new string cleaned
     */
    @Override
    public String cleanAttributes(String stream){
        stream = stream.replace(">", " >");
        pattern = Pattern.compile("[a-zA-Z]*=[^\"]*?\\s");
        matcher = pattern.matcher(stream);
        stringBuffer = new StringBuffer(stream);

        while(matcher.find()){
            String tmp = stringBuffer.substring(matcher.start(), matcher.end());
            String s = tmp.
                    replace("=", "=\"").
                    replace(" ", "\" ");
            stream = stream.replace(tmp, s);
        }
        stringBuffer = new StringBuffer(stream.replace(" >", ">"));
        return stringBuffer.toString();
    }

    /**
     * Pass the xml string you need to clean as a parameter.
     * This method will clean every tags to respect all
     * xml naming conventions.
     * @param stream: the xml string you need to clean
     * @return a new string cleaned
     */
    @Override
    public String cleanTags(String stream){
        pattern = Pattern.compile("<.*?>");
        matcher = pattern.matcher(stream);
        stringBuffer = new StringBuffer(stream);

        while (matcher.find()){
            String s = stream.substring(matcher.start(), matcher.end()).toLowerCase();
            stringBuffer.replace(matcher.start(), matcher.end(), s);
        }
        return stringBuffer.toString();
    }

    /**
     * Pass the xml string you need to clean as a parameter.
     * This method will remove every comment to have a lighter
     * file and reduce all non-important data.
     * @param stream: the xml string you need to clean
     * @return a new string cleaned
     */
    @Override
    public String cleanComments(String stream) {
        return stream.replaceAll("<!--.*?-->", "");
    }

    /**
     * Pass the xml string you need to verify and a list containing
     * all the tags which are supposed to be single.
     * This method will close all of these.
     * @param stream: the stream to verify
     * @return a correct string
     */
    @Override
    public String closeAllOrphanTags(String stream) {
        int matchesNb = 0; //Useful because the index is not automatically update by the matcher.
        stringBuffer = new StringBuffer(stream);

        for (String s: htmlOrphansTags){
            pattern = Pattern.compile("<"+ s + ".*?>");
            matcher = pattern.matcher(stream);
            while(matcher.find()){
                if(!stream.substring(matcher.start(), matcher.end()).contains("/>"))
                    stringBuffer.insert(matcher.end() - (1 - matchesNb++), '/');
            }
        }
        return stringBuffer.toString();
    }

    //TODO: Implement the closeAllTag function
    /**
     * Pass the xml string you need to verify and the method
     * will automatically close every tag by peer.
     * @param stream: the stream to verify
     * @return a correct string
     */
    @Override
    public String closeAllTags(String stream) {
        return null;
    }

    public String fromFile(String filename, String root){
        StringBuilder stringBuilder = new StringBuilder();
        Path p = Paths.get(filename);

        Map<String, Charset> map = Charset.availableCharsets();

        Charset charset = Charset.forName("UTF-8");
        int encoding = 0;

        for(Charset c: map.values()){
            try {
                Files.readAllLines(p, c);
                charset = c;
            }catch (MalformedInputException e){
                encoding++;
            }catch (IOException e){
                System.out.print("");
            }
        }

        System.out.println(encoding + " encodages ont été testé.");
        System.out.println(charset.toString() + " a été selectionné.");

        if(Files.exists(p)){
            try {
                for(String s: Files.readAllLines(p, charset)) {
                    stringBuilder.append(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cleanXML(stringBuilder.toString(), root);
    }

    public String fromStream(String stream, String root){
        return cleanXML(stream, root);
    }
}
