package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.GetValueFromDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
public class DevianceValues implements GetValueFromDB<BigDecimal> {

    //MUST be < 0 for all deviances!
    private BigDecimal upperDeviance;
    private BigDecimal lowerDeviance;
    private String sqlQuery;
    private final NominalDimension nominalDimension;

    @Autowired
    public DevianceValues(NominalDimension nominalDimension) {
        this.nominalDimension = nominalDimension;
    }

    @Override
    public BigDecimal getValueFromDb() {


        return null;
    }
}
