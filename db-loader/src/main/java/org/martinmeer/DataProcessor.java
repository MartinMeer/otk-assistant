package org.martinmeer;

import lombok.Builder;
import org.martinmeer.repo.Table;
import org.martinmeer.utils.Parser;
import org.martinmeer.utils.PlainTxtParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.regex.Pattern;

@Builder
public class DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    private final File inputFile;
    private final File outputFile;
    private final Pattern pattern;
    private final int payload;
    private Table table;



    private void getData() {
        Parser parser = new PlainTxtParser();

    }

    private void saveData() {

    }

    public void saveToFile() {

    }

    public void saveToDb() {

    }





}
