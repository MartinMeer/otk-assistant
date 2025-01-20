package org.martinmeer.otkassistant.io;

import org.martinmeer.otkassistant.utils.Namespace;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputConverter {

    private String input;
    private String normalized;

    /*public InputConverter(String input) {
        this.input = input;
        //normalized = normalize(input);
    }*/

    public void setInput(String input) {
        this.input = input;
    }

    public Map<Namespace, String> generateInputMap() {
        normalize(input);
        Map<Namespace, String> inputMap = new HashMap<>();
        inputMap.put(Namespace.DIRECTION, threadDirection(normalized));
        inputMap.put(Namespace.NOMINAL_SIZE, nominalSize(normalized));
        inputMap.put(Namespace.MULTISTART_TREAD, multistartThread(normalized));
        inputMap.put(Namespace.PITCH, pitch(normalized));
        inputMap.put(Namespace.TOLERANCE_ZONE, toleranceZone(normalized));
        return inputMap;
    }

    /** This method prepare input for mapping.*/
    private void normalize(String input) {
        normalized = input
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
/**
 * This method returns string representation of nominal thread size.
 */
    private String nominalSize(String normalized) {
        String[] splitInput = normalized.split("-");
        String str = splitInput[0];
        return str.substring(1);
    }
    private String multistartThread(String normalized) {
        Pattern pattern = Pattern.compile("ph\\d");
        Matcher matcher = pattern.matcher(normalized);
        if (matcher.find()) {
            return "многозаходная резьба";
        }
        return null;
    }

    //"  М2.25хРh3P0,45- 6е6G - LH"
    private String pitch(String normalized) {
        Pattern pattern = Pattern.compile("-(\\d+(?:\\.\\d+)?)-");
        Matcher matcher = pattern.matcher(normalized);
        String pitch = null;
        if (matcher.find()) {
            pitch = matcher.group(1);
            return pitch;
        }
        return pitch;
    }

    private String threadDirection(String normalized) {
        Pattern pattern = Pattern.compile("-lh");
        Matcher matcher = pattern.matcher(normalized);
        if (matcher.find()) {
            return "левая резьба";
        }
        return null;
    }

    private String toleranceZone(String normalized) {
        Pattern pattern = Pattern.compile("-((\\d[defgh])+)");
        Matcher matcher = pattern.matcher(normalized);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /*private String pitchNormalize(String pitch) {
        return (pitch.length() > 1)
                ? pitch.replaceAll(String.valueOf(pitch.charAt(0)), pitch.charAt(0) + ".")
                : pitch;
    }*/

}