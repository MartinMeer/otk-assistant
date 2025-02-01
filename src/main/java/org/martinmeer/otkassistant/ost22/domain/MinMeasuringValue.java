package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.MeasuringValuesGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
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
