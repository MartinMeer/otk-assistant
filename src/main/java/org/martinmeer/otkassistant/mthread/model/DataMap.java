package org.martinmeer.otkassistant.mthread.model;

import lombok.Setter;

import java.util.Map;

public class DataMap {

    @Setter
    private Map<MThrdNSpace, String> inputMap;

    /*public DataMap(Map<Namespace, String> inputMap) {
        this.inputMap = inputMap;
    }
*/
    public String getParameter(MThrdNSpace paramName) {
        return inputMap.get(paramName);
    }
}
