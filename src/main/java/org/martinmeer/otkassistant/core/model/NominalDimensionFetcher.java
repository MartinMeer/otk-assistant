package org.martinmeer.otkassistant.core.model;

import lombok.Getter;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;


@Getter
public class NominalDimensionFetcher extends AbstractFetchedData {

    protected NominalDimensionFetcher(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                      String baseData) {
        super(namedParameterJdbcTemplate);
        this.baseData = baseData;
        this.sql = "SELECT :filterValue FROM ost22.def_deviances WHERE nom_dim_range @> :filterValue LIMIT 1";
    }

    @Override
    protected BigDecimal typeConverter(String baseData) {
        try {
            return new BigDecimal(baseData);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Невозможно преобразовать rawData в числовой тип", e);
        }
    }

    @Override
    protected RowMapper<?> getObjectMapper() {
        return (rs, rowNum) -> rs.getBigDecimal(":filterValue");
    }
}
