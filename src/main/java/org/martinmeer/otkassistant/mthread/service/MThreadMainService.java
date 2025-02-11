package org.martinmeer.otkassistant.mthread.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.CalculatedDataService;
import org.martinmeer.otkassistant.core.service.FetchedDataService;
import org.martinmeer.otkassistant.core.service.MainService;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MThreadMainService extends MainService {


    public MThreadMainService(@Qualifier("mThreadInputData") AbstractInputData inputData, FetchedDataService fetchedDataService, CalculatedDataService calculatedDataService, OutputDataMapper outputDataMapper, String page) {
        super(inputData, fetchedDataService, calculatedDataService, outputDataMapper);
    }
}
