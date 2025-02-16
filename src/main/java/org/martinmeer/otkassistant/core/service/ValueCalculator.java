package org.martinmeer.otkassistant.core.service;

import org.martinmeer.otkassistant.core.model.sceletal.AbstractDefinedData;

import java.math.BigDecimal;
import java.util.Map;

public interface ValueCalculator<T> {

    //Map<String, T> getCalculatedValues();

    void generateCalculatedData(AbstractDefinedData<T> definedData);
}
