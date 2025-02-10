package org.martinmeer.otkassistant.core.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractCalculatedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public abstract class CalculatedDataProcessor {

    @Setter
    protected Map<String, AbstractFetchedData> fetchedDataMap;
    public Map<String, AbstractCalculatedData> calculatedDataMap;


    public abstract Map<String, AbstractCalculatedData> genCalculatedDataMap(Map<String, AbstractFetchedData> fetchedDataMap);

}
