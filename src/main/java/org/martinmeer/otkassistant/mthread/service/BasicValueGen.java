package org.martinmeer.otkassistant.mthread.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.model.InputConverter;
import org.martinmeer.otkassistant.core.model.ValueGenerator;
import org.martinmeer.otkassistant.mthread.domain.DataMap;

import java.util.Map;

@Setter
public class BasicValueGen implements ValueGenerator {

    private String input;
    private InputConverter inputConverter;
    private DataMap dataMap;
    private Map sqlQueries;
    private Map outputValues;

    @Override
    public void generateValue() {

    }

    @Override
    public Map generateOutput() {
        return null;
    }

    private void parseQueriesMap() {

    }
}
