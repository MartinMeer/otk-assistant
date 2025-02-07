package org.martinmeer.otkassistant.ost22.model;

import java.util.Arrays;

/**Contains field names from frontend, MUST be compared to*/
public enum OstNSpace {
    
    //From PUT request
    NOM_DIMENSION("nom_dimension"),
    TYPE_OF_DETAIL(""),

    //Fields from frontend selector
    UNDEF("undef"),
    HOLE("hole"),
    SHAFT("shaft"),
    QUASI_HOLE("quasi_hole"),
    QUASI_SHAFT("quasi_shaft"),

    //For response map
    DEVIANCE_VALUES("devValues"), //0, -0.5 || +0.5, 0
    UPPER_DEVIANCE(""),
    LOWER_DEVIANCE(""),
    MAX_MES_VALUE("max_mes_value"),
    MIN_MES_VALUE("min_mes_value");

    OstNSpace(String substr) {
        this.substr = substr;
    }

/**This method returns constant name by string value*/
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
