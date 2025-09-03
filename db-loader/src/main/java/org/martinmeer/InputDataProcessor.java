package org.martinmeer;

import org.martinmeer.repo.MainReference;
import org.martinmeer.repo.Range;
import org.martinmeer.repo.RangeByToleranceByType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputDataProcessor {
    Logger logger = LoggerFactory.getLogger(InputDataProcessor.class);

    private final String inputLine;
    private final String elementType;
    private final UUID uuid;


    public InputDataProcessor(String inputLine, String elementType) {
        this.inputLine = normalize(inputLine);
        this.elementType = elementType;
        uuid = generateUUID();
    }

    //INPUT EXAMPLE "0-3","A","{""9"":[295,270],""10"":[310,270],""11"":[330,270],""12"":[370,270],""13"":[410,270]}"

    public Range createRange() {
        return new Range(generateRange());
    }

    public List<MainReference> createMainReferencesList() {
        List<Map<String, String>> gradeMapsList = generateGradeMapsList();
        List<MainReference> mainReferencesList = new ArrayList<>();
        for (Map<String, String> gradeMap : gradeMapsList) {
            MainReference mainReference = MainReference.builder()
                    .rtt_id(uuid)
                    .dev_code(Integer.parseInt(gradeMap.get("dev_code")))
                    .es(Integer.parseInt(gradeMap.get("es")))
                    .ei(Integer.parseInt(gradeMap.get("ei")))
                    .build();
            mainReferencesList.add(mainReference);
        }
        return mainReferencesList;
    }

    public RangeByToleranceByType createRangeByToleranceByType() {
        Range range = createRange();
        return RangeByToleranceByType.builder()
                .rtt_id(uuid)
                .bas_tol(generateBasicTolerance())
                .el_type(elementType)
                .range(range.range())
                .build();
    }

    private String normalize(String inputLine) {
        return inputLine.replaceAll("\"", "");
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }

    private String generateRange() {
        Pattern rangePattern = Pattern.compile("(\\d+)-(\\d+)");
        Matcher matcher = rangePattern.matcher(inputLine);
        if (matcher.find()) {
            String range = "(" +
                    matcher.group(1) +
                    "," +
                    matcher.group(2) +
                    "]";
            return range;
        }
        logger.error("Range not found{}", rangePattern);
        return "";
    }

    private String generateBasicTolerance() {
        Pattern basicTolerancePattern = Pattern.compile("([A-Za-z]+)");
        Matcher matcher = basicTolerancePattern.matcher(inputLine);
        if (matcher.find()) {
            return matcher.group().toLowerCase();
        }
        //throw new IllegalStateException();
        logger.error("BasicTolerance not found{}", basicTolerancePattern);
        return "";
    }

    //"{""9"":[295,270],""10"":[310,270],""11"":[330,270],""12"":[370,270],""13"":[410,270]}"
    private List<Map<String, String>> generateGradeMapsList() {
        Pattern valuesPattern = Pattern.compile("(\\d+):\\[(-?\\d+),(-?\\d+)]");
        List<Map<String, String>> gradeMapsList = new ArrayList<>();
        Matcher matcher = valuesPattern.matcher(inputLine);
        try {
            while (matcher.find()) {
                Map<String, String> gradeMap = new HashMap<>();
                gradeMap.put("dev_code", matcher.group(1));
                gradeMap.put("es", matcher.group(2));
                gradeMap.put("ei", matcher.group(3));
                gradeMapsList.add(gradeMap);
            }
            return gradeMapsList;
        } catch (Exception e) {
            logger.error("valuesPattern not found{}", valuesPattern);
        }
        return new ArrayList<>();
    }
}

