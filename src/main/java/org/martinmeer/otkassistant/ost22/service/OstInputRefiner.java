package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.service.InputRefiner;
import org.martinmeer.otkassistant.ost22.model.OstNSpace;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class OstInputRefiner implements InputRefiner<OstNSpace, String> {
    //hole:20.2 // undef:50.3


    @Override
    public EnumMap<OstNSpace, String> generateDataMap(String input) {
        String typeOfDetail = (input.split(":"))[0];
        String nominalDimension = (input.split(":"))[1];
        EnumMap<OstNSpace, String> dataMap = new EnumMap<>(OstNSpace.class);
        dataMap.put(OstNSpace.TYPE_OF_DETAIL, typeOfDetail);
        dataMap.put(OstNSpace.NOM_DIMENSION, nominalDimension);
        return dataMap;
    }

    @Override
    public String normalize(String input) {
        return "";
    }
}
