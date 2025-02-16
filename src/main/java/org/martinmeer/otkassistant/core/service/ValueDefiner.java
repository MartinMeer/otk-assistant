package org.martinmeer.otkassistant.core.service;

import java.util.Map;

public interface ValueDefiner<T> {

    //Map<String, T> generateDataForOutput();

    void generateDefinedData(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate);
}
