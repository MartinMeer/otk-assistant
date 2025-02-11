package org.martinmeer.otkassistant.ost22.model;

import lombok.Getter;
import lombok.Setter;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
public class OstNominalDimension extends AbstractFetchedData<BigDecimal> {

    private String detailType;

    protected OstNominalDimension(SchemaAwareNamedParameterJdbcTemplate dbQueryTemplate) {
        super(dbQueryTemplate);
    }


    @Override
    protected void setSql() {
        this.sql = ""; //select 10.0 from ost22.def_deviances where nom_dim_range @> 10.0;
    }


    @Override
    protected BigDecimal typeConverter(String baseData) {
        return new BigDecimal(baseData);
    }

    @Override
    protected RowMapper<BigDecimal> getObjectMapper() {
        return null;
    }
}