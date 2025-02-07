/*
package org.martinmeer.otkassistant.mthread.service;

import lombok.Setter;
import org.martinmeer.otkassistant.core.service.InputRefiner;
import org.martinmeer.otkassistant.core.model.InputNormalizer;
import org.martinmeer.otkassistant.mthread.domain.MThrdNSpace;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Setter
public class MThrdInputRefiner implements InputRefiner {

    private String input;

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

    @Override
    public Map<MThrdNSpace, String> generateDataMap() {

        String normalized = normalize(input);
        Map<MThrdNSpace, String> inputMap = new HashMap<>();
        inputMap.put(MThrdNSpace.DIRECTION, threadDirection(normalized));
        inputMap.put(MThrdNSpace.NOM_DIAMETER, nominalSize(normalized));
        inputMap.put(MThrdNSpace.MULTISTART_TREAD, multistartThread(normalized));
        inputMap.put(MThrdNSpace.PITCH, pitch(normalized));
        inputMap.put(MThrdNSpace.TOLERANCE_ZONE, toleranceZone(normalized));
        return inputMap;
    }
*/
/**
 * This method returns string representation of nominal thread size.
 *//*

    private String nominalSize(String normalized) {
        String[] splitInput = normalized.split("-");
        String str = splitInput[0];
        return str.substring(1);
    }

    */
/**
     * This method returns string representation of multistart thread.
     *//*

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

    */
/*private String pitchNormalize(String pitch) {
        return (pitch.length() > 1)
                ? pitch.replaceAll(String.valueOf(pitch.charAt(0)), pitch.charAt(0) + ".")
                : pitch;
    }*//*


}*/
