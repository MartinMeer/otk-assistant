package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.sceletal.CalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.FetchedData;
import org.martinmeer.otkassistant.core.service.CalculatedDataProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ostCalculatedDataProcessor extends CalculatedDataProcessor {
    @Override
    public Map<String, CalculatedData> genCalculatedDataMap(Map<String, FetchedData> fetchedDataMap) {
        return Map.of();
    }
}
