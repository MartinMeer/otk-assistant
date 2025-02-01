package org.martinmeer.otkassistant.core.model;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CalculationService {

    Map<String, String> generateOutput(String input);
}
