package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public abstract class AbstractInputData {

    protected String inputDimension;

    public AbstractInputData createInputData(String input) {
        refineData(normalize(input));
        return this;
    }

    protected abstract String normalize(String str);

    protected abstract void refineData(String input);



}
