package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.ost22.domain.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OstMeasuringValuesGen implements org.martinmeer.otkassistant.core.MeasuringValuesGen {

    private NominalDimension nominalDimension;
    private UpperDeviation upperDeviation;
    private LowerDeviation lowerDeviation;


    /**Метод получает:
     * - значение размера,
     * - значения верхнего и нижнего отклонений(из БД),
     * - вычисляет данные для вычислений
     * - формирует мапу для сериализации*/

    @Override
    public Map<OstNSpace, String> generateValuesMap() {
        Map<OstNSpace, String> valuesMap = new HashMap<>();
        BigDecimal nominal = nominalDimension.getNominalDimension();
        String deviationValues
                = upperDeviation.getUpperDeviation().toString()
                + "|"
                + lowerDeviation.getLowerDeviation().toString();

        valuesMap.put(OstNSpace.DEV_VALUES, deviationValues);
        valuesMap.put(OstNSpace.MAX_MES_VALUE, calculate(
                nominal,
                upperDeviation.getUpperDeviation()));
        valuesMap.put(OstNSpace.MIN_MES_VALUE, calculate(
                nominal,
                lowerDeviation.getLowerDeviation()));
        return Map.of();
    }

    @Override
    public String calculate(BigDecimal nominal, BigDecimal deviation) {
        BigDecimal result = nominal.add(deviation);
        return result.toString();
    }
}
