package org.martinmeer.otkassistant.ost22.service;

import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.service.MainService;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.martinmeer.otkassistant.ost22.model.OstInputData;
import org.martinmeer.otkassistant.ost22.model.OstSqlBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@FieldNameConstants
public class OstMainService implements MainService {

    private final OstSqlBuilder sqlBuilder;
    private final OstInputData inputData;
    private final SchemaAwareNamedParameterJdbcTemplate jdbcTemplate;
    private String sql;

    private String dimensionDefinition;
    private BigDecimal nominalDimension;


    private BigDecimal baseDeviance;
    private BigDecimal upperDeviance;
    private BigDecimal lowerDeviance;

    private BigDecimal maxMeasuringValue;
    private BigDecimal minMeasuringValue;


    public OstMainService(OstSqlBuilder sqlBuilder, OstInputData inputData, SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
        this.sqlBuilder = sqlBuilder;
        this.inputData = inputData;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Map<String, String> generateOutput(String page, String input) {
        jdbcTemplate.setSchemaName(page);
        inputData.createInputData(input);
        generateDefinedData(jdbcTemplate);
        return outputMapper();
    }


    private void generateDefinedData(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
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

    private void setNominalDimension(String baseDimension) {
        this.nominalDimension = new BigDecimal(baseDimension);
    }

    private void setDimensionDefinition() {
        dimensionDefinition = inputData.getDimensionDefinition();
    }

    private void generateSql() {
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


    private BigDecimal calculateMeasuringValues(BigDecimal dimension, BigDecimal deviance) {
        return dimension.add(deviance);
    }

    private Map<String, String> outputMapper() {
        Map<String, String> output = new HashMap<>();
        maxMeasuringValue = calculateMeasuringValues(baseDeviance, maxMeasuringValue);
        minMeasuringValue = calculateMeasuringValues(baseDeviance, minMeasuringValue);
        String devianceValues = lowerDeviance.toString()
                + upperDeviance.toString();
        output.put("deviance_values", devianceValues);
        output.put("max_mes_value", maxMeasuringValue.toString());
        output.put("min_mes_value", minMeasuringValue.toString());
        return output;
    }

}
