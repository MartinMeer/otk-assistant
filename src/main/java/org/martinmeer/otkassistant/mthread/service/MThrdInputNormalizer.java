package org.martinmeer.otkassistant.mthread.service;

import org.martinmeer.otkassistant.core.InputNormalizer;

public class MThrdInputNormalizer implements InputNormalizer {

    @Override
    public String normalize(String input) {
        return input
                .toLowerCase()
                .replace(" ", "")
                //.replace(".", "")
                .replace(",", ".")
                .replace("х", "-")
                .replace("x", "-")
                .replace("*", "-")
                .replace("м", "m")
                .replace("р", "p")
                .replaceAll("p(\\d)", "-$1")
                .replace("н", "h")
                .replace("е", "e");
    }
}
