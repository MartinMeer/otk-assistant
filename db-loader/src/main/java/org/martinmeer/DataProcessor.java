package org.martinmeer;


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


public class DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    private final Path inputFile;
    private final String elementType;

    public DataProcessor(Path inputFile, String elementType) {
        this.inputFile = inputFile;
        this.elementType = elementType;
    }
    //private final int payload;

    public void process() throws IOException {
        if (elementType.isEmpty()) {
            processRange();
        } else {
            processRttThenMainReference();
        }
    }

    public void processRange() throws IOException {
        if (!Files.isReadable(inputFile)) {
            throw new AccessDeniedException("Source is not readable: " + inputFile);
        }
        String inputLine;
        try (BufferedReader bf = Files.newBufferedReader(inputFile)) {
            while ((inputLine = bf.readLine()) != null) {
                InputProcessor inputProcessor = new InputProcessor(inputLine, elementType);
                SQLExecutor exec = new SQLExecutor();
                Range range = inputProcessor.createRange();
                Connection conn = DatabaseConnection.getConnection();
                exec.saveRange(conn, range);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void processRttThenMainReference() throws IOException {
        if (!Files.isReadable(inputFile)) {
            throw new AccessDeniedException("Source is not readable: " + inputFile);
        }
        String inputLine;
        try (BufferedReader bf = Files.newBufferedReader(inputFile)) {
            while ((inputLine = bf.readLine()) != null) {
                InputProcessor inputProcessor = new InputProcessor(inputLine, elementType);
                SQLExecutor exec = new SQLExecutor();
                Connection conn = DatabaseConnection.getConnection();
                exec.saveRttAndMainReference(conn, inputProcessor);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
