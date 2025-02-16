package org.martinmeer.otkassistant.core.model.sceletal;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.martinmeer.otkassistant.core.service.SqlBuilder;
import org.martinmeer.otkassistant.core.service.ValueDefiner;
import org.martinmeer.otkassistant.ost22.model.OstInputData;
import org.martinmeer.otkassistant.ost22.model.OstSqlBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Getter

public abstract class AbstractDefinedData<T> implements ValueDefiner<T>{

    protected final AbstractInputData inputData;
    //protected SqlBuilder sqlBuilder;

    //private String dimensionDefinition;
    //private BigDecimal nominalDimension;

    protected BigDecimal baseDeviance;
    protected BigDecimal upperDeviance;
    protected BigDecimal lowerDeviance;

    //private String sql;


    public AbstractDefinedData(AbstractInputData inputData) {
        this.inputData = inputData;
        //this.sqlBuilder = sqlBuilder;
    }

}
