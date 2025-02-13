package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Getter;
import lombok.Setter;
import org.martinmeer.otkassistant.core.model.DataFetcher;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@Setter
@Getter
public abstract class AbstractFetchedData<T> {


    protected String baseData;
    protected T fetchedData;
    protected String sql;

    private final SchemaAwareNamedParameterJdbcTemplate dbQueryTemplate;

    protected AbstractFetchedData(SchemaAwareNamedParameterJdbcTemplate dbQueryTemplate) {
        this.dbQueryTemplate = dbQueryTemplate;
    }

    public void fetchFromDatabase() {
        Map<String, Object> params = Collections.singletonMap("baseData", typeConverter(baseData));
        T result = dbQueryTemplate.queryWithSchema(
                sql,
                params,
                getObjectMapper()
        );
        setFetchedData(result);
    }

    protected abstract T typeConverter(String baseData);

    // Абстрактный метод для преобразования результата запроса
    protected abstract RowMapper<T> getObjectMapper();

}
