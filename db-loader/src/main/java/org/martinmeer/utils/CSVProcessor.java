package org.martinmeer.utils;

import org.martinmeer.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


public class CSVProcessor {
    private static File input;
    private static File output;
    private static Pattern pattern;
    private Table tableForLoad;


    /**
     * Process CSV file line by line.
     * Return a List of Table instances for loading in one transaction.
     */
    public static List<Table> processCSV(File input, Pattern pattern, int payload) {

        return new ArrayList<>();

    }

}

