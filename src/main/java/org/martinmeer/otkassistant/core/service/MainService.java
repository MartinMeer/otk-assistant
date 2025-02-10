package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MainService {

    private final AbstractInputData inputData;
    private final FetchedDataProcessor fetchedDataProcessor;
    private final CalculatedDataProcessor calculatedDataProcessor;
    private final OutputDataMapper outputDataMapper;

    public MainService(AbstractInputData inputData,
                       FetchedDataProcessor fetchedDataProcessor,
                       CalculatedDataProcessor calculatedDataProcessor,
                       OutputDataMapper outputDataMapper) {

        this.inputData = inputData;
        this.fetchedDataProcessor = fetchedDataProcessor;
        this.calculatedDataProcessor = calculatedDataProcessor;
        this.outputDataMapper = outputDataMapper;
    }

    public Map<String, String> generateOutput(String input) {
        var rawData = inputData.createInputData(input);
        fetchedDataProcessor.setInputData(rawData);
        var fetchedDataMap = fetchedDataProcessor.genFetchedDataMap();
        var calculatedDataMap = calculatedDataProcessor.genCalculatedDataMap(fetchedDataMap);
        return outputDataMapper.generateOutputData(fetchedDataMap, calculatedDataMap);
    }


}
