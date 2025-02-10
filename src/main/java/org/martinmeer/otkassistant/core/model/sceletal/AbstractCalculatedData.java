package org.martinmeer.otkassistant.core.model.sceletal;

import java.util.Map;

public abstract class AbstractCalculatedData {

    private Map<String, Object> dataToCalculate;
    private Object calculatedValue;

    public abstract void calculate();
}
