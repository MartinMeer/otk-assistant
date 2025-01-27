package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.GetValueFromDB;

import java.math.BigDecimal;

public class NominalDimension implements GetValueFromDB<BigDecimal> {

    @Getter
    private BigDecimal nominalDimension;
    private

    @Override
    public BigDecimal getValueFromDb() {
        return null;
    }
}
