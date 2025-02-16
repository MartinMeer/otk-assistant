package org.martinmeer.otkassistant.core.service;

import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
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

    public <T> T queryWithSchema(String sql, Map<String, Object> params, Class<T> clazz) {
        try {
            jdbcTemplate.execute("SET LOCAL search_path TO " + sanitizeSchemaName(schemaName));
            return namedParameterJdbcTemplate.queryForObject(sql, params, clazz);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error setting schema or executing query", e);
        }
    }

    public String sanitizeSchemaName(String schemaName) {
        if (!schemaName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid schema name: " + schemaName);
        }
        return schemaName;
    }
}
