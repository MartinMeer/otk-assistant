package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.martinmeer.otkassistant.core.service.CalculatedDataProcessor;
import org.martinmeer.otkassistant.core.service.FetchedDataProcessor;
import org.martinmeer.otkassistant.core.service.InputRefiner;
import org.martinmeer.otkassistant.core.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

 /*const pageId = 'ost';
       value = response.deviance_values || '';
        response.min_mes_value || '';
       response.max_mes_value || '';*/

@Component
public class OstMainService extends MainService {

    @Autowired
    public OstMainService(@Qualifier("ostInputData") AbstractInputData inputData,
                          @Qualifier("ostFetchedDataProcessor") FetchedDataProcessor fetchedDataProcessor,
                          @Qualifier("ostCalculatedDataProcessor") CalculatedDataProcessor calculatedDataProcessor,
                          @Qualifier("ostOutputDataMapper") OutputDataMapper outputDataMapper) {
        super(inputData, fetchedDataProcessor, calculatedDataProcessor, outputDataMapper);
    }
}
