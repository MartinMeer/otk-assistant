package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;

import java.math.BigDecimal;

public class LowerDeviation {

    //MUST be < 0 for all deviations!
    @Getter
    private BigDecimal lowerDeviation;
}
