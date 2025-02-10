package org.martinmeer.otkassistant.mthread.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.CalculatedDataProcessor;
import org.martinmeer.otkassistant.core.service.FetchedDataProcessor;
import org.martinmeer.otkassistant.core.service.MainService;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.springframework.stereotype.Component;

import java.util.Map;





@Component
public class MTheradMainService extends MainService {


    public MTheradMainService(AbstractInputData inputData, FetchedDataProcessor fetchedDataProcessor, CalculatedDataProcessor calculatedDataProcessor, OutputDataMapper outputDataMapper) {
        super(inputData, fetchedDataProcessor, calculatedDataProcessor, outputDataMapper);
    }

    @Override
    public Map<String, String> generateOutput(String input) {
        return Map.of();
    }
}
