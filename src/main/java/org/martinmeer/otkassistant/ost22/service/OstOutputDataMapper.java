package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.sceletal.CalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.FetchedData;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OstOutputDataMapper implements OutputDataMapper {
    @Override
    public Map<String, String> generateOutputData(Map<String, FetchedData> fetchedDataMap, Map<String, CalculatedData> calculatedDataMap) {
        return Map.of();
    }
}
