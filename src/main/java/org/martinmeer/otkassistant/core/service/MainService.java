package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.core.model.OutputDataMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class MainService<E extends Enum<E>, V> {

    private InputRefiner<E, V> inputRefiner;
    private ComparedDataProcessor<E, V> comparedDataProcessor;
    private CalculatedDataProcessor calculatedDataProcessor;
    private OutputDataMapper outputDataMapper;


    public abstract Map<String, String> generateOutput(String input);

    public Map<String, String> TEMPLgenerateOutput(String input) {
        inputRefiner.generateDataMap(input);





    };


}
