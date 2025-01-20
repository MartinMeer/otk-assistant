package org.martinmeer.otkassistant.engine;

import org.martinmeer.otkassistant.io.InputConverter;
import org.martinmeer.otkassistant.parameters.DataMap;

import java.util.Map;

public class BasicValueGen implements ValueGenerator{

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
