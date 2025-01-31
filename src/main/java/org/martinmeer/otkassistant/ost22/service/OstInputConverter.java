package org.martinmeer.otkassistant.ost22.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.InputConverter;
import org.martinmeer.otkassistant.core.InputNormalizer;
import org.martinmeer.otkassistant.mthread.domain.MThrdNSpace;
import org.martinmeer.otkassistant.ost22.domain.OstNSpace;
import org.martinmeer.otkassistant.ost22.model.OstStringHttpRequest;

import java.util.HashMap;
import java.util.Map;

@Setter
public class OstInputConverter implements InputConverter {
    private OstStringHttpRequest ostStringHttpRequest; //hole:20.2 // undef:50.3
    private InputNormalizer inputNormalizer; //OstInputNormalizer

    @Override
    public Map<MThrdNSpace, String> generateDataMap() {
        String input = ostStringHttpRequest.getInputString();
        String normalized = inputNormalizer.normalize(input);
        String key = (input.split(":"))[0];
        String value = (input.split(":"))[1];
        Map<OstNSpace, String> inputMap = new HashMap<>();
        inputMap.put(OstNSpace.valueFromEnum(key), value);
        return Map.of();
    }
}
