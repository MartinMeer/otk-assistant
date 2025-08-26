package org.martinmeer;

import org.martinmeer.repo.Table;
import org.martinmeer.utils.PlainTxtParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Main class for the DB Loader application
 */
public class DbLoaderMain {
    private static final Logger logger = LoggerFactory.getLogger(DbLoaderMain.class);

    public static void main(String[] args) throws SQLException {
        int payload = 10;
        File file = new File("");
        Pattern pattern = Pattern.compile("");
        List<Table> loadData = PlainTxtParser.parse(file, pattern, payload);
        try {
            SQLExecutor.execute(loadData);
        } catch (SQLException e) {
            logger.error("Error db interaction {}", e.getMessage());
        }
    }



}