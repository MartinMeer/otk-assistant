package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.MeasuringValuesGenerator;

import java.math.BigDecimal;

@Getter
public class MinMeasuringValue implements MeasuringValuesGenerator<BigDecimal> {

    private BigDecimal minMesValue;
    private NominalDimension nominalDimension;
    private LowerDeviance lowerDeviance;

    @Override
    public BigDecimal generateValue() {
        return nominalDimension.getNominalDimension().add(lowerDeviance.getLowerDeviance());
    }
}
