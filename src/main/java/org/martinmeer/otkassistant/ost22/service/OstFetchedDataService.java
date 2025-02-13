package org.martinmeer.otkassistant.ost22.service;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.FetchedDataService;
import org.martinmeer.otkassistant.core.service.SchemaAwareNamedParameterJdbcTemplate;
import org.martinmeer.otkassistant.ost22.model.OstInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@FieldNameConstants
@Getter
public class OstFetchedDataService extends FetchedDataService {

    //private OstInputData ostInputData;

    protected OstFetchedDataService(@Qualifier("ostInputData") AbstractInputData inputData, SchemaAwareNamedParameterJdbcTemplate jdbcTemplate, List fetchedData) {
        super(inputData, jdbcTemplate, fetchedData);
    }


    @Override
    protected void init() {
        nominalDimension.setBaseData(inputData.getInputDimension());
    }

    @Override
    public void fetchData() {



    }

    @Override
    protected void setSchema() {

    }
}
