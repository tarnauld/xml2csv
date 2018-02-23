package com.koloth.xml2csv.stream;

/**
 * @author Timothée Arnauld
 */
public interface Stream {
    void download(String url, String filename);
}
