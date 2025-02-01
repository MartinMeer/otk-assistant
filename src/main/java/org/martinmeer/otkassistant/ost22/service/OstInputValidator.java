package org.martinmeer.otkassistant.ost22.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OstInputValidator {
 /*"Размер должен быть не менее 0.1 мм и не более 10000 мм"*/

    public boolean validate(BigDecimal nominalDimension) {
        BigDecimal lowerBound = new BigDecimal("0.1");
        BigDecimal upperBound = new BigDecimal("10000");
        return nominalDimension.compareTo(lowerBound) > 0 || nominalDimension.compareTo(upperBound) < 0;
    }
}
