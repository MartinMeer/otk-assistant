package org.martinmeer.otkassistant.facets.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.martinmeer.otkassistant.core.service.CalculatedDataService;
import org.martinmeer.otkassistant.core.service.FetchedDataService;
import org.martinmeer.otkassistant.core.service.MainService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class FacetMainService extends MainService {


    public FacetMainService(AbstractInputData inputData, FetchedDataService fetchedDataService, CalculatedDataService calculatedDataService, OutputDataMapper outputDataMapper, String page) {
        super(inputData, fetchedDataService, calculatedDataService, outputDataMapper, page);
    }
}
