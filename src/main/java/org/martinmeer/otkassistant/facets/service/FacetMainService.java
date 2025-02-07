package org.martinmeer.otkassistant.facets.service;

import org.martinmeer.otkassistant.core.service.MainService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FacetMainService extends MainService {
    @Override
    public Map<String, String> generateOutput(String input) {
        return Map.of();
    }
}
