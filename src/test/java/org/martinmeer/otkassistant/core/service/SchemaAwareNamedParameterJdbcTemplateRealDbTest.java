package org.martinmeer.otkassistant.core.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = JndiDataSourceAutoConfiguration.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
}