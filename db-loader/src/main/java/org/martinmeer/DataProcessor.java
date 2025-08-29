package org.martinmeer;


import lombok.Builder;
import org.martinmeer.repo.InputData;
import org.martinmeer.repo.Table;
import org.martinmeer.utils.Parser;
import org.martinmeer.utils.InputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
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


    private InputData getInputData() throws IOException {
        if (!Files.isReadable(inputFile)) {
            throw new AccessDeniedException("Source is not readable: " + inputFile);
        }
        try (BufferedReader bf = Files.newBufferedReader(inputFile)) {
            while (bf.readLine() != null) {
                return new InputData(bf.readLine());
            }
            ;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private void saveData(InputData inputData) throws IOException {

    }

    public void saveToFile(InputData inputData) throws IOException {
       Path outputFile = Files.createTempFile("uuid-range-es-ei", ".tmp");
        try(BufferedWriter bw = Files.newBufferedWriter(outputFile)) {
            bw.write(generateOutputLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateOutputLine() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        String delimiter = inputData.getDelimiter();
        Map<String, String> gradeMap = inputData.getGradeMap();
        return String.valueOf(sb
                .append(uuid.toString())
                .append(delimiter)
                .append(gradeMap.get("es"))
                .append(delimiter)
                .append(gradeMap.get("ei")));
    }

    private String generateRequest() {

    }

    public void saveToDb() {

    }
}
