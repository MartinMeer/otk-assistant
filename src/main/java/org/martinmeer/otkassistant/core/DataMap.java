package org.martinmeer.otkassistant.core;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class DataMap<T extends Enum<T>> {
    @Setter
    private Map<T, String> dataMap; // Инициализация мапы

    public Map<String, String> generateOutputData() {
        return dataMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(), // Используем метод name()
                        Map.Entry::getValue
                ));
    }
}