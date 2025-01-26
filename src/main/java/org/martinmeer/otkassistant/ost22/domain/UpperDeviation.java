package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;

import java.math.BigDecimal;

public class UpperDeviation {

    //MUST be > 0 for unspec_deviations!
    @Getter
    private BigDecimal upperDeviation;
}
