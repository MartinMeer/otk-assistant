package org.martinmeer.otkassistant.ost22.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.model.InputConverter;
import org.martinmeer.otkassistant.core.model.InputNormalizer;
import org.martinmeer.otkassistant.mthread.domain.MThrdNSpace;
import org.martinmeer.otkassistant.ost22.domain.OstNSpace;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class OstInputConverter{
    //hole:20.2 // undef:50.3


        public Map<OstNSpace, Object> convertInput(String input) {
            String typeOfDetail = (input.split(":"))[0];
            String nominalDimensionStr = (input.split(":"))[1];

            BigDecimal nominalDimension = new BigDecimal(nominalDimensionStr);

            Map<OstNSpace, Object> dataMap = new HashMap<>();
            dataMap.put(OstNSpace.TYPE_OF_DETAIL, typeOfDetail);
            dataMap.put(OstNSpace.NOM_DIMENSION, nominalDimension);

        return convertedInput;
    }
}
