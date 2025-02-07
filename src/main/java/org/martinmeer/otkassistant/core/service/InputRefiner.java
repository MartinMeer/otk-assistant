package org.martinmeer.otkassistant.core.service;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public interface InputRefiner<E extends Enum<E>, V> {

    String normalize(String input);

    public EnumMap<E, V> generateDataMap(String input);


}
