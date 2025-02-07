package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.ost22.domain.OstInputDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OstInputValidator {

    /*"Размер должен быть не менее 0.1 мм и не более 10000 мм"*/
    private BigDecimal nominalDimension;
    private final OstInputDataMap ostInputDataMap;

    @Autowired
    public OstInputValidator(OstInputDataMap ostInputDataMap) {
        this.ostInputDataMap = ostInputDataMap;
    }

    public boolean validate() {
        nominalDimension = ostInputDataMap.getNominalDimension();
        BigDecimal lowerBound = new BigDecimal("0.1");
        BigDecimal upperBound = new BigDecimal("10000");
        return nominalDimension.compareTo(lowerBound) > 0 || nominalDimension.compareTo(upperBound) < 0;
    }
}
