package org.martinmeer.otkassistant.mthread.model;

public enum MThrdNSpace {


    PITCH_DIAM_TOLERANCE("", ""),
    NOM_DIAM_TOLERANCE("nom_diam_tolerances", "nom_diam_tolerance"),
    PITCH("pitches", "pitch"),
    MULTISTART_TREAD(),
    TOLERANCE_ZONE(),
    DEVIATION("basic_deviation", "deviation"),
    DIRECTION(),
    NOM_DIAMETER("nom_diams", "nom_diam"),
    PITCH_DIAMETER(),
    DB_QUERY(),
    DB_ALIAS(),
    SQL_PARAMETER();

    MThrdNSpace(String dbTable, String dbColumn) {
        this.dbTable = dbTable;
        this.dbColumn = dbColumn;
    }

    MThrdNSpace() {
        this.dbTable = "";
        this.dbColumn = "";
    }

    /*public static MThrdNSpace valueFrom(String substr) {
        return Arrays.stream(MThrdNSpace.values())
                .filter(e -> e.substr.equals(substr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No OstNSpace enum constant with substr: " + substr
                ));
    }*/

    public String getDbTable() {
        return dbTable;
    }
    public String getDbColumn() {
        return dbColumn;
    }

    private final String dbTable;
    private final String dbColumn;
}
