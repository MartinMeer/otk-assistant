package org.martinmeer.otkassistant.core;

import java.math.BigDecimal;
import java.util.Map;

public interface MeasuringValuesGen<T extends Enum<T>> {

    Map<T, String> generateValuesMap();
    String calculate(BigDecimal nominal, BigDecimal deviation);
}
