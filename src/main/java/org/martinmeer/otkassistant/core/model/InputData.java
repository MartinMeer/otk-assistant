package org.martinmeer.otkassistant.core.model;

import org.martinmeer.otkassistant.core.service.InputRefiner;
import org.martinmeer.otkassistant.ost22.model.OstNSpace;
import org.springframework.stereotype.Component;

import java.util.EnumMap;


public abstract class InputData implements InputRefiner {

    private String nominalDimension;
    private final EnumMap<OstNSpace, String> inputDataMap;

    public InputData(EnumMap<OstNSpace, String> inputDataMap) {
        this.inputDataMap = inputDataMap;
    }
}
