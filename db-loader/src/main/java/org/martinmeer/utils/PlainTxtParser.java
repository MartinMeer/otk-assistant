package org.martinmeer.utils;

import org.martinmeer.repo.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class PlainTxtParser implements Parser{
    private static File input;
    private static File output;
    private static Pattern pattern;
    private Table elementTypeTableForLoad;


    /**
     * Process CSV file line by line.
     * Return a List of ElementTypeTable instances for loading in one transaction.
     */
    //"0-3","A","{""9"":[295,270],""10"":[310,270],""11"":[330,270],""12"":[370,270],""13"":[410,270]}"
    public static List<Table> parse(File input, Pattern pattern, int payload) {

        return new ArrayList<>();

    }

}

