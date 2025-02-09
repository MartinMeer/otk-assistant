package org.martinmeer.otkassistant.core.service;

import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public interface InputRefiner {

    String normalize(String input);

    <E extends Enum<E>, V> EnumMap<E, V> generateDataMap(String input);


}
