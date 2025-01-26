package org.martinmeer.otkassistant.ost22.service;

import org.martinmeer.otkassistant.core.InputNormalizer;

public class OstInputNormalizer implements InputNormalizer {
    @Override
    public String normalize(String input) {
        return input
                .toLowerCase()
                .trim()
                .replace(",", ".");
    }
}
