package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.model.InputNormalizer;
import org.springframework.stereotype.Component;


public class OstInputNormalizer implements InputNormalizer {
    @Override
    public String normalize(String input) {
        return input
                .toLowerCase()
                .trim()
                .replace(",", ".");
    }
}
