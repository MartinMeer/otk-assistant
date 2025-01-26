package org.martinmeer.otkassistant.ost22.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.InputConverter;
import org.martinmeer.otkassistant.core.InputNormalizer;
import org.martinmeer.otkassistant.mthread.domain.MThrdNSpace;
import org.martinmeer.otkassistant.ost22.domain.OstNSpace;

import java.util.HashMap;
import java.util.Map;

@Setter
public class OstInputConverter implements InputConverter {
    private String input; //hole:20.2 // undef:50.3

    @Override
    public Map<MThrdNSpace, String> generateDataMap() {
        InputNormalizer inputNormalizer = new OstInputNormalizer();
        String normalized = inputNormalizer.normalize(input);
        String key = (input.split(":"))[0];
        String value = (input.split(":"))[1];
        Map<OstNSpace, String> inputMap = new HashMap<>();
        inputMap.put(OstNSpace.valueFromEnum(key), value);
        return Map.of();
    }
}
