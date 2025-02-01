package org.martinmeer.otkassistant.core.model;

import org.martinmeer.otkassistant.mthread.domain.MThrdNSpace;

import java.util.Map;

public interface InputConverter {
    Map<MThrdNSpace, String> generateDataMap();
    //void normalize(String input);


}
