package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.GetValueFromDB;

import java.math.BigDecimal;

@Getter
public class NominalDimension implements GetValueFromDB<BigDecimal> {

    private BigDecimal nominalDimension;


    @Override
    public BigDecimal getValueFromDb() {
        return null;
    }
}
