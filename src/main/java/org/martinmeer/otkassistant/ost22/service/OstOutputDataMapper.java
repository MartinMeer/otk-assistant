package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractCalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.martinmeer.otkassistant.core.service.OutputDataMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OstOutputDataMapper implements OutputDataMapper {
    @Override
    public Map<String, String> generateOutputData(Map<String, AbstractFetchedData> fetchedDataMap, Map<String, AbstractCalculatedData> calculatedDataMap) {
        return Map.of();
    }
}
