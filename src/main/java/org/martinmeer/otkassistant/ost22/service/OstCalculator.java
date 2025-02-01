package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.ost22.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component

public class OstCalculator {


    public Map<OstNSpace, BigDecimal> calculateMeasuringValues(BigDecimal nominalDimension, Map<OstNSpace, BigDecimal> deviances) {
        Map<OstNSpace, BigDecimal> measuringValues = new HashMap<>();
        BigDecimal maxMeasuringValue = nominalDimension.add(deviances.get(OstNSpace.UPPER_DEVIANCE));
        BigDecimal minMeasuringValue = nominalDimension.add(deviances.get(OstNSpace.LOWER_DEVIANCE));
        measuringValues.put(OstNSpace.MAX_MES_VALUE, maxMeasuringValue);
        measuringValues.put(OstNSpace.MIN_MES_VALUE, minMeasuringValue);
        return measuringValues;
    }
}
