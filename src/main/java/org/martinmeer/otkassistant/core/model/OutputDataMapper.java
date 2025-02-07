package org.martinmeer.otkassistant.core.model;

import org.martinmeer.otkassistant.core.service.CalculatedDataProcessor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public abstract class OutputDataMapper<E extends Enum<E>, V> {

private ComparedData comparedData;
private CalculatedDataProcessor calculatedDataProcessor;

public abstract EnumMap<E, V> generateOutputData();

}
