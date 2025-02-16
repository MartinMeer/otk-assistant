package org.martinmeer.otkassistant.ost22.model;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractDefinedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.martinmeer.otkassistant.core.service.ValueDefiner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component

@FieldNameConstants
@Getter
public class OstDefinedData extends AbstractDefinedData implements ValueDefiner<BigDecimal> {

    private final OstSqlBuilder sqlBuilder;

    private String dimensionDefinition;
    private BigDecimal nominalDimension;


    //private BigDecimal baseDeviance;


    //private BigDecimal upperDeviance;
    //private BigDecimal lowerDeviance;

    private BigDecimal maxMeasuringValue;
    private BigDecimal minMeasuringValue;

    private String sql;

    public OstDefinedData(@Qualifier("ostInputData") AbstractInputData inputData, OstSqlBuilder sqlBuilder) {
        super(inputData);
        this.sqlBuilder = sqlBuilder;
    }

    /*@Override

    public Map<String, BigDecimal> generateDataForOutput() {
        Map<String, BigDecimal> definedValues = new HashMap<>();
        definedValues.put(Fields.nominalDimension, nominalDimension);
        definedValues.put(Fields.upperDeviance, upperDeviance);
        definedValues.put(Fields.lowerDeviance, lowerDeviance);
        definedValues.put(Fields.maxMeasuringValue, calculateMeasuringValues(nominalDimension,upperDeviance));
        definedValues.put(Fields.minMeasuringValue, calculateMeasuringValues(nominalDimension, lowerDeviance));
        return definedValues;
    }*/


    @Override
    @Transactional
    public void generateDefinedData(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
        setNominalDimension(inputData.getInputDimension());
        setDimensionDefinition();
        generateSql();
        if (validate(jdbcTemplate)) {
            fetchBaseDeviance(jdbcTemplate);
            setDeviances();
        } else {
            throw new RuntimeException("Проверьте правильность введенных данных");
        }
    }

    private void setDimensionDefinition() {
        dimensionDefinition = inputData.getDimensionDefinition();
    }

    private void setDeviances() {
        switch (dimensionDefinition) {
            case "hole", "quasi_hole" -> {
                upperDeviance = baseDeviance;
                lowerDeviance = new BigDecimal(0);
            }
            case "shaft", "quasi_shaft" -> {
                upperDeviance = new BigDecimal(0);
                lowerDeviance = baseDeviance.negate();
            }
            case "undef" -> {
                upperDeviance = baseDeviance;
                lowerDeviance = baseDeviance.negate();
            }
            default -> throw new RuntimeException("Проверьте правильность введенных данных");
        }
    }

    private void setNominalDimension(String baseDimension) {
        this.nominalDimension = new BigDecimal(baseDimension);
    }

    //Must be private
    public void generateSql() {
        if (dimensionDefinition.equals("undef")) {
            sqlBuilder.setTable("undef_deviances");
            sqlBuilder.setDefinedColumn("deviance");
            sqlBuilder.setWhereColumn("dim_range");
        } else {
            sqlBuilder.setTable("def_deviances");
            sqlBuilder.setWhereColumn("nom_dim_range");
            switch (dimensionDefinition) {
                case "hole" -> sqlBuilder.setDefinedColumn("hole");
                case "shaft" -> sqlBuilder.setDefinedColumn("shaft");
                case "quasi_hole" -> sqlBuilder.setDefinedColumn("quasi_hole");
                case "quasi_shaft" -> sqlBuilder.setDefinedColumn("quasi_shaft");
                default -> throw new RuntimeException("Проверьте правильность типа введенных данных");
            }
        }
        this.sql = sqlBuilder.buildSelectSql();
    }

    private boolean validate(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
        BigDecimal testValue;
        String validateSql = "SELECT EXIST (" +
                sql.substring(sql.lastIndexOf(sql)) +
                ");";
        Map<String, Object> params = Collections.singletonMap(":value", nominalDimension);
        testValue = jdbcTemplate.queryWithSchema(validateSql, params, BigDecimal.class);
        return testValue != null;
    }

    private void fetchBaseDeviance(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
        try {
            Map<String, Object> params = Collections.singletonMap(":value", nominalDimension);
            baseDeviance = jdbcTemplate.queryWithSchema(sql, params, BigDecimal.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("Проверьте правильность введенных данных");
        }
    }

    private BigDecimal calculateMeasuringValues(BigDecimal dimension, BigDecimal deviance) {
        return dimension.add(deviance);
    }
}
