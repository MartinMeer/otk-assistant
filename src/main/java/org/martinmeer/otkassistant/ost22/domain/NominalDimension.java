package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.GetValueFromDB;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
public class NominalDimension implements GetValueFromDB<BigDecimal> {

    private BigDecimal nominalDimension;


    @Override
    public BigDecimal getValueFromDb() {
        return null;
    }
}
