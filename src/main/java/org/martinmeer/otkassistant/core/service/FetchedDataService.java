package org.martinmeer.otkassistant.core.service;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.model.*;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractFetchedData;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


@FieldNameConstants
@Getter
public abstract class FetchedDataService {

    @Setter
    protected AbstractInputData inputData;

    protected final SchemaAwareNamedParameterJdbcTemplate jdbcTemplate;

    protected NominalDimensionFetcher nominalDimension;
    protected UpperDeviance upperDeviance;
    protected LowerDeviance lowerDeviance;

    protected FetchedDataService(SchemaAwareNamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //Step 1. Initialize init fields in fetched classes from ostInputData

    protected abstract void init();

    /* Step 2 (if necessary): Initialize init fields in fetched classes from DB:
     - via separate conn?
     - via  initialization arranging in single conn?*/

    //Step 3. Fetch data from DB, init fetchedDataFields
    protected abstract void fetchData();
    protected abstract void setSchema();

    //Step 4. gen MapOf fetched classes for CalcService

    public Map<String, AbstractFetchedData> genFetchedDataMap() {
        return Map.of(Fields.nominalDimension, nominalDimension,
                Fields.upperDeviance, upperDeviance,
                Fields.lowerDeviance, lowerDeviance);
    }
}
