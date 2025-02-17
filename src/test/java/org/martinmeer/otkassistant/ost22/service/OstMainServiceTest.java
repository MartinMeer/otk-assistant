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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    void seUp() throws NoSuchMethodException, NoSuchFieldException {
        // Access private method via reflection
        method = OstMainService.class.getDeclaredMethod("generateDefinedData", SchemaAwareNamedParameterJdbcTemplate.class);
        method.setAccessible(true);
        nominalDimensionField = getField(OstMainService.class, "nominalDimension");
        dimensionDefinitionField = getField(OstMainService.class, "dimensionDefinition");
        upperDevianceField = getField(OstMainService.class, "upperDeviance");
        lowerDevianceField = getField(OstMainService.class, "lowerDeviance");
        String inputDimension = "20.0";
        when(inputData.getInputDimension()).thenReturn(inputDimension);
    }

    @Test
    void generateDefinedData_ValidHoleDimension_SetsCorrectValues() throws Exception {
        // Arrange
        when(inputData.getDimensionDefinition()).thenReturn("hole");
        when(sqlBuilder.buildSelectSql()).thenReturn("SELECT hole FROM def_deviances WHERE nom_dim_range @> :value");
        when(jdbcTemplate.queryWithSchema(anyString(), anyMap(), eq(Boolean.class))).thenReturn(true);
        when(jdbcTemplate.queryWithSchema(anyString(), anyMap(), eq(BigDecimal.class))).thenReturn(new BigDecimal("0.05"));

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
    void generateDefinedData_InvalidDimension_ThrowsException() throws Exception {
        // Arrange
        when(inputData.getDimensionDefinition()).thenReturn("invalid_type");

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> method.invoke(ostMainService, jdbcTemplate));
        assertTrue(exception.getCause().getMessage().contains("Проверьте правильность типа введенных данных"));
    }

    @Test
    void generateDefinedData_ValidationFailed_ThrowsException() throws Exception {
        // Arrange
        when(inputData.getDimensionDefinition()).thenReturn("hole");
        when(sqlBuilder.buildSelectSql()).thenReturn("SELECT hole FROM def_deviances WHERE nom_dim_range @> :value");
        when(jdbcTemplate.queryWithSchema(anyString(), anyMap(), eq(Boolean.class))).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> method.invoke(ostMainService, jdbcTemplate));
        assertTrue(exception.getCause().getMessage().contains("Проверьте правильность введенных данных"));
    }

    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }


}