package org.martinmeer.otkassistant.facets.service;

import org.martinmeer.otkassistant.core.model.OutputDataMapper;
import org.martinmeer.otkassistant.core.service.CalculatedDataProcessor;
import org.martinmeer.otkassistant.core.service.ComparedDataProcessor;
import org.martinmeer.otkassistant.core.service.InputRefiner;
import org.martinmeer.otkassistant.core.service.MainService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FacetMainService extends MainService {

    public FacetMainService(InputRefiner inputRefiner, ComparedDataProcessor comparedDataProcessor, CalculatedDataProcessor calculatedDataProcessor, OutputDataMapper outputDataMapper) {
        super(inputRefiner, comparedDataProcessor, calculatedDataProcessor, outputDataMapper);
    }
}
