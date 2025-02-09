package org.martinmeer.otkassistant.ost22.model;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.sceletal.InputData;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OstInputData extends InputData {

    private String detailType;


    @Override
    protected String normalize(String input) {
        return input.toLowerCase().trim();
    }

    @Override
    protected void refineData(String str) {
        String normalizedInput = normalize(str);
        detailType = (normalizedInput.split(":"))[0];
        inputDimension = (normalizedInput.split(":"))[1];
    }
}

