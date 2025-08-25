package org.martinmeer;

import org.martinmeer.utils.CSVProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Main class for the DB Loader application
 */
public class DbLoader {
    private static final Logger logger = LoggerFactory.getLogger(DbLoader.class);

    public static void main(String[] args) {
        int payload = 10;
        File file = new File("");
        Pattern pattern = Pattern.compile("");
        List<Table> loadData = CSVProcessor.processCSV(file, pattern, payload);
        SQLExecutor.execute(loadData);
    }



}