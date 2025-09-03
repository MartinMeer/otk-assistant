package org.martinmeer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Main class for the DB Loader application
 */
public class DbLoaderMain {
    private static final Logger logger = LoggerFactory.getLogger(DbLoaderMain.class);

    public static void main(String[] args) throws SQLException, IOException {

        Path inputFile = Path.of(args[0]).toAbsolutePath();
        String elementType;
        switch (args[1]) {
            case "-h" -> elementType = "hole";
            case "-s" -> elementType = "shaft";
            case null -> elementType = "";
            default -> throw new IllegalStateException("Unexpected value: " + args[1]
            + "-h for hole, -s for shaft");
        }
        DataProcessor dataProcessor = new DataProcessor(inputFile, elementType);
        dataProcessor.process();

}



}