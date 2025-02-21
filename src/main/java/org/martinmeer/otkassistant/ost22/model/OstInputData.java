package org.martinmeer.otkassistant.ost22.model;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OstInputData extends AbstractInputData {

    protected String TEST;


    @Override
    protected String normalize(String input) {
        return input.toLowerCase().trim();
    }

    @Override
    protected void refineData(String str) {
        String normalizedInput = normalize(str);
        dimensionDefinition = (normalizedInput.split(":"))[0];
        inputDimension = (normalizedInput.split(":"))[1];
    }
}

