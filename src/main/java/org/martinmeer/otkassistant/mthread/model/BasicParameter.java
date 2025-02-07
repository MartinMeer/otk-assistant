package org.martinmeer.otkassistant.mthread.model;

import lombok.Getter;

public class BasicParameter implements Parameter {

    @Getter
    private String outputValue;
    private Parameter parameter;
    private String sqlQuery;

    @Override
    public void getFromDb(String input) {


    }

    @Override
    public void setValue() {
        outputValue = null;
    }
}
