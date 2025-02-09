package org.martinmeer.otkassistant.ost22.model;

import org.martinmeer.otkassistant.core.model.InputData;

import java.util.EnumMap;

public class OstInputData extends InputData {

    private String detailType;

    public OstInputData(EnumMap<OstNSpace, String> generateDataMap) {
        super(input);
    }

    @Override
    public String normalize(String input) {
        return "";
    }

    @Override
    public <E extends Enum<E>, V> EnumMap<E, V> generateDataMap(String input) {
        return null;
    }
}
