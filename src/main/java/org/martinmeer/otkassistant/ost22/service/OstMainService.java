package org.martinmeer.otkassistant.ost22.service;

import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.service.MainService;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.martinmeer.otkassistant.ost22.model.OstInputData;
import org.martinmeer.otkassistant.ost22.model.OstSqlBuilder;
import org.martinmeer.otkassistant.ost22.web.InvalidScaleException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    void setNominalDimension(String baseDimension) {
        BigDecimal bigDecimal = new BigDecimal(baseDimension);
        // Проверяем, является ли число целым (дробная часть = 0)
        boolean isInteger = bigDecimal.compareTo(bigDecimal.setScale(0, RoundingMode.DOWN)) == 0;

        if (isInteger) {
            // Добавляем .0 для целых чисел
            this.nominalDimension = bigDecimal.setScale(1, RoundingMode.UNNECESSARY);
        }
        if (bigDecimal.scale() > 3) {
            throw new InvalidScaleException(
                    "Число должно содержать не более 3 знаков после точки. Введено: "
                            + baseDimension);
        }
        // Оставляем исходный масштаб для дробных
        this.nominalDimension = bigDecimal;
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
        Boolean testValue;
        //select exist (select 1 <<from ost22.def_deviances where nom_dim_range @> 20.0>>);

        String subquery = sql.substring(sql.toLowerCase().indexOf("from"));
        String validateSql = "SELECT EXISTS (SELECT 1 "
                + subquery
                + ");";
        Map<String, Object> params = Collections.singletonMap(":value", nominalDimension);
        testValue = jdbcTemplate.queryWithSchema(validateSql, params, Boolean.class);
        return testValue;
    }

    private void fetchBaseDeviance(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
        try {
            Map<String, Object> params = Collections.singletonMap(":value", nominalDimension);
            baseDeviance = jdbcTemplate.queryWithSchema(sql, params, BigDecimal.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("Проверьте правильность введенных данных");
        }
    }

    void setDeviances() {
        switch (dimensionDefinition) {
            case "hole", "quasi_hole" -> {
                upperDeviance = baseDeviance;
                lowerDeviance = BigDecimal.ZERO;
            }
            case "shaft", "quasi_shaft" -> {
                upperDeviance = BigDecimal.ZERO;
                lowerDeviance = baseDeviance.negate();
            }
            case "undef" -> {
                upperDeviance = baseDeviance;
                lowerDeviance = baseDeviance.negate();
            }
            default -> throw new RuntimeException("Проверьте правильность введенных данных");
        }
    }


    BigDecimal calculateMeasuringValues(BigDecimal dimension, BigDecimal deviance) {
        return dimension.add(deviance);
    }

    Map<String, String> outputMapper() {
        Map<String, String> output = new HashMap<>();
        maxMeasuringValue = calculateMeasuringValues(nominalDimension, upperDeviance);
        minMeasuringValue = calculateMeasuringValues(nominalDimension, lowerDeviance);

        output.put("upper_deviance", upperDeviance.toString());
        output.put("lower_deviance", lowerDeviance.toString());
        output.put("max_mes_value", maxMeasuringValue.toString());
        output.put("min_mes_value", minMeasuringValue.toString());
        return output;
    }

}
