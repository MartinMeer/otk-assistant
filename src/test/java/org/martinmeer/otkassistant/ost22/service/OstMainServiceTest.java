package org.martinmeer.otkassistant.ost22.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.martinmeer.otkassistant.ost22.model.OstInputData;
import org.martinmeer.otkassistant.ost22.model.OstSqlBuilder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OstMainServiceTest {

    @Mock
    private OstSqlBuilder sqlBuilder;

    @Mock
    private OstInputData inputData;

    @Mock
    private SchemaAwareNamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private OstMainService ostMainService;

    private static Method method;
    //private static List<Field> fieldsList;

    private static Field nominalDimensionField;
    private static Field dimensionDefinitionField;
    private static Field upperDevianceField;
    private static Field lowerDevianceField;

    @BeforeEach
    void setUp() throws NoSuchMethodException, NoSuchFieldException {
        // Access private method via reflection
        method = OstMainService.class.getDeclaredMethod(
                "generateDefinedData",
                SchemaAwareNamedParameterJdbcTemplate.class);
        method.setAccessible(true);
        nominalDimensionField = getField(OstMainService.class, "nominalDimension");
        dimensionDefinitionField = getField(OstMainService.class, "dimensionDefinition");
        upperDevianceField = getField(OstMainService.class, "upperDeviance");
        lowerDevianceField = getField(OstMainService.class, "lowerDeviance");
        String inputDimension = "20.0";
        lenient().when(inputData.getInputDimension()).thenReturn(inputDimension);

    }

    @Test
    void generateDefinedDataValidHoleDimensionSetsCorrectValues() throws Exception {
        // Arrange
        when(inputData.getDimensionDefinition())
                .thenReturn("hole");
        when(sqlBuilder.buildSelectSql())
                .thenReturn("SELECT hole FROM def_deviances WHERE nom_dim_range @> :value");
        when(jdbcTemplate.queryWithSchema(anyString(),
                anyMap(),
                eq(Boolean.class)))
                .thenReturn(true);
        when(jdbcTemplate.queryWithSchema(anyString(),
                anyMap(),
                eq(BigDecimal.class)))
                .thenReturn(new BigDecimal("0.05"));

        // Act
        method.invoke(ostMainService, jdbcTemplate);

        // Assert
        // Verify field values
        assertFieldValues(
                new BigDecimal("20.0"),
                "hole",
                new BigDecimal("0.05"),
                BigDecimal.ZERO);

        // Verify SQL builder interactions
        verify(sqlBuilder).setTable("def_deviances");
        verify(sqlBuilder).setDefinedColumn("hole");
        verify(sqlBuilder).setWhereColumn("nom_dim_range");
        verify(sqlBuilder).buildSelectSql();

        // Verify JDBC interactions
        verify(jdbcTemplate).queryWithSchema(
                contains("SELECT EXISTS (SELECT 1 FROM def_deviances WHERE nom_dim_range @> :value)"),
                anyMap(),
                eq(Boolean.class)
        );
        verify(jdbcTemplate).queryWithSchema(
                eq("SELECT hole FROM def_deviances WHERE nom_dim_range @> :value"),
                anyMap(),
                eq(BigDecimal.class)
        );
    }

    private void assertFieldValues(
            BigDecimal expectedNominal,
            String expectedDefinition,
            BigDecimal expectedUpper,
            BigDecimal expectedLower
    ) throws IllegalAccessException {
        assertEquals(expectedNominal, nominalDimensionField.get(ostMainService));
        assertEquals(expectedDefinition, dimensionDefinitionField.get(ostMainService));
        assertEquals(expectedUpper, upperDevianceField.get(ostMainService));
        assertEquals(expectedLower, lowerDevianceField.get(ostMainService));
    }

    @Test
    void generateDefinedDataInvalidDimensionThrowsException() throws Exception {
        // Arrange
        when(inputData.getDimensionDefinition()).thenReturn("invalid_type");

        // Act & Assert
        Exception exception = assertThrows(Exception.class,
                () -> method.invoke(ostMainService, jdbcTemplate));
        assertTrue(exception
                .getCause()
                .getMessage()
                .contains("Проверьте правильность типа введенных данных"));
    }

    @Test
    void generateDefinedDataValidationFailedThrowsException() throws Exception {
        // Arrange
        when(inputData.getDimensionDefinition()).thenReturn("hole");
        when(sqlBuilder.buildSelectSql())
                .thenReturn("SELECT hole FROM def_deviances WHERE nom_dim_range @> :value");
        when(jdbcTemplate.queryWithSchema(anyString(),
                anyMap(),
                eq(Boolean.class))).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(Exception.class,
                () -> method.invoke(ostMainService, jdbcTemplate));
        assertTrue(exception
                .getCause()
                .getMessage()
                .contains("Проверьте правильность введенных данных"));
    }

    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    @Test
    void outputMapperReturnsCorrectValues() throws IllegalAccessException {
        // Arrange: Set required fields using reflection
        nominalDimensionField.set(ostMainService, new BigDecimal("20.0"));
        upperDevianceField.set(ostMainService, new BigDecimal("0.05"));
        lowerDevianceField.set(ostMainService, new BigDecimal("-0.05"));

// Act
        Map<String, String> result = ostMainService.outputMapper();

        // Assert
        assertAll(
                () -> assertEquals("0.05", result.get("upper_deviance")),
                () -> assertEquals("-0.05", result.get("lower_deviance")),
                () -> assertEquals("20.05", result.get("max_mes_value")),
                () -> assertEquals("19.95", result.get("min_mes_value"))
        );
    }
}
