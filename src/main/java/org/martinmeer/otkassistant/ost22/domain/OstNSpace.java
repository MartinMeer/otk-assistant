package org.martinmeer.otkassistant.ost22.domain;

import java.util.Arrays;

public enum OstNSpace {
    NOM_DIMENSION("nom_dimension"),
    UNDEF("undef"),
    HOLE("hole"),
    SHAFT("shaft"),
    QUASI_HOLE("quasi_hole"),
    QUASI_SHAFT("quasi_shaft"),
    DEV_VALUES("devValues"), //0, -0.5 || +0.5, 0
    MAX_MES_VALUE("maxMesValue"),
    MIN_MES_VALUE("minMesValue");

    OstNSpace(String substr) {
        this.substr = substr;
    }

    public static OstNSpace valueFromEnum(String substr) {
        return Arrays.stream(OstNSpace.values())
                .filter(e -> e.substr.equals(substr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No OstNSpace enum constant with substr: " + substr
                ));
    }

    public String toString() {
        return name().toLowerCase();
    }

    private final String substr;
}
