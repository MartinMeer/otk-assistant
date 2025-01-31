/*
package org.martinmeer.otkassistant.ost22.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.DataMap;
import org.martinmeer.otkassistant.ost22.domain.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Setter
@Component
public class OstOutputMapper extends DataMap<OstNSpace> {

    private NominalDimension nominalDimension;
    private UpperDeviance upperDeviance;
    private LowerDeviance lowerDeviance;
    private MaxMeasuringValue maxMeasuringValue;
    private MinMeasuringValue minMeasuringValue;


    */
/**если маппится аут для второго раздела,
     * метод должен добавлять в ключ единицу (minMesValue -> minMesValue1
     * или это на уровне апи?*//*

    public Map<String, String> generateOutputMap() {
        Map<String, String> outputMap = new HashMap<>();
        outputMap.put(OstNSpace.DEV_VALUES.toString(),
                upperDeviance.getUpperDeviance().toString()
                + ", "
                + lowerDeviance.getLowerDeviance().toString());
        outputMap.put(OstNSpace.MAX_MES_VALUE.toString(),
                maxMeasuringValue.getMaxMesValue().toString());
        outputMap.put(OstNSpace.MIN_MES_VALUE.toString(),
                minMeasuringValue.getMinMesValue().toString());

        return outputMap;
    }

}
*/
