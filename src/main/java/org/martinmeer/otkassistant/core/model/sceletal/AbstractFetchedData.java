package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Getter;
import lombok.Setter;
import org.martinmeer.otkassistant.core.model.DataFetcher;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;

@Setter
@Getter
public abstract class AbstractFetchedData implements DataFetcher {


    protected String baseData;
    protected Object fetchedData;
    protected String sql;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected AbstractFetchedData(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void fetchFromDatabase() {
        // Выполняем запрос с использованием NamedParameterJdbcTemplate
        MapSqlParameterSource params = new MapSqlParameterSource("baseData", typeConverter(baseData));
        Object result = namedParameterJdbcTemplate.queryForObject(
                sql,
                params,
                getObjectMapper()
        );
        setFetchedData(result);
    }

    protected abstract <T> T typeConverter(String baseData);

    // Абстрактный метод для преобразования результата запроса
    protected abstract RowMapper<?> getObjectMapper();


}
