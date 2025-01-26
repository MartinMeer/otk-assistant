package org.martinmeer.otkassistant.core;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
public abstract class DataMap<T extends Enum<T>> {

    private Map<T, BigDecimal> inputMap;

    private Map<T, String> dataMap;
    private Map<String, String> outputMap;

     // Инициализация мапы


    public Map<T, String> crDataMap(Map<T, String> dataMap) {
        return inputMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue

                ));
    }

    public Map<String, String> generateOutputData() {
        Map<String, String> outputMap = dataMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(), // Используем метод name()
                        Map.Entry::getValue));
        return outputMap;
    }
}