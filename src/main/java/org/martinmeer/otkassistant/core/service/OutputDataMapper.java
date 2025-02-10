package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractCalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface OutputDataMapper {

    Map <String, String> generateOutputData(Map<String, AbstractFetchedData> fetchedDataMap,
                              Map<String, AbstractCalculatedData> calculatedDataMap);

}
