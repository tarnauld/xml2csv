package com.koloth.xml2csv.stream;

import java.io.*;
import java.net.URL;

/**
 * @author Timothée Arnauld
 */
public class StreamDownload extends StreamUtil implements Stream {
    /**
     * Open a web stream by using an URL.
     * @param url: a string respecting the url protocol.
     * @param filename: the file to create.
     */
    @Override
        public void download(String url, String filename) {
            try {
                this.url = new URL(url);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.url.openStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
                StringBuilder stringBuilder = new StringBuilder();
                String s;

                while ((s = bufferedReader.readLine())!= null){
                    stringBuilder.append(s.replace("\0", ""));
                    stringBuilder.append("\n");
                }

                bufferedWriter.write(stringBuilder.toString());
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
