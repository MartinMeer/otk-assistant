package org.martinmeer.otkassistant.facets.service;

import org.martinmeer.otkassistant.core.model.CalculationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FacetCalculationService implements CalculationService {
    @Override
    public Map<String, String> generateOutput(String input) {
        return Map.of();
    }
}
