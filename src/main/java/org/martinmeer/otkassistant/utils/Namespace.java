package org.martinmeer.otkassistant.utils;

public enum Namespace {
    PITCHES(),
    DEVIATIONS("", ""),
    TOLERANCES_d("", ""),
    PITCH_DIAM_TOLERANCE("", ""),
    NOM_DIAM_TOLERANCE("nom_diam_tolerances", "nom_diam_tolerance"),
    PITCH("pitches", "pitch"),
    MULTISTART_TREAD(),
    TOLERANCE_ZONE(),
    DEVIATION("basic_deviation", "deviation"),
    DIRECTION(),
    NOMINAL_SIZE("nom_diams", "nom_diam"),
    PITCH_DIAMETER(),
    DB_QUERY(),
    DB_ALIAS(),
    NOM_DIAMETER(),
    PARAMETER(),
    DEFAULT("", "");

    Namespace(String dbTable, String dbColumn) {
        this.dbTable = dbTable;
        this.dbColumn = dbColumn;
    }

    Namespace() {
        this.dbTable = "";
        this.dbColumn = "";
    }

    public String getDbTable() {
        return dbTable;
    }

    public String getDbColumn() {
        return dbColumn;
    }

    private final String dbTable;
    private final String dbColumn;
}
