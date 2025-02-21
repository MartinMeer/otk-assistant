package org.martinmeer.otkassistant.facets.service;

import org.martinmeer.otkassistant.core.service.MainService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FacetMainService implements MainService {


    @Override
    public Map<String, String> generateOutput(String page, String input) {
        return Map.of();
    }
}
