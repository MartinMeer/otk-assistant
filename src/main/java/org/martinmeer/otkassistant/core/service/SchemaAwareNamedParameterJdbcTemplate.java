package org.martinmeer.otkassistant.core.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SchemaAwareNamedParameterJdbcTemplate {

    @Setter
    private String schemaName;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SchemaAwareNamedParameterJdbcTemplate(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //@Transactional
    public <T> T queryWithSchema(String sql, Map<String, Object> params, Class<T> clazz) {

        String sanitizedSchemaName = sanitizeSchemaName(schemaName);
        //log.debug("Setting schema to: {}", sanitizedSchemaName); // Логирование выбора схемы
        //log.debug("Executing SQL: {}", sql); // Логирование SQL-запроса
        try {
            //log.debug("Parameters: {}", params);
            jdbcTemplate.execute("SET LOCAL search_path TO " + sanitizedSchemaName);
            return namedParameterJdbcTemplate.queryForObject(sql, params, clazz);
        } catch (DataAccessException e) {
            //log.error("Error executing query in schema {}: {}", sanitizedSchemaName, e.getMessage());
            throw new RuntimeException("Error setting schema or executing query", e);
        } finally {
            jdbcTemplate.execute("RESET search_path");
        }
    }

    private String sanitizeSchemaName(String schemaName) {
        if (!schemaName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid schema name: " + schemaName);
        }
        return schemaName;
    }
}
