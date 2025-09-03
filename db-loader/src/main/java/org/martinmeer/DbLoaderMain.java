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
        Path inputFile = Path.of("/home/oleg/IdeaProjects/otk-assistant/src/main/resources/db-src/esdp/shaft/shaft-aOK.txt");
        String elementType = "";
        DataProcessor dataProcessor = new DataProcessor(inputFile, elementType);
        dataProcessor.processRange();

}



}