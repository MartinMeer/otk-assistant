package org.martinmeer;


import lombok.Builder;

import org.martinmeer.repo.Range;
import org.martinmeer.utils.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

@Builder
public class DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    private final Path inputFile;
    private final String elementType;
    //private final int payload;

    public void processRange() throws IOException {
        if (!Files.isReadable(inputFile)) {
            throw new AccessDeniedException("Source is not readable: " + inputFile);
        }
        try (BufferedReader bf = Files.newBufferedReader(inputFile)) {
            while (bf.readLine() != null) {
                String inputLine = bf.readLine();
                InputDataProcessor inputDataProcessor = new InputDataProcessor(inputLine, "");
                SQLExecutor exec = new SQLExecutor();
                Range range = inputDataProcessor.createRange();
                Connection conn = DatabaseConnection.getConnection();
                exec.saveRange(conn, range);

            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
