package org.martinmeer.otkassistant.core.service;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.model.*;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@FieldNameConstants
@Getter
public abstract class FetchedDataService {


    protected final AbstractInputData inputData;

    protected final SchemaAwareNamedParameterJdbcTemplate jdbcTemplate;

    protected final List fetchedData;

    protected FetchedDataService(AbstractInputData inputData, SchemaAwareNamedParameterJdbcTemplate jdbcTemplate, List fetchedData) {
        this.inputData = inputData;
        this.jdbcTemplate = jdbcTemplate;
        this.fetchedData = fetchedData;
    }


    public Map<String, AbstractFetchedData> genFetchedDataMap() {
        return Map.of(Fields.nominalDimension, nominalDimension,
                Fields.upperDeviance, upperDeviance,
                Fields.lowerDeviance, lowerDeviance);
    }
}
