package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;

import java.util.Map;


public abstract class MainService {

    private final SchemaAwareNamedParameterJdbcTemplate jdbcTemplate;
    private final AbstractInputData inputData;
    private final FetchedDataService fetchedDataService;
    private final CalculatedDataService calculatedDataService;
    private final OutputDataMapper outputDataMapper;

    public MainService(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate, AbstractInputData inputData,
                       FetchedDataService fetchedDataService,
                       CalculatedDataService calculatedDataService,
                       OutputDataMapper outputDataMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.inputData = inputData;
        this.fetchedDataService = fetchedDataService;
        this.calculatedDataService = calculatedDataService;
        this.outputDataMapper = outputDataMapper;
    }

    public Map<String, String> generateOutput(String page, String input) {
        jdbcTemplate.setSchemaName(page);
        var rawData = inputData.createInputData(input);
        fetchedDataService.setInputData(rawData);
        var fetchedDataMap = fetchedDataService.genFetchedDataMap();
        var calculatedDataMap = calculatedDataService.genCalculatedDataMap(fetchedDataMap);
        return outputDataMapper.generateOutputData(fetchedDataMap, calculatedDataMap);
    }


}
