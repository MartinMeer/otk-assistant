package org.martinmeer.otkassistant.ost22.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class OstOutputMap {

    private String deviance_values;
    private String min_mes_value;
    private String max_mes_value;


    public void setOutput() {

    }

    public Map<String, String> getOutputMap(Map<String, Object> refinedValues, Map<String, Object> calculatedValues) {
        Map<String, String> outputMap = new HashMap<>();


        outputMap.put(deviance_values,
                refinedValues.get(OstNSpace.UPPER_DEVIANCE)
                        + ", "
                        + refinedValues.get(OstNSpace.LOWER_DEVIANCE));
        outputMap.put(OstNSpace.MAX_MES_VALUE.toString(),
                calculatedVaues.get(OstNSpace.MAX_MES_VALUE).toString());
        outputMap.put(OstNSpace.MIN_MES_VALUE.toString(),
                calculatedVaues.get(OstNSpace.MIN_MES_VALUE).toString());
        return outputMap;
    }


}
