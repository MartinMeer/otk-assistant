package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.InputNormalizer;
import org.springframework.stereotype.Service;


public class OstInputNormalizer implements InputNormalizer {
    @Override
    public String normalize(String input) {
        return input
                .toLowerCase()
                .trim()
                .replace(",", ".");
    }
}
