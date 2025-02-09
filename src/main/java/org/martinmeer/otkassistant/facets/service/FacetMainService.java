package org.martinmeer.otkassistant.facets.service;

import org.martinmeer.otkassistant.core.model.sceletal.InputData;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.martinmeer.otkassistant.core.service.CalculatedDataProcessor;
import org.martinmeer.otkassistant.core.service.FetchedDataProcessor;
import org.martinmeer.otkassistant.core.service.InputRefiner;
import org.martinmeer.otkassistant.core.service.MainService;
import org.springframework.stereotype.Component;

@Component
public class FacetMainService extends MainService {


    public FacetMainService(InputData inputData, FetchedDataProcessor fetchedDataProcessor, CalculatedDataProcessor calculatedDataProcessor, OutputDataMapper outputDataMapper) {
        super(inputData, fetchedDataProcessor, calculatedDataProcessor, outputDataMapper);
    }
}
