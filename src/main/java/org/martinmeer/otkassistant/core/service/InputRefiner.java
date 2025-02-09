package org.martinmeer.otkassistant.core.service;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public interface InputRefiner {

    String normalize(String input);

    public <E extends Enum<E>, V> EnumMap<E, V> generateDataMap(String input);


}
