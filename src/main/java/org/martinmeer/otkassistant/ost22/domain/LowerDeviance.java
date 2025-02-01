package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.GetValueFromDB;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
public class LowerDeviance implements GetValueFromDB<BigDecimal> {

    //MUST be < 0 for all deviances!
    private BigDecimal lowerDeviance;

    @Override
    public BigDecimal getValueFromDb() {
        return null;
    }
}
