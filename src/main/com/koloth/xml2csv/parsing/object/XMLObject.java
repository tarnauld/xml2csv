package com.koloth.xml2csv.parsing.object;

import java.util.ArrayList;

public class XMLObject extends Object{
    public XMLObject(){
        this.nodes = new ArrayList<String>();
    }

    public ArrayList<String> getObjects(){
        return this.nodes;
    }

    public void addValue(String s){
        this.nodes.add(s);
    }

    public void removeValue(int index){
        this.nodes.remove(index);
    }

    @Override
    public int compareTo(java.lang.Object o) {
        return 0;
    }

    public String toString(String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s: nodes){
            stringBuilder.append(s).append(delimiter);
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
