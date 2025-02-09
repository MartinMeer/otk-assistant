package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.core.model.sceletal.CalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.FetchedData;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public interface OutputDataMapper {

    Map <String, String> generateOutputData(Map<String, FetchedData> fetchedDataMap,
                              Map<String, CalculatedData> calculatedDataMap);

}
