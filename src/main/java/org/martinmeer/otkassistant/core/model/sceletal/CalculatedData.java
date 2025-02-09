package org.martinmeer.otkassistant.core.model.sceletal;

import java.util.Map;

public abstract class CalculatedData {

    private Map<String, Object> dataToCalculate;
    private Object calculatedValue;

    public abstract void calculate();
}
