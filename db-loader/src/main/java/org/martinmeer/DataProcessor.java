package org.martinmeer;


import lombok.Builder;
import org.martinmeer.repo.InputData;
import org.martinmeer.repo.Table;
import org.martinmeer.utils.Parser;
import org.martinmeer.utils.InputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Pattern;

@Builder
public class DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    private final Path inputFile;
    private File outputFile;
    private final Pattern pattern;
    private final int payload;
    private Table table;
    private InputData inputData;



    private String getInput() throws IOException {
        if (!Files.isReadable(inputFile)) {
            throw new AccessDeniedException("Source is not readable: " + inputFile);
        }
        try (BufferedReader bf = Files.newBufferedReader(inputFile)) {
            return bf.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    private void saveData() {

    }

    public void saveToFile() {

    }

    public void saveToDb() {

    }
}
