package org.martinmeer.otkassistant.core.model.sceletal;

import java.util.Map;

public abstract class AbstractOutputData<T> {

    private final AbstractDefinedData<T> definedData;
    private final AbstractCalculatedData<T> calculatedData;

    public AbstractOutputData(AbstractDefinedData<T> definedData, AbstractCalculatedData<T> calculatedData) {
        this.definedData = definedData;
        this.calculatedData = calculatedData;
    }

    public abstract Map<String, String> generateOutput();
}
