package org.martinmeer.otkassistant.core.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.model.InputData;
import org.martinmeer.otkassistant.core.model.OutputDataMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class MainService<E extends Enum<E>, V> {

    @Setter
    private InputData inputData;
    private InputRefiner<E, V> inputRefiner;
    private ComparedDataProcessor<E, V> comparedDataProcessor;
    private CalculatedDataProcessor calculatedDataProcessor;
    private OutputDataMapper outputDataMapper;


    //public abstract Map<String, String> generateOutput(String input);

    public MainService(InputRefiner<E, V> inputRefiner, ComparedDataProcessor<E, V> comparedDataProcessor, CalculatedDataProcessor calculatedDataProcessor, OutputDataMapper outputDataMapper) {
        this.inputData = inputData;
        this.inputRefiner = inputRefiner;
        this.comparedDataProcessor = comparedDataProcessor;
        this.calculatedDataProcessor = calculatedDataProcessor;
        this.outputDataMapper = outputDataMapper;
    }

    public Map<String, String> generateOutput(String input) {
        inputRefiner.generateDataMap(input);

        return outputDataMapper.generateOutputData();
    };


}
