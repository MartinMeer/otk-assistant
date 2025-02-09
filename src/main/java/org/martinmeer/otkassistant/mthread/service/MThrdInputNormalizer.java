package org.martinmeer.otkassistant.mthread.service;

public class MThrdInputNormalizer {


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
