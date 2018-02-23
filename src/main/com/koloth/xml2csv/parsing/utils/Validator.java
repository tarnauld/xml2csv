package com.koloth.xml2csv.parsing.utils;

/**
 * @author Timothée Arnauld
 */
public interface Validator {
    /**
     * Method used to clean a steam by respecting all conventions.
     * @param stream: the stream to clean
     * @return a cleaned stream
     */
    String cleanXML(String stream, String root);

    /**
     * Method used to clean all attributes by using the language convention
     * @return a cleaned string
     */
    String cleanAttributes(String stream);

    /**
     * Method used to clean all tags by using the language convention
     * @return a cleaned string
     */
    String cleanTags(String stream);

    /**
     * Method used to clean all comment within a stream
     * @param stream: the stream to clean
     * @return a cleaned string
     */
    String cleanComments(String stream);

    /**
     * Method which close the tags passed as parameters
     * as an orphan tag.
     * @param stream: the stream to verify
     * @return a correct string
     */
    String closeAllOrphanTags(String stream);

    /**
     * Method which close the tags passed as parameters
     * by peer.
     * @param stream: the stream to verify
     * @return a correct string
     */
    String closeAllTags(String stream);
}
