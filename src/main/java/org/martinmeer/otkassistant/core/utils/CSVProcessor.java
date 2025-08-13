package org.martinmeer.otkassistant.core.utils;

import java.io.File;
import java.util.regex.Pattern;

public class CSVProcessor {
    private static File input;
    private static File output;
    private static Pattern pattern;

    public static void main(String[] args) {
        processCSV(input, output, pattern);
    }


    /*Stream process:
    Open BufferedReader for the source.
    Open BufferedWriter for the temp.
    For each line: apply replacement(s) and write the transformed line, followed by a newline via writer.newLine().
    Close streams automatically using try-with-resources.*/
    private static void processCSV(File input, File output, Pattern pattern) {



    }

}

