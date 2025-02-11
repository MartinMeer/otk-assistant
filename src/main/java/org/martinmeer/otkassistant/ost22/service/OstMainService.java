package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.CalculatedDataService;
import org.martinmeer.otkassistant.core.service.FetchedDataService;
import org.martinmeer.otkassistant.core.service.MainService;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

 /*const pageId = 'ost';
       value = response.deviance_values || '';
        response.min_mes_value || '';
       response.max_mes_value || '';*/

@Service
public class OstMainService extends MainService {

    public OstMainService(@Qualifier("ostInputData") AbstractInputData inputData,
                          @Qualifier("ostFetchedDataService") FetchedDataService fetchedDataService,
                          @Qualifier("ostCalculatedDataService") CalculatedDataService calculatedDataService,
                          @Qualifier("ostOutputDataMapper") OutputDataMapper outputDataMapper) {
        super(inputData, fetchedDataService, calculatedDataService, outputDataMapper);
    }

}
