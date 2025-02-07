package org.martinmeer.otkassistant.ost22.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Component
public class OstInputDataMap {

    //private String input;
    private String elementType;
    private BigDecimal nominalDimension;


    /*public OstInputDataMap(String input) {
        this.input = input;
    }*/

    public void setInputDataMap(String input) {
        this.elementType = (input.split(":"))[0];
        String nominalDimensionStr = (input.split(":"))[1];
        this.nominalDimension = new BigDecimal(nominalDimensionStr);
    }
}
