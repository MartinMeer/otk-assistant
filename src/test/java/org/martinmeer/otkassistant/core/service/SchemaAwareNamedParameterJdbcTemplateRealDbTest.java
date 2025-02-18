package org.martinmeer.otkassistant.core.service;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.martinmeer.otkassistant.MainApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MainApp.class, SchemaAwareNamedParameterJdbcTemplateRealDbTest.class})
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = JndiDataSourceAutoConfiguration.class)
@Tag("excludeFromBuild")
public class SchemaAwareNamedParameterJdbcTemplateRealDbTest {

    @Autowired
    private SchemaAwareNamedParameterJdbcTemplate schemaAwareJdbc;

    @Test
    @Transactional
    void testReadDataFromRealDb() {
        // Arrange
        schemaAwareJdbc.setSchemaName("ost22");
        String sql = "SELECT deviance FROM undef_deviances WHERE dim_range @> :id";

        // Act
        BigDecimal result = schemaAwareJdbc.queryWithSchema(
                sql,
                Map.of("id", new BigDecimal("20.0")),
                BigDecimal.class
        );

        // Assert
        assertNotNull(result);
        BigDecimal expected = new BigDecimal("0.20");
        assertEquals(0, result.compareTo(expected));
    }
    @Test
    @Transactional
    void testInvalidSchemaName() {
        // Arrange
        schemaAwareJdbc.setSchemaName("invalid@schema!");;
        String sql = "SELECT deviance FROM undef_deviances WHERE dim_range @> :id";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            schemaAwareJdbc.queryWithSchema(sql,
                    Map.of("id", new BigDecimal("20.0")),
                    BigDecimal.class);
        }, "Invalid schema name should throw IllegalArgumentException");

        assertTrue(exception.getMessage().contains("Invalid schema name"), "Exception message should indicate invalid schema name");
    }
}