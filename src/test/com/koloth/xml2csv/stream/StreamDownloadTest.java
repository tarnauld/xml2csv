package com.koloth.xml2csv.stream;

import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class StreamDownloadTest {
    @Test
    public void shouldDownloadaStream(){
        String filename = "neptune_stream.html";
        StreamDownload stream = new StreamDownload();
        String url = "http://reporting1-neptune.intra.groupama.fr/check.html";
        Path p = Paths.get(filename);
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stream.download(url, filename);
        assertEquals(Files.exists(p), true);
    }
}