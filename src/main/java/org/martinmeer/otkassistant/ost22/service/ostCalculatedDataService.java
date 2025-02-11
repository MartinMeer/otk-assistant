package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractCalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.martinmeer.otkassistant.core.service.CalculatedDataService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ostCalculatedDataService extends CalculatedDataService {
    @Override
    public Map<String, AbstractCalculatedData> genCalculatedDataMap(Map<String, AbstractFetchedData> fetchedDataMap) {
        return Map.of();
    }
}
