package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Getter;

@Getter
public abstract class AbstractInputData {

    protected String inputDimension;
    protected String dimensionDefinition;

    public AbstractInputData createInputData(String input) {
        refineData(normalize(input));
        return this;
    }

    protected abstract String normalize(String str);

    protected abstract void refineData(String input);
}
