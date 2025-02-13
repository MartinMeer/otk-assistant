package org.martinmeer.otkassistant.ost22.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OstDevianceDefiner {

    private final OstInputData ostInputData;
    private final OstSqlBuilder ostSqlBuilder;

    //private String definition;
    private BigDecimal nominalDimension;


    private BigDecimal upperDeviance;
    private BigDecimal lowerDeviance;

    private String sql;

    public OstDevianceDefiner(OstInputData ostInputData, OstSqlBuilder ostSqlBuilder) {
        this.ostInputData = ostInputData;
        this.ostSqlBuilder = ostSqlBuilder;
    }

    public void defineDeviances() {
        setNominalDimension(ostInputData.getInputDimension());
        generateSql();
        if (validate(nominalDimension)) {
            setDeviances();
        } else {
            throw new RuntimeException("Проверьте правильность введенных данных");
        }
    }

    private void setNominalDimension(String baseDimension) {
        this.nominalDimension = new BigDecimal(baseDimension);
    }

    private void generateSql() {
        if (ostInputData.getDimensionDefinition().equals("undef")) {
            ostSqlBuilder.setTable("undef_deviances");
            ostSqlBuilder.setDefinedColumn("deviance");
            ostSqlBuilder.setWhereColumn("dim_range");
        } else {
            ostSqlBuilder.setTable("def_deviances");
            ostSqlBuilder.setWhereColumn("nom_dim_range");
            switch (ostInputData.getDimensionDefinition()) {
                case "hole" -> ostSqlBuilder.setDefinedColumn("hole");
                case "shaft" -> ostSqlBuilder.setDefinedColumn("shaft");
                case "quasi_hole" -> ostSqlBuilder.setDefinedColumn("quasi_hole");
                case "quasi_shaft" -> ostSqlBuilder.setDefinedColumn("quasi_shaft");
                default -> throw new RuntimeException("Проверьте правильность типа введенных данных");
            }
        }
        this.sql = ostSqlBuilder.buildSelectSql();
    }

    private void setDeviances() {
        upperDeviance = null;
        lowerDeviance = null;
    }

    private boolean validate(BigDecimal nominalDimension) {
        String validateSql = "SELECT EXIST (" +
                sql.substring(sql.lastIndexOf(sql)) +
                ");";
        return true;
    }


}
