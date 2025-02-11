package org.martinmeer.otkassistant.core.service;

import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class SchemaAwareNamedParameterJdbcTemplate {

    @Setter
    private String schemaName;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SchemaAwareNamedParameterJdbcTemplate(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public <T> T queryWithSchema(String sql, Map<String, Object> params, RowMapper<T> rowMapper) {
        try {
            jdbcTemplate.execute("SET LOCAL search_path TO " + sanitizeSchemaName(schemaName));
            return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error setting schema or executing query", e);
        }
    }

    private String sanitizeSchemaName(String schemaName) {
        if (!schemaName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid schema name: " + schemaName);
        }
        return schemaName;
    }
}
