package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.MeasuringValuesGenerator;

import java.math.BigDecimal;

@Getter
public class MaxMeasuringValue implements MeasuringValuesGenerator<BigDecimal> {

    private BigDecimal maxMesValue; // must eliminate zeroes from decimal part
    private NominalDimension nominalDimension;
    private UpperDeviance upperDeviation;


    @Override
    public BigDecimal generateValue() {
        return null;
    }

    public String toString() {
        return maxMesValue.toString();
    }
}
