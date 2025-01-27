package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.GetValueFromDB;

import java.math.BigDecimal;

@Getter
public class UpperDeviance implements GetValueFromDB<BigDecimal> {

    //MUST be > 0 for unspec_deviations!

    private BigDecimal upperDeviance;

    @Override
    public BigDecimal getValueFromDb() {
        return null;
    }
}
