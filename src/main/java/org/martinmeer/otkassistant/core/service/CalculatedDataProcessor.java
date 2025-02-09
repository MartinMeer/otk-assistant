package org.martinmeer.otkassistant.core.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.model.sceletal.CalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.FetchedData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public abstract class CalculatedDataProcessor {

    @Setter
    protected Map<String, FetchedData> fetchedDataMap;
    public Map<String, CalculatedData> calculatedDataMap;


    public abstract Map<String, CalculatedData> genCalculatedDataMap(Map<String, FetchedData> fetchedDataMap);

}
