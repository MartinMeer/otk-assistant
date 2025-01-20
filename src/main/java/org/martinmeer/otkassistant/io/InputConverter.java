package org.martinmeer.otkassistant.io;

import org.martinmeer.otkassistant.utils.Namespace;

import java.util.Map;

public interface InputConverter {
    Map<Namespace, String> generateDataMap();
    //void normalize(String input);


}
