package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.ost22.domain.OstInputDataMap;
import org.martinmeer.otkassistant.ost22.domain.OstNSpace;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class DeviancesIdentifier {





    public Map<OstNSpace, BigDecimal> getDeviances(OstInputDataMap ostInputDataMap) {
        String typeOfDetail = (String) dataMap.get(OstNSpace.TYPE_OF_DETAIL);
        BigDecimal nominalDimension = (BigDecimal) dataMap.get(OstNSpace.NOM_DIMENSION);

        //get values from DB according to typeOfDetail

        Map<OstNSpace, BigDecimal> deviances = new HashMap<>();
        return deviances;
    }
}
