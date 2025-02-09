package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public abstract class InputData {

    protected String inputDimension;

    public InputData createInputData(String input) {
        refineData(normalize(input));
        return this;
    }

    protected abstract String normalize(String str);

    protected abstract void refineData(String input);



}
