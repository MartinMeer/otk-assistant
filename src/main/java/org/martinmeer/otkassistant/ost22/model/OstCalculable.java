package org.martinmeer.otkassistant.ost22.model;

import java.math.BigDecimal;

public interface OstCalculable {
    BigDecimal calculate(BigDecimal nominalDimension, BigDecimal deviance);
}
