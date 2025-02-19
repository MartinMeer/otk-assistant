package org.martinmeer.otkassistant.core.service;

import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
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

    @Transactional
    public <T> T queryWithSchema(String sql, Map<String, Object> params, Class<T> clazz) {
        String sanitaizedSchemaName = sanitizeSchemaName(schemaName);
        try {
            jdbcTemplate.execute("SET LOCAL search_path TO " + sanitaizedSchemaName);
            return namedParameterJdbcTemplate.queryForObject(sql, params, clazz);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error setting schema or executing query", e);
        } /*finally {
            // Reset schema to default or previous state if needed
            jdbcTemplate.execute("RESET search_path");
        }*/
    }

    private String sanitizeSchemaName(String schemaName) {
        if (!schemaName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid schema name: " + schemaName);
        }
        return schemaName;
    }
}
