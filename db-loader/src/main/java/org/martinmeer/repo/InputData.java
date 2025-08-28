package org.martinmeer.repo;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


public class InputData {

    private static Pattern pattern;
    private UUID uuid;
    private String range;
    private String basicTolerance;
    private String delimiter;
    private Map<String, String> gradeMap;

    public InputData(String inputLine) {
        parse(inputLine);
    }


    /**
     * Process CSV file line by line.
     * Return a List of ElementTypeTable instances for loading in one transaction.
     */
    //"0-3","A","{""9"":[295,270],""10"":[310,270],""11"":[330,270],""12"":[370,270],""13"":[410,270]}"
    private void parse(String inputLine) {
        pattern = Pattern.compile("");
        range = "";

    }

}

