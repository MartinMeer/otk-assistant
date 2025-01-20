package org.martinmeer.otkassistant.params;

import lombok.Setter;
import org.martinmeer.otkassistant.utils.Namespace;

import java.util.Map;

public class DataMap {

    @Setter
    private Map<Namespace, String> inputMap;

    /*public DataMap(Map<Namespace, String> inputMap) {
        this.inputMap = inputMap;
    }
*/
    public String getParameter(Namespace paramName) {
        return inputMap.get(paramName);
    }
}
